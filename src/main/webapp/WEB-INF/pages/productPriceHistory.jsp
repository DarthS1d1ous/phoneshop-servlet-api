<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <h1>Price history</h1>
    <h2>${product.description}</h2>
    <table border="none">
        <thead>
        <tr align="left">
            <td>Start date</td>
            <td class="price">
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="priceHistory" items="${product.priceHistory}">
            <tr>
                <td>${priceHistory.date}</td>
                <td class="price">
                    <fmt:formatNumber value="${priceHistory.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>

</tags:master>