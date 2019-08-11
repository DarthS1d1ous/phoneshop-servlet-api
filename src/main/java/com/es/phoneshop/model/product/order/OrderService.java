package com.es.phoneshop.model.product.order;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    Order getOrder(HttpServletRequest request);

    boolean isOrderValid(Order order);
}
