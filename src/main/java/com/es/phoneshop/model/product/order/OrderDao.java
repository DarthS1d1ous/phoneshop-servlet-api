package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;

import java.util.UUID;

public interface OrderDao {
    void placeOrder(Order order);

    Order findById(UUID orderId) throws OrderNotFoundException;
}
