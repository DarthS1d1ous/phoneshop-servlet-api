package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.Cart.CartService;
import com.es.phoneshop.model.product.Cart.HttpSessionCartService;
import com.es.phoneshop.model.product.enums.DeliveryMod;
import com.es.phoneshop.model.product.enums.PaymentMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

public class OrderServiceImpl implements OrderService {
    private CartService cartService;

    public OrderServiceImpl() {
        this.cartService = HttpSessionCartService.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        return OrderServiceImpl.SingletonHolder.instance;
    }

    public static class SingletonHolder {
        public static final OrderServiceImpl instance = new OrderServiceImpl();
    }

    @Override
    public Order getOrder(HttpServletRequest request) {
        Order order = new Order();
        order.setCart(cartService.getCart(request.getSession()));
        order.setName(request.getParameter("name"));
        order.setSurname(request.getParameter("surname"));
        order.setPhone(request.getParameter("phone"));
        order.setDeliveryAddress(request.getParameter("deliveryAddress"));
        order.setDeliveryCost(new BigDecimal(5));
        order.setOrderCost(order.getCart().getTotalCost().add(order.getDeliveryCost()));
        order.setDeliveryDate(new Date());
        order.setDeliveryMod(getDeliveryMod(request));
        order.setPaymentMethod(getPaymentMethod(request));
        return order;
    }

    @Override
    public boolean isOrderValid(Order order) {
        return !order.getName().isEmpty()
                && !order.getSurname().isEmpty()
                && !order.getDeliveryAddress().isEmpty()
                && order.getDeliveryDate() != null
                && order.getPhone().matches("(\\+*)375\\d{9}");
    }

    private PaymentMethod getPaymentMethod(HttpServletRequest request) {
        String paymentMethod = request.getParameter("paymentMethod");
        if (paymentMethod == null) {
            return PaymentMethod.CASH;
        } else {
            return PaymentMethod.valueOf(paymentMethod.replaceAll(" ", "_").toUpperCase());
        }
    }

    private DeliveryMod getDeliveryMod(HttpServletRequest request) {
        String deliveryMod = request.getParameter("deliveryMod");
        if (deliveryMod == null) {
            return DeliveryMod.COURIER;
        } else {
            return DeliveryMod.valueOf(deliveryMod.replaceAll(" ", "_").toUpperCase());
        }
    }
}
