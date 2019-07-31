package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.history.HistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ArrayListProductDao arrayListProductDao;
    @Mock
    private Product product;
    @Mock
    private Cart cart;
    @Mock
    private CartService cartService;
    @Mock
    private HistoryService historyService;
    @InjectMocks
    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getParameter("quantity")).thenReturn("1");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Long correctId = product.getId();
        when(request.getPathInfo()).thenReturn("/" + correctId);
        when(arrayListProductDao.getProduct(correctId)).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(historyService).update(request, correctId);
        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(request).setAttribute(eq("product"), eq(product));
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoGetProductNotFoundException() throws ServletException, IOException {
        Long invalidId = Long.MAX_VALUE;
        when(request.getPathInfo()).thenReturn("/" + invalidId);
        when(arrayListProductDao.getProduct(invalidId)).thenThrow(new ProductNotFoundException());

        servlet.doGet(request, response);

        verify(response).setStatus(eq(404));
        verify(request).getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, OutOfStockException {
        when(request.getPathInfo()).thenReturn("/1");
        when(cartService.getCart(request)).thenReturn(cart);
        when(servlet.getProductFromPath(request)).thenReturn(product);
        when(request.getRequestURI()).thenReturn("http://localhost:8080/phoneshop-servlet-api/products/1");

        servlet.doPost(request, response);

        verify(cartService).add(cart, product, 1);
        verify(response).sendRedirect(request.getRequestURI() + "?message=Added to cart successfully");
    }

    @Test
    public void testDoPostNumberFormatException() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("1");
        when(request.getRequestURI()).thenReturn("http://localhost:8080/phoneshop-servlet-api/products/1");

        servlet.doPost(request, response);

        verify(response).sendRedirect(request.getRequestURI() + "?error=Not a number");

    }

    @Test
    public void testDoPostOutOfStockException() throws ServletException, IOException, OutOfStockException {
        when(cartService.getCart(request)).thenReturn(cart);
        doThrow(OutOfStockException.class).when(cartService).add(cart, product, 1);
        when(request.getPathInfo()).thenReturn("/1");
        when(servlet.getProductFromPath(request)).thenReturn(product);
        when(request.getRequestURI()).thenReturn("http://localhost:8080/phoneshop-servlet-api/products/1");

        servlet.doPost(request, response);

        verify(cartService).add(cart, product, 1);
        verify(response).sendRedirect(request.getRequestURI() + "?error=Out of stock. Max stock is " + 0);

    }
}
