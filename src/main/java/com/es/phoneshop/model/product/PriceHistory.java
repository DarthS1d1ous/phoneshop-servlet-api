package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

public class PriceHistory {
    private GregorianCalendar date;
    private BigDecimal price;

    public PriceHistory() {
    }

    public PriceHistory(GregorianCalendar date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
