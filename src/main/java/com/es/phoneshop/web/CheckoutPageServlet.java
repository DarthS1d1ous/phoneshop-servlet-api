package com.es.phoneshop.web;

import com.es.phoneshop.model.product.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private OrderService orderService;
    private OrderDao orderDao;

    @Override
    public void init() {
        this.orderService = OrderServiceImpl.getInstance();
        this.orderDao = OrderDaoImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("order", orderService.getOrder(request));
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order = orderService.getOrder(request);
        if (orderService.isOrderValid(order)) {
            orderDao.placeOrder(order);
            response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getId());
        } else {
            request.setAttribute("order", order);
            request.setAttribute("error", "Incorrect data");
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
        }
    }
}
