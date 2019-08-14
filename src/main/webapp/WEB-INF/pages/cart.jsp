<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.product.Cart.Cart" scope="request"/>
<tags:master pageTitle="Product List">
    <c:if test="${not empty errors}">
        <br><span class="error">Error updating cart</span><br>
    </c:if>
    <c:if test="${not empty cart.cartItems}">
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
                    <td>
                        Actions
                    </td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
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
                                   value="${not empty errors[status.index] ? paramValues.quantity[status.index] : cartItem.quantity}">
                            <c:if test="${not empty errors[status.index]}">
                                <span class="error">${errors[status.index]}</span>
                            </c:if>
                            <input name="productId" type="hidden" value="${cartItem.product.id}">
                        </td>
                        <td class="price">
                            <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${cartItem.product.id}">
                                <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                                  currencySymbol="${cartItem.product.currency.symbol}"/>
                            </a>
                        </td>
                        <td>
                            <button formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.id}">
                                Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${not empty param.message}">
                <p class="good">${param.message}</p>
            </c:if>
            <button>Update</button>
            <button formaction="${pageContext.servletContext.contextPath}/checkout" formmethod="get">Checkout</button>
        </form>
    </c:if>
    <c:if test="${empty cart.cartItems}">
        <p style="font-size: 30px">Cart is empty</p>
    </c:if>
</tags:master>