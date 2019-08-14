package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDaoImpl implements OrderDao {
    private List<Order> orders;

    public static OrderDaoImpl getInstance() {
        return OrderDaoImpl.SingletonHolder.instance;
    }

    private OrderDaoImpl() {
        this.orders = new ArrayList<>();
    }

    public static class SingletonHolder {
        public static final OrderDaoImpl instance = new OrderDaoImpl();
    }

    @Override
    public void placeOrder(Order order) {
        order.setId(UUID.randomUUID());
        orders.add(order);
    }

    @Override
    public Order findById(UUID orderId) throws OrderNotFoundException {
        return orders.stream().filter(order -> order.getId().equals(orderId)).findAny().orElseThrow(OrderNotFoundException::new);
    }
}
