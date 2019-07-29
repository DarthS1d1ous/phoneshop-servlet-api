package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.enums.OutputOrder;
import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private Long counter = 0L;

    private List<Product> products;

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.instance;
    }

    public static class SingletonHolder {
        public static final ArrayListProductDao instance = new ArrayListProductDao();
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
                .collect(Collectors.toMap(Function.identity(), product -> Arrays.stream(words)
                        .filter(x -> product.getDescription().toLowerCase().contains(x)).count()))
                .entrySet().stream()
                .filter(x -> x.getValue() > 0)
                .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(String query, OutputOrder outputOrder, SortBy sortBy) {
        List<Product> products = findProducts(query);
        if (sortBy == null) {
            return products;
        } else {
            Comparator<Product> comparator = sortBy.equals(SortBy.DESCRIPTION)
                    ? Comparator.comparing(Product::getDescription, Comparator.comparing(String::toLowerCase))
                    : sortBy.equals(SortBy.PRICE)
                    ? Comparator.comparing(Product::getPrice)
                    : null;
            if (comparator == null) {
                throw new IllegalArgumentException("Incorrect sortBy");
            }
            if (outputOrder.equals(OutputOrder.DESC)) {
                comparator = comparator.reversed();
            }
            products.sort(comparator);
            return products;
        }
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

    protected void setProducts(List<Product> products) {
        this.products = products;
    }
}
