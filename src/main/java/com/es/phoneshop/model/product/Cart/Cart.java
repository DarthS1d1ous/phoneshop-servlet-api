package com.es.phoneshop.model.product.Cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal totalCost = new BigDecimal(0);
    private int totalQuantity;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartItems=" + cartItems +
                ", totalCost=" + totalCost +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
