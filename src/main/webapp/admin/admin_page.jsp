<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<fmt:setBundle basename="locale"/>--%>
<%--<fmt:setBundle basename="locale_ua"/>--%>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Home Page</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
    <jsp:include page="../admin/admin_header.jsp"></jsp:include>
    <h1><%=request.getSession().getAttribute("id")%></h1>
    <h2><%=request.getSession().getAttribute("login")%></h2>
    <h3><%=request.getSession().getAttribute("user")%></h3>
    <div class="navbar-collapse">
        <ul id="menu-top" class="nav navbar-nav navbar-right">
            <li><a href="admin_page.jsp">Home</a></li>
            <li><a href="">Add Product</a></li>
            <li><a href="">View Products</a></li>
            <li><a href="">View All Orders</a></li>
            <li><a href="">View Pending Orders</a></li>
            <li><a href="">View Delivered Orders</a></li>
            <li><a href="">View Customers</a></li>
            <li><a href="">LOGOUT</a></li>
        </ul>
    </div>
    <jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
