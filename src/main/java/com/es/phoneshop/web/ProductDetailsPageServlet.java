package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.Cart.HttpSessionCartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.recentlyViewed.HttpSessionRecentlyViewedService;
import com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class ProductDetailsPageServlet extends HttpServlet {
    private ArrayListProductDao arrayListProductDao;
    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;


    @Override
    public void init() {
        this.arrayListProductDao = ArrayListProductDao.getInstance();
        this.cartService = HttpSessionCartService.getInstance();
        this.recentlyViewedService = HttpSessionRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> recentlyViewedProducts = recentlyViewedService.getRecentlyViewed(request.getSession());
        try {
            Cart cart = cartService.getCart(request.getSession());
            request.setAttribute("cart", cart);
            recentlyViewedService.addRecentlyViewedProduct(recentlyViewedProducts, getProductFromPath(request).getId());
            request.setAttribute("recentlyViewed", recentlyViewedProducts);
            request.setAttribute("product", getProductFromPath(request));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (ProductNotFoundException e) {
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());

        try {
            int quantity = NumberFormat.getInstance(request.getLocale()).parse(request.getParameter("quantity")).intValue();
            Product product = getProductFromPath(request);
            cartService.add(cart, product, quantity);
            response.sendRedirect(request.getRequestURI() + "?message=Added to cart successfully");
        } catch (NumberFormatException | ParseException e) {
            response.sendRedirect(request.getRequestURI() + "?error=Not a number");
        } catch (OutOfStockException e) {
            response.sendRedirect(request.getRequestURI() + "?error=Out of stock. Max stock is " + e.getMaxStock());
        }
    }

    protected Product getProductFromPath(HttpServletRequest request) throws ProductNotFoundException {
        return arrayListProductDao.getProduct(Long.valueOf(request.getPathInfo().substring(1)));
    }
}
