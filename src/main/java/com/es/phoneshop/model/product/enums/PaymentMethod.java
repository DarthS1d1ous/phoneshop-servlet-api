package com.es.phoneshop.model.product.enums;

public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CART("Credit cart");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
