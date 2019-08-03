package com.es.phoneshop.model.product.Cart;

public class CartItem {
    private Long productId;
    private int quantity;

    public CartItem(Long productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException();
        }
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product " + productId + ", qty " + quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
