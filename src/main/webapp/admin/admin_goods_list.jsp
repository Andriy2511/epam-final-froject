<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<html>
<head>
    <title><fmt:message key="locale.GoodsManagementPage" /></title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>

</head>
<body>
<header>
    <jsp:include page="../admin/admin_header.jsp"></jsp:include>
</header>
<div class="row">
    <div class="container">
        <h3 class="text-center"><fmt:message key="locale.ListOfGoods" /></h3>
        <hr>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><fmt:message key="locale.Id" /></th>
                <th><fmt:message key="locale.Name" /></th>
                <th><fmt:message key="locale.Description" /></th>
                <th><fmt:message key="locale.Price" /></th>
                <th><fmt:message key="locale.Category" /></th>
                <th><fmt:message key="locale.PublicationDate" /></th>
                <th><fmt:message key="locale.Actions" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="goods" items="${goodsList}">
                <c:set var = "categoryId" value="${goods.getCategoryId()}" />
                <tr>
                    <td><c:out value="${goods.getId()}" /></td>
                    <td><c:out value="${goods.getName()}" /></td>
                    <td><c:out value="${goods.getDescription()}" /></td>
                    <td><c:out value="${goods.getPrice()}" /></td>
                    <td><ctg:info-category categoryId="${goods.getCategoryId()}"/></td>
                    <td><c:out value="${goods.getPublicationTime()}" /></td>
                    <td><a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=change&goodsId=<c:out value='${goods.id}'/>" class="btn btn-info"><fmt:message key="locale.Change" /></a>
                        <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=delete&goodsId=<c:out value='${goods.id}'/> " class="btn btn-danger"><fmt:message key="locale.Delete" /></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList&page=previous"
               class="btn btn-primary"><fmt:message key="locale.Previous" /></a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList&page=next"
               class="btn btn-success"><fmt:message key="locale.Next" /></a>
        </div>
        <br>
        <div class="alert alert-success center" role="alert">
            <p><%=request.getParameter("NOTIFICATION")%></p>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
