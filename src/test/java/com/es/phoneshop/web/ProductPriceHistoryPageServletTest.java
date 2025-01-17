package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPriceHistoryPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ArrayListProductDao arrayListProductDao;
    @Mock
    private Product product;
    @Mock
    private RecentlyViewedService recentlyViewedService;
    private List<Product> recentlyViewedProducts;
    @InjectMocks
    private ProductPriceHistoryPageServlet servlet;

    public ProductPriceHistoryPageServletTest() {
    }

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Long correctId = product.getId();
        when(request.getPathInfo()).thenReturn("/" + correctId);
        when(arrayListProductDao.getProduct(correctId)).thenReturn(product);
        when(recentlyViewedService.getRecentlyViewed(session)).thenReturn(recentlyViewedProducts);

        servlet.doGet(request, response);

        verify(request).setAttribute("recentlyViewed", recentlyViewedProducts);
        verify(recentlyViewedService).getRecentlyViewed(session);
        verify(request).setAttribute(eq("product"), eq(product));
        verify(request).getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp");
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
}