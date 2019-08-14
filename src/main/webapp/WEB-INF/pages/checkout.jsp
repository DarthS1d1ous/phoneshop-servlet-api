<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.order.Order" scope="request"/>
<tags:master pageTitle="Product List">
    <c:if test="${not empty order.cart.cartItems}">
        <form method="post">
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td>
                        Description
                    </td>
                    <td>
                        Quantity
                    </td>
                    <td class="price">
                        Price
                    </td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${order.cart.cartItems}" varStatus="status">
                    <tr>
                        <td>
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">${cartItem.product.description}</a>
                        </td>
                        <td>
                            <input name="quantity" class="quantity" type="number"
                                   value="${cartItem.quantity}"
                                   readonly>
                            <input name="productId" type="hidden" value="${cartItem.product.id}">
                        </td>
                        <td class="price">
                            <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${cartItem.product.id}">
                                <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                                  currencySymbol="${cartItem.product.currency.symbol}"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <tr style="background-color: mediumspringgreen">
                    <td style="font-weight: bold">Subtotal</td>
                    <td></td>
                    <td style="text-align: right">${order.cart.totalQuantity}</td>
                    <td><fmt:formatNumber value="${order.cart.totalCost}" type="currency"
                                          currencySymbol="${order.cart.cartItems.get(0).product.currency.symbol}"/></td>
                </tr>
                <tr style="background-color: mediumspringgreen">
                    <td style="font-weight: bold">Total</td>
                    <td></td>
                    <td></td>
                    <td><fmt:formatNumber value="${order.orderCost}" type="currency"
                                          currencySymbol="${order.cart.cartItems.get(0).product.currency.symbol}"/></td>
                </tr>
            </table>
            <c:if test="${not empty error}">
                <p class="error">Error</p>
            </c:if>
            <label for="name">Name</label><input name="name" id="name" value="${param.name}"><Br>
            <label for="surname">Surname</label><input name="surname" id="surname" value="${param.surname}"><Br>
            <label for="phone">Phone</label><input type="tel" name="phone" id="phone" value="${param.phone}"><Br>
            <label for="deliveryMode">Delivery Mode</label>
            <select name="deliveryMode" id="deliveryMode">
                <option>Courier</option>
                <option>Store pickup</option>
            </select>
            <br>
            <label for="deliveryDate">Delivery Date</label><input name="deliveryDate" id="deliveryDate"
                                                                  value="${order.deliveryDate}" readonly><Br>
            <label for="deliveryCost">Delivery Cost</label><input class="price" name="deliveryCost" id="deliveryCost"
                                                                  value="${order.deliveryCost}" readonly><Br>
            <label for="deliveryAddress">Delivery Address</label><input name="deliveryAddress" id="deliveryAddress"
                                                                        value="${param.deliveryAddress}"><Br>
            <label for="paymentMethod">Payment method </label>
            <select name="paymentMethod" id="paymentMethod">
                <option>Cash</option>
                <option>Credit cart</option>
            </select>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <p>
                <button>Place Order</button>
            </p>
        </form>
    </c:if>
</tags:master>