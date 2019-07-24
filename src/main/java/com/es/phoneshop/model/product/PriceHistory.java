package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

public class PriceHistory {
    private Date date;
    private BigDecimal price;

    public PriceHistory() {
    }

    public PriceHistory(Date date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}