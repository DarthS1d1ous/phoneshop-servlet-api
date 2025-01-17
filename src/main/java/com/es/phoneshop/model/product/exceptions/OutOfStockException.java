package com.es.phoneshop.model.product.exceptions;

public class OutOfStockException extends Exception {
    private int maxStock;

    public OutOfStockException(int maxStock) {
        this.maxStock = maxStock;
    }

    @Override
    public String getMessage() {
        return "Out of stock. Max stock is " + this.getMaxStock();
    }

    public int getMaxStock() {
        return maxStock;
    }
}
