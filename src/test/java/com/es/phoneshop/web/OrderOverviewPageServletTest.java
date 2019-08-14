package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.product.order.OrderDao;
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
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private CartService cartService;
    @Mock
    private OrderDao orderDao;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private OrderOverviewPageServlet orderOverviewPageServlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp")).thenReturn(requestDispatcher);
        when(request.getRequestDispatcher("/WEB-INF/pages/orderNotFound.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        UUID correctId = UUID.randomUUID();
        when(request.getPathInfo()).thenReturn("/" + correctId);

        orderOverviewPageServlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetOrderNotFoundException() throws ServletException, IOException {
        UUID incorrectId = UUID.randomUUID();
        when(request.getPathInfo()).thenReturn("/" + incorrectId);
        when(orderDao.findById(incorrectId)).thenThrow(OrderNotFoundException.class);

        orderOverviewPageServlet.doGet(request, response);

        verify(response).setStatus(404);
        verify(request).getRequestDispatcher("/WEB-INF/pages/orderNotFound.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}