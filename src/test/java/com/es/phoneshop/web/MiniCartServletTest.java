package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {
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
    private MiniCartServlet servlet;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/miniCart.jsp")).thenReturn(requestDispatcher);
        when(cartService.getCart(session)).thenReturn(cart);
    }

    @Test
    public void testGoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        testPostAndGet();
    }

    @Test
    public void testGoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        testPostAndGet();
    }

    private void testPostAndGet() throws ServletException, IOException {
        verify(request).getSession();
        verify(cartService).getCart(session);
        verify(request).setAttribute("cart", cart);
        verify(request).getRequestDispatcher("/WEB-INF/pages/miniCart.jsp");
        requestDispatcher.include(request, response);
    }

}