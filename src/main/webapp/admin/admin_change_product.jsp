<%@ page import="com.example.finalproject.models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="locale.AddProduct" /></title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

</head>
</head>
<body>
<jsp:include page="../admin/admin_header.jsp"></jsp:include>
<div class="content-wrapper">
    <div class="container">
        <div class="row pad-botm">
            <div class="col-md-12">
                <h4 class="header-line">Edit Product</h4>
            </div>
        </div>
        <c:set var = "goods" value="${goods}" />
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <form role="form" action="<%=request.getContextPath()%>/FrontController?command=ADMIN_CHANGE_PRODUCT&goodsId=<c:out value='${goods.getId()}'/>"
                              method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label><fmt:message key="locale.EnterName" /></label> <input class="form-control" type="text" name="name" value="<c:out value="${goods.getName()}" />" />
                            </div>
                            <div class="form-group">
                                <label><fmt:message key="locale.Description" /></label> <input class="form-control" type="text" style="min-height: 100px;" name="description" value="<c:out value="${goods.getDescription()}"/>" />
                            </div>
                            <div class="form-group">
                                <label>Photo</label> <input class="form-control" type="text" name="photoName" value="<c:out value="${goods.getPhoto()}"/>" />
                            </div>

                            <div class="form-group">
                                <label><fmt:message key="locale.AttachProductImage" /></label>
                                <input type="file" name="photo"/>
                            </div>

                            <div class="form-group">
                                <label><fmt:message key="locale.Price" /></label> <input class="form-control" type="number" min="0" step=".01" name="price" value="<c:out value="${goods.getPrice()}"/>" />
                            </div>

                            <div class="form-group">
                                <label><fmt:message key="locale.Category" /></label> <input class="form-control" type="text" name="category" value="<ctg:info-category categoryId="${goods.getCategoryId()}"/>" />
                            </div>

                            <div class="alert alert-success center" role="alert">
                                <p>${NOTIFICATION}</p>
                            </div>

                            <button type="submit" class="btn btn-success"><fmt:message key="locale.UpdateProduct" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"></jsp:include>
</body>
</html>
