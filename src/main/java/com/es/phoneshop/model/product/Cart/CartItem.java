package com.es.phoneshop.model.product.Cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product.getId() == null) {
            throw new IllegalArgumentException();
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ProductId " + product.getId() + ", qty " + quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
