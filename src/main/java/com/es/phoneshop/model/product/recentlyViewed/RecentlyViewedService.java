package com.es.phoneshop.model.product.recentlyViewed;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RecentlyViewedService {
    List<Product> getRecentlyViewed(HttpServletRequest request) throws ProductNotFoundException;
    void addRecentlyViewedProduct(List<Product> recentlyViewedProducts, Long productId) throws ProductNotFoundException;
}
