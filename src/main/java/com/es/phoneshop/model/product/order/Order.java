package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.Cart.Cart;
import com.es.phoneshop.model.product.enums.DeliveryMod;
import com.es.phoneshop.model.product.enums.PaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Order implements Serializable {
    private UUID id;
    private Cart cart;
    private String name;
    private String surname;
    private String phone;
    private DeliveryMod deliveryMod;
    private Date deliveryDate;
    private BigDecimal deliveryCost;
    private String deliveryAddress;
    private BigDecimal orderCost;

    public Order() {
    }

    public Order(Cart cart, String name, String surname,
                 String phone, DeliveryMod deliveryMod,
                 Date deliveryDate, BigDecimal deliveryCost,
                 String deliveryAddress, BigDecimal orderCost, PaymentMethod paymentMethod) {
        this.cart = cart;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.deliveryMod = deliveryMod;
        this.deliveryDate = deliveryDate;
        this.deliveryCost = deliveryCost;
        this.deliveryAddress = deliveryAddress;
        this.orderCost = orderCost;
        this.paymentMethod = paymentMethod;
    }

    private PaymentMethod paymentMethod;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DeliveryMod getDeliveryMod() {
        return deliveryMod;
    }

    public void setDeliveryMod(DeliveryMod deliveryMod) {
        this.deliveryMod = deliveryMod;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

}
