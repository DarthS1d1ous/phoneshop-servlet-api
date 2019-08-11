<%@ page contentType="text/html;charset=UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.order.Order" scope="request"/>
<tags:master pageTitle="Product List">

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
        <br>
        <label for="name">Name</label><input name="name" id="name" value="${order.name}" readonly><Br>
        <label for="surname">Surname</label><input name="surname" id="surname" value="${order.surname}" readonly><Br>
        <label for="phone">Phone</label><input name="phone" id="phone" value="${order.phone}" readonly><Br>
        <label for="deliveryMode">Delivery Mode</label><input name="deliveryMode" id="deliveryMode"
                                                              value="${order.deliveryMod.name}" readonly><Br>
        <label for="deliveryDate">Delivery Date</label><input name="deliveryDate" id="deliveryDate"
                                                              value="${order.deliveryDate}" readonly><Br>
        <label for="deliveryCost">Delivery Cost</label><input class="price" name="deliveryCost" id="deliveryCost"
                                                              value="${order.deliveryCost}" readonly><Br>
        <label for="deliveryAddress">Delivery Address</label><input name="deliveryAddress" id="deliveryAddress"
                                                                    value="${order.deliveryAddress}" readonly><Br>
        <label for="paymentMethod">Payment Method</label><input name="paymentMethod" id="paymentMethod"
                                                                value="${order.paymentMethod.name}" readonly><Br>
            <%--Payment Method:--%>
            <%--<label for="cash">Cash</label><input type="radio" name="paymentMethod" id="cash">--%>
            <%--<label for="creditCart">Credit Cart</label><input type="radio" name="paymentMethod" id="creditCart"><br>--%>
    </form>

</tags:master>