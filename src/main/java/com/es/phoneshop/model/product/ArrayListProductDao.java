package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();

    private Long counter = 0L;

    private List<Product> products;

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        return instance;
    }

    @Override
    public synchronized Product getProduct(Long id) throws ProductNotFoundException {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query) {
        String[] words = query
                .trim()
                .toLowerCase()
                .split(" ");

        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .filter(product -> Arrays.stream(words).anyMatch(x -> product.getDescription().toLowerCase().contains(x)))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query, String order, String sortBy) {
        List<Product> products = findProducts(query);
        Comparator<Product> comparator = null;
        if (sortBy != null) {
            switch (sortBy) {
                case "description":
                    comparator = Comparator.comparing(Product::getDescription, Comparator.comparing(String::toLowerCase));
                    break;
                case "price":
                    comparator = Comparator.comparing(Product::getPrice);
                    break;

            }
            if (comparator == null) {
                return products;
            }
            if (order.equals("desc")) {
                comparator = comparator.reversed();
            }
            products.sort(comparator);
        }
        return products;
    }

    @Override
    public synchronized void save(Product product) {
        if (product.getId() != null) {
            throw new IllegalArgumentException("Product ID must be null");
        }
        product.setId(++counter);
        products.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

}
