<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<jsp:useBean id="history" type="java.util.ArrayList" scope="request"/>

<c:forEach items="${history}" var="historyProduct">
    <div id="history-block">
        <div>
            <img align="center" class="product-tile" src="${historyProduct.imageUrl}">
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/products/${historyProduct.id}">${historyProduct.description}</a>
        </div>
        <div>${historyProduct.price}${historyProduct.currency.symbol}</div>
    </div>
</c:forEach>

