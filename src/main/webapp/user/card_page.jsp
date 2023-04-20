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
  <title><fmt:message key="locale.Card" /></title>

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
        <th><fmt:message key="locale.Name" /></th>
        <th><fmt:message key="locale.Price" /></th>
        <th><fmt:message key="locale.Category" /></th>
        <th><fmt:message key="locale.Actions" /></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="goods" items="${cardGoodsList}">

        <tr>
          <td><c:out value="${goods.getName()}" /></td>
          <td><c:out value="${goods.getPrice()}" /></td>
          <td><ctg:info-category categoryId="${goods.getCategoryId()}"/></td>
          <td><a href="${pageContext.request.contextPath}/FrontController?command=USER_CARD_COMMAND&action=confirm&goodsId=<c:out value='${goods.id}'/>" class="btn btn-info"><fmt:message key="locale.ConfirmOrder" /></a>
            <a href="${pageContext.request.contextPath}/FrontController?command=USER_CARD_COMMAND&action=delete&goodsId=<c:out value='${goods.id}'/> " class="btn btn-danger"><fmt:message key="locale.Delete" /></a></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <div class="alert alert-success center" role="alert">
      <p>${NOTIFICATION}</p>
    </div>
  </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
