package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void add(Cart cart, Product product, int quantity) throws OutOfStockException, ProductNotFoundException;
}
