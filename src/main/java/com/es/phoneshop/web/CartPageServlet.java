package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.Cart.HttpSessionCartService;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() {
        this.cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        String[] quantities = request.getParameterValues("quantity");
        String[] productsId = request.getParameterValues("productId");
        String[] errors = new String[productsId.length];
        for (int i = 0; i < productsId.length; i++) {
            int quantity;
            try {
                quantity = NumberFormat.getInstance(request.getLocale()).parse(quantities[i]).intValue();
                cartService.update(cart, Long.valueOf(productsId[i]), quantity);
            } catch (NumberFormatException | ParseException e) {
                errors[i] = "Not a number";
            } catch (OutOfStockException e) {
                errors[i] = e.getMessage();
            }
        }
        boolean hasError = Arrays.stream(errors).anyMatch(Objects::nonNull);
        if (hasError) {
            request.setAttribute("errors", errors);
            doGet(request, response);
        } else {
            response.sendRedirect(request.getRequestURI() + "?message=Update successfully");
        }
    }
}
