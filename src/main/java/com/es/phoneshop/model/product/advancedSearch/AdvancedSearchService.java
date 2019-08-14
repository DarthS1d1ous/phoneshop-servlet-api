package com.es.phoneshop.model.product.advancedSearch;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface AdvancedSearchService {
    List<Product> findProducts(String description, int minPrice, int maxPrice, int minStock, int maxStock);
}
