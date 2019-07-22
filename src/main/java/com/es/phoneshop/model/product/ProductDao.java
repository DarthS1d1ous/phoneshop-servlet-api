package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts();
    List<Product> findProducts(String query);
    List<Product> findProducts(String query, String order,String sortBy);
    void save(Product product);
    void delete(Long id);
}
