package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class Product {
    private Long id;
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private List<PriceHistory> priceHistory;

    public Product() {
    }

    public Product(Long id, String code, String description, Currency currency, int stock, String imageUrl, List<PriceHistory> priceHistory) {
        this.id = id;
        this.code = code;
        this.description = description;
        if (!priceHistory.isEmpty()) {
            this.price = priceHistory.get(0).getPrice();
        } else this.price = null;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.priceHistory = priceHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<PriceHistory> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceHistory> priceHistory) {
        this.priceHistory = priceHistory;
    }
}