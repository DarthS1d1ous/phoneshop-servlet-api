package com.es.phoneshop.model.product.enums;

public enum DeliveryMod {
    COURIER("Courier"),
    STORE_PICKUP("Store pickup");

    private final String name;

    DeliveryMod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
