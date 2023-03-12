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
    <title><fmt:message key="locale.UserManagementPage" /></title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>

<body class="bg-light">
<header>
    <jsp:include page="../admin/admin_header.jsp"></jsp:include>
</header>
<div class="row">
    <div class="container">
        <h3 class="text-center"><fmt:message key="locale.ListOfOrders" /></h3>
        <hr>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=showList&list=registeredList"
               class="btn btn-success"><fmt:message key="locale.ShowRegisteredOrders" /></a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=showList&list=paidList"
               class="btn btn-success"><fmt:message key="locale.ShowPaidOrders" /></a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=showList&list=canceledList"
               class="btn btn-success"><fmt:message key="locale.ShowCanceledOrders" /></a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><fmt:message key="locale.Id" /></th>
                <th><fmt:message key="locale.User" /></th>
                <th><fmt:message key="locale.Goods" /></th>
                <th><fmt:message key="locale.Status" /></th>
                <th><fmt:message key="locale.Actions" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orderList}">
                <tr>
                    <td><c:out value="${order.getId()}" /></td>
                    <td><ctg:info-user userId="${order.getUserId()}"/></td>
                    <td><ctg:info-goods goodsId="${order.getGoodsId()}"/></td>
                    <td><ctg:info-order-status orderStatusId="${order.getOrderStatusId()}"/></td>
                    <td><a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=paid&orderId=<c:out value='${order.id}'/>" class="btn btn-info"><fmt:message key="locale.Paid" /></a>
                        <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=canceled&orderId=<c:out value='${order.id}'/> " class="btn btn-danger"><fmt:message key="locale.Canceled" /></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=showList&page=previous"
               class="btn btn-primary"><fmt:message key="locale.Previous" /></a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=showList&page=next"
               class="btn btn-success"><fmt:message key="locale.Next" /></a>
        </div>
        <div class="alert alert-success center" role="alert">
            <tf:notification/>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</body>
</html>
