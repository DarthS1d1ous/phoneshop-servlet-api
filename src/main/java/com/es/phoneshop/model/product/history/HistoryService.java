package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface HistoryService {
    void update(HttpServletRequest request,Long productId) throws ProductNotFoundException;
}
