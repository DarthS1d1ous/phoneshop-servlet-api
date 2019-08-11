package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.Cart.HttpSessionCartService;
import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.product.order.Order;
import com.es.phoneshop.model.product.order.OrderDao;
import com.es.phoneshop.model.product.order.OrderDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class OrderOverviewPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderDao orderDao;

    @Override
    public void init() {
        this.orderDao = OrderDaoImpl.getInstance();
        this.cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID orderId = UUID.fromString(request.getPathInfo().substring(1));
        try {
            Order order = orderDao.findById(orderId);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
            cartService.clearCart(request.getSession());
        } catch (OrderNotFoundException e) {
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/orderNotFound.jsp").forward(request, response);
        }

    }
}
