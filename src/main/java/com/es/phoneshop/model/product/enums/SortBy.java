package com.es.phoneshop.model.product.enums;

public enum SortBy {
    DESCRIPTION("description"),
    PRICE("price");

    private String sortBy;

    SortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }
}
