<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <form>
        <c:if test="${not empty error}">
            <p class="error">Error</p>
        </c:if>
        <label for="description">Description</label><input name="description" id="description"
                                                           value="${param.description}"><Br>
        <label for="minPrice">Min price</label><input name="minPrice" id="minPrice" value="${param.minPrice}"
                                                      class="price"><Br>
        <label for="maxPrice">Max price</label><input name="maxPrice" id="maxPrice" value="${param.maxPrice}"
                                                      class="price"><Br>
        <label for="minStock">Min stock</label><input name="minStock" id="minStock" value="${param.minStock}"
                                                      class="price"><Br>
        <label for="maxStock">Max stock</label><input name="maxStock" id="maxStock" value="${param.maxStock}"
                                                      class="price"><Br>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <button>Search</button>
    </form>
    <table>
    <thead>
    <tr>
        <td>Image</td>
        <td>
            Description
            <tags:sort sortBy="DESCRIPTION" order="DESC"/>
            <tags:sort sortBy="DESCRIPTION" order="ASC"/>
        </td>
        <td class="price">
            Price
            <tags:sort sortBy="PRICE" order="DESC"/>
            <tags:sort sortBy="PRICE" order="ASC"/>
        </td>
    </tr>
    </thead>
    <c:if test="${not empty products}">
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <td class="price">
                    <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </table>
    </c:if>
</tags:master>