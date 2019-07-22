<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" required="true" %>
<%@ attribute name="order" required="true" %>

<a href="${pageContext.servletContext.contextPath}/products?query=${param.query}&sort=${sortBy}&order=${order}">${order}</a>