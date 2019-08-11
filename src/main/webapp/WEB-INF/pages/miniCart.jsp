<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="cart" type="com.es.phoneshop.model.product.Cart.Cart" scope="request"/>
<div style="float: right; margin-right: 3em; margin-top: 1.2em ; font-size: 20px">
    <a href="${pageContext.servletContext.contextPath}/cart">
        <img src="${pageContext.servletContext.contextPath}/images/cart.jpg">
    </a>
    <c:if test="${not empty cart}">
        Total quantity: ${cart.totalQuantity}
        Total cost: ${cart.totalCost}
    </c:if>
</div>