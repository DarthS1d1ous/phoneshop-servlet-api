package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Product product, int quantity) throws OutOfStockException, ProductNotFoundException;
}
