<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<c:set var="locale" value="${sessionScope.lang}" />
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="locale" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<title><fmt:message key="locale.Registration" /></title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body class="bg-light">
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
	<h2><fmt:message key="locale.UserRegisterForm" /></h2>
	<div class="col-md-6 col-md-offset-4">
		<div class="alert alert-success center" role="alert">
			<tf:notification/>
		</div>

		<form action="${pageContext.request.contextPath}/FrontController?command=REGISTRATION_CONTROLLER" method="post">
			<div class="form-group">
				<label for="uname"><fmt:message key="locale.Name" />:</label>
				<input type="text" class="form-control" id="uname" placeholder="Name" name="name" required>
			</div>

			<div class="form-group">
				<label for="uname"><fmt:message key="locale.Surname" />:</label>
				<input type="text" class="form-control" id="surname" placeholder="Surname" name="surname" required>
			</div>

			<div class="form-group">
				<label for="uname"><fmt:message key="locale.Login" />:</label>
				<input type="text" class="form-control" id="username" placeholder="Login" name="login" required>
			</div>

			<div class="form-group">
				<label for="uname"><fmt:message key="locale.Password" />:</label>
				<input type="password" class="form-control" id="password" placeholder="Password" name="password" required>
			</div>

			<div class="form-group">
				<label for="uname"><fmt:message key="locale.Email" />:</label>
				<input type="text" class="form-control" id="email" placeholder="Email" name="email" required>
			</div>
			<button type="submit" class="btn btn-primary"><fmt:message key="locale.Submit" /></button>
		</form>
	</div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>