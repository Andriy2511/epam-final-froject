<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<fmt:setBundle basename="locale"/>--%>
<%--<fmt:setBundle basename="locale_ua"/>--%>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="<%= request.getContextPath() %>/FrontController?command=USER_ORDER_COMMAND" class="navbar-brand"><fmt:message key="locale.MyOrders" /></a>
            <a href="<%= request.getContextPath() %>/catalog_goods/catalog.jsp" class="navbar-brand"><fmt:message key="locale.Catalog" /></a>
        </div>
        <ul class="navbar-nav navbar-collapse justify-content-end">
            <li><a href="<%= request.getContextPath() %>/user/card_page.jsp" class="navbar-brand"><fmt:message key="locale.Card" /></a></li>
            <li><a href="<%= request.getContextPath() %>/register/register.jsp" class="navbar-brand"><fmt:message key="locale.SignUp" /></a></li>
            <li><a href="<%= request.getContextPath() %>/login/login.jsp" class="navbar-brand"><fmt:message key="locale.Login" /></a></li>
        </ul>
    </nav>
</header>
