<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <form>
        <button formaction="${pageContext.servletContext.contextPath}/products/advancedSearch" formmethod="get">Advanced Search
        </button>
    </form>
    <br>
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
    <tags:recentlyViewed/>
</tags:master>