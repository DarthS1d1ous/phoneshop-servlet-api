package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.Cart.HttpSessionCartService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() {
        this.cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cart cart = cartService.getCart(request.getSession());
        Long productId = getProductIdFromPath(request);

        cartService.delete(cart, productId);

        response.sendRedirect(request.getContextPath() + "/cart");

    }

    protected Long getProductIdFromPath(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
