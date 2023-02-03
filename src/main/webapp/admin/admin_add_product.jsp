<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<%@ page isELIgnored="false" %>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<html>
<head>
	<meta charset="UTF-8">
<title><fmt:message key="locale.AddProductPage" /></title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">

</head>

</head>
<body>
<jsp:include page="../admin/admin_header.jsp"></jsp:include>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<form action="<%=request.getContextPath()%>/FrontController?command=ADMIN_ADD_PRODUCT" method="post" enctype="multipart/form-data">
				<caption>
					<h2>
						<fmt:message key="locale.AddProduct" />
					</h2>
				</caption>
				<fieldset class="form-group">
					<label><fmt:message key="locale.Name" /></label> <input type="text" class="form-control"
					<fmt:message key="locale.Name" />="name" required="required" minlength="3">
				</fieldset>

				<fieldset class="form-group">
					<label><fmt:message key="locale.Description" /></label> <input type="text" class="form-control"
						name="description" minlength="5">
				</fieldset>

				<fieldset class="form-group">
					<label><fmt:message key="locale.AttachProductImage" /></label>
					<input type="file" name="photo"/>
				</fieldset>

				<fieldset class="form-group">
					<label><fmt:message key="locale.Price" /></label> <input type="number" class="form-control"
					min="0" value="0" step=".01" name="price" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label><fmt:message key="locale.Category" /></label> <input type="text" class="form-control"
						name="category" required="required">
				</fieldset>

				<button type="submit" class="btn btn-success"><fmt:message key="locale.Save" /></button>
				</form>
				<div class="alert alert-success center" role="alert">
					<p><%=request.getParameter("NOTIFICATION")%></p>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
