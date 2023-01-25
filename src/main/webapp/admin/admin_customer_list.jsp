<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<fmt:setBundle basename="locale"/>--%>
<%--<fmt:setBundle basename="locale_ua"/>--%>
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

</head>
<body>
<header>
    <jsp:include page="../admin/admin_header.jsp"></jsp:include>
</header>

<div class="row">
    <div class="container">
        <h3 class="text-center">List of users</h3>
        <hr>
        <div class="container text-left">
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=showList&list=fullList"
               class="btn btn-success"><fmt:message key="locale.ShowListOfUsers" /></a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=showList&list=blockedList"
               class="btn btn-success"><fmt:message key="locale.ShowBlockedUsers" /></a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=showList&list=unblockedList"
               class="btn btn-success"><fmt:message key="locale.ShowUnblockedUsers" /></a>

        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th><fmt:message key="locale.Id" /></th>
                <th><fmt:message key="locale.Name" /></th>
                <th><fmt:message key="locale.Surname" /></th>
                <th><fmt:message key="locale.Login" /></th>
                <th><fmt:message key="locale.Email" /></th>
                <th><fmt:message key="locale.StatusBlocked" /></th>
                <th><fmt:message key="locale.Actions" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${userList}">
                <tr>
                    <td><c:out value="${user.getId()}" /></td>
                    <td><c:out value="${user.getName()}" /></td>
                    <td><c:out value="${user.surname}" /></td>
                    <td><c:out value="${user.login}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.statusBlocked}" /></td>
                    <td><a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=block&userId=<c:out value='${user.id}'/>"><fmt:message key="locale.Block" /></a>
                        <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=unblock&userId=<c:out value='${user.id}'/>"><fmt:message key="locale.Unblock" /></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=showList&page=previous"
               class="btn btn-primary">Previous</a>
            <a href="<%=request.getContextPath()%>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=showList&page=next"
               class="btn btn-success">Next</a>


    </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
