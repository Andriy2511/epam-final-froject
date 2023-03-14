<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<html>
<head>
    <title><fmt:message key="locale.Catalog" /></title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>

</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<tf:set_background_image image="shopping-carts-facing-each-other.jpg"></tf:set_background_image>
<div class="container">
<div class="card-header my-3"><fmt:message key="locale.AllProducts" /></div>
    <div class="footer-copyright text-center py-3">
        <a href="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&sort=sortByName"
           class="btn btn-light"><fmt:message key="locale.SortByName" /></a>
        <a href="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&sort=sortByPrice"
           class="btn btn-light"><fmt:message key="locale.SortByPrice" /></a>
        <a href="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&sort=sortByNovelty"
           class="btn btn-light"><fmt:message key="locale.SortByNovelty" /></a>
        <a href="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&sort=default"
           class="btn btn-light"><fmt:message key="locale.DefaultOrder" /></a>
        <tf:arrows></tf:arrows>
    </div>
    <form name="category" action="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&sort=sortByCategory" method="post">
        <input type="text" id="categoryName" name="categoryName" placeholder="Search by category">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>
<div class="row">
    <c:forEach var="goods" items="${goodsList}">
    <div class="col-md-3 my-3">
        <div class="card w-100">
<%--            <img class="card-img-top" height="270px" width="100%" src="${pageContext.request.contextPath}/image/<c:out value="${goods.getPhoto()}" />"--%>
<%--                 alt="Card image">--%>
            <tf:image imgSrc="${goods.getPhoto()}" imgClass="card-img-top" imgHeight="270px" imgWidth="100%" imgAlt="Without photo" />
            <div class="card-body">
                <h5 class="card-title"></h5>
                <h6 class="price"><fmt:message key="locale.Name" />: <c:out value="${goods.getName()}" /></h6>
                <h6 class="price"><fmt:message key="locale.Price" />: <c:out value="${goods.getPrice()}" /></h6>
                <h6 class="category"><fmt:message key="locale.Category" />: <ctg:info-category categoryId="${goods.getCategoryId()}" /></h6>
                <div class="mt-3 d-flex justify-content-between">
                    <a class="btn btn-dark" href="<%= request.getContextPath() %>/FrontController?command=CATALOG_COMMAND&action=addToCard&goodsId=<c:out value='${goods.id}'/>"><fmt:message key="locale.AddToCard" /></a> <a
                        class="btn btn-primary" href="<%= request.getContextPath() %>/FrontController?command=CATALOG_COMMAND&action=buyNow&goodsId=<c:out value='${goods.id}'/>"><fmt:message key="locale.BuyNow" /></a>
                </div>
            </div>
        </div>
    </div>
    </c:forEach>
</div>
</div>

<div class="footer-copyright text-center py-3">
    <a href="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&page=previous"
       class="btn btn-primary"><fmt:message key="locale.Previous" /></a>
    <a href="<%=request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList&page=next"
       class="btn btn-success"><fmt:message key="locale.Next" /></a>
</div>
</body>
</html>
