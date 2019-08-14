package com.es.phoneshop.web;

import com.es.phoneshop.model.product.order.Order;
import com.es.phoneshop.model.product.order.OrderDao;
import com.es.phoneshop.model.product.order.OrderService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private OrderService orderService;
    @Mock
    private OrderDao orderDao;
    @Mock
    private Order order;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private CheckoutPageServlet checkoutPageServlet;

    @Before
    public void setup() {
        when(orderService.getOrder(request)).thenReturn(order);
        when(request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        checkoutPageServlet.doGet(request, response);

        verify(request).setAttribute("order", order);
        verify(request).getRequestDispatcher("/WEB-INF/pages/checkout.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostValidTrue() throws ServletException, IOException {
        when(orderService.isOrderValid(order)).thenReturn(true);

        checkoutPageServlet.doPost(request, response);

        verify(orderDao).placeOrder(order);
        verify(response).sendRedirect(request.getContextPath() + "/orderOverview/" + order.getId());
    }

    @Test
    public void doPostValidFalse() throws ServletException, IOException {
        when(orderService.isOrderValid(order)).thenReturn(false);

        checkoutPageServlet.doPost(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/checkout.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
