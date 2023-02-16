<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<title><fmt:message key="locale.LoginForm" /></title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<jsp:include page="../common/header.jsp"></jsp:include>
	<div class="container col-md-8 col-md-offset-3" style="overflow: auto">
		<h1><fmt:message key="locale.LoginForm" /></h1>
		<form action="<%=request.getContextPath()%>/FrontController?command=LOGIN_CONTROLLER" method="post">
			<div class="form-group">
				<label for="login"><fmt:message key="locale.UserName" />:</label> <input type="text"
					class="form-control" id="login" placeholder="Login"
					name="login" required>
			</div>

			<div class="form-group">
				<label for="password"><fmt:message key="locale.Password" />:</label> <input type="password"
					class="form-control" id="password" placeholder="Password"
					name="password" required>
			</div>

			<div class="alert alert-info" role="alert">
				<tf:notification/>
			</div>

			<button type="submit" class="btn btn-primary"><fmt:message key="locale.Submit" /></button>
		</form>

	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>