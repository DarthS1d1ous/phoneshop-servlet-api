package com.es.phoneshop.model.product.enums;

public enum Order {
    ASC("asc"),
    DESC("desc");

    private String order;

    public String getOrder() {
        return order;
    }

    Order(String order) {
        this.order = order;
    }
}
