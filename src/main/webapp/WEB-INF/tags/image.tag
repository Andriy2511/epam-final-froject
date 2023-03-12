<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="imgClass" required="false" %>
<%@ attribute name="imgHeight" required="false" %>
<%@ attribute name="imgWidth" required="false" %>
<%@ attribute name="imgAlt" required="false" %>
<%@ attribute name="imgSrc" required="true" %>

<c:set var="image" value="${pageContext.request.contextPath}/image/${imgSrc}" />

<img class="${imgClass}" height="${imgHeight}" width="${imgWidth}" src="<c:out value="${image}" />" alt="${imgAlt}" />