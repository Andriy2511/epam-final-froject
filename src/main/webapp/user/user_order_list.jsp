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
    <title><fmt:message key="locale.OrderList" /></title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
</head>
<body>
<tf:set_background_image image="composition-black-friday-shopping-cart-with-copy-space.jpg"></tf:set_background_image>
<header>
    <jsp:include page="../common/header.jsp"></jsp:include>
</header>

<div class="row">
    <div class="container">
        <h3 class="text-center"><fmt:message key="locale.ListOfOrders" /></h3>
        <hr>
        <hr>
        <br>
        <table class="table table-bordered bg-warning">
            <thead>
            <tr>
                <th><fmt:message key="locale.Id" /></th>
                <th><fmt:message key="locale.Goods" /></th>
                <th><fmt:message key="locale.Status" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orderList}">
                <c:set var = "userId" value="${order.getUserId()}" />
                <c:set var = "goodsId" value="${order.getGoodsId()}" />
                <c:set var = "orderStatusId" value="${order.getOrderStatusId()}" />
                <tr>
                    <td><c:out value="${order.getId()}" /></td>
                    <td><ctg:info-goods goodsId="${order.getGoodsId()}"/></td>
                    <td><ctg:info-order-status orderStatusId="${order.getOrderStatusId()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <a href="${pageContext.request.contextPath}/FrontController?command=USER_ORDER_COMMAND&page=previous"
               class="btn btn-primary"><fmt:message key="locale.Previous" /></a>
            <a href="${pageContext.request.contextPath}/FrontController?command=USER_ORDER_COMMAND&page=next"
               class="btn btn-success"><fmt:message key="locale.Next" /></a>
        </div>
        <div class="alert alert-success center" role="alert">
            <p>${NOTIFICATION}</p>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
