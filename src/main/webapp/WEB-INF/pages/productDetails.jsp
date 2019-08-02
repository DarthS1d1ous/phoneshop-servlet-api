<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product">
    <p>${cart.getCartItems()}</p>
    <c:if test="${not empty param.error}">
        <p class="error">Error</p>
    </c:if>
    <div>
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td class="price">
                    Price
                </td>
            </tr>
            </thead>
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>${product.description}</td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </table>
    </div>

    <div>
        <form method="post">
            <input name="quantity" class="price" value=${empty param.quantity ? 1 : param.quantity}>
            <button>Add to cart</button>
            <c:if test="${not empty param.message}">
                <p class="good">${param.message}</p>
            </c:if>
            <c:if test="${not empty param.error}">
                <p class="error">${param.error}</p>
            </c:if>
        </form>
    </div>

    <tags:recentlyViewed/>
</tags:master>