<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<jsp:useBean id="recentlyViewed" type="java.util.ArrayList" scope="request"/>
<h3>Recently Viewed</h3>
<table>
    <thead>
    <c:forEach var="recentlyViewedProduct" items="${recentlyViewed}">
        <td align="center">
            <img class="product-tile"
                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${recentlyViewedProduct.imageUrl}">
            <br>
            <a href="<c:url value="/products/${recentlyViewedProduct.id}"/>">
                    ${recentlyViewedProduct.description}
            </a>
            <br>
            <fmt:formatNumber value="${recentlyViewedProduct.price}" type="currency"
                              currencySymbol="${recentlyViewedProduct.currency.symbol}"/>
        </td>
    </c:forEach>
    </thead>
</table>

