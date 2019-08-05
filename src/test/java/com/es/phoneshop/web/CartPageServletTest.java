package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Cart cart;
    @InjectMocks
    private CartPageServlet servlet;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).getSession();
        verify(cartService).getCart(session);
        verify(request).setAttribute("cart", cart);
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        requestDispatcher.forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String[] quantities = {"5", "10"};
        String[] productsId = {"1", "2"};
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getParameterValues("productId")).thenReturn(productsId);

        servlet.doPost(request, response);

        verify(request).setAttribute("message", "Update successfully");
    }

    @Test
    public void testDoPostExceptions() throws ServletException, IOException, OutOfStockException {
        String[] quantities = {"", "-40"};
        String[] productsId = {"1", "2"};
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        when(request.getParameterValues("productId")).thenReturn(productsId);
        doThrow(OutOfStockException.class).when(cartService).update(cart, 2L, -40);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errors"), any());
    }
}