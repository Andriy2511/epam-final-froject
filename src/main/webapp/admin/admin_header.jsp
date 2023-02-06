<%@ page import="com.example.finalproject.command.language.LanguageCommand" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <div>
            <a href="" class="navbar-brand"><fmt:message key="locale.AdminPage" /></a>
<%--            <a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=en<%request.getSession().setAttribute("mapParam", request.getParameterMap());%><%request.getSession().setAttribute("servletPath", request.getServletPath());%>">ENG</a>--%>
<%--            <a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=en<%request.getSession().setAttribute("mapParam", request.getParameterMap());%><%request.getSession().setAttribute("servletPath", request.getServletPath());%>">ENG</a>--%>
            <a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=en<%request.getSession().setAttribute("MyURL", request.getRequestURL().append('?').append(request.getQueryString()));%>">ENG</a>
            <a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=ua<%request.getSession().setAttribute("MyURL", request.getRequestURL().append('?').append(request.getQueryString()));%>">UKR</a>
        </div>
        <ul class="navbar-nav navbar-collapse justify-content-end">
<%--            <li><a href="<%= request.getContextPath() %>/admin/admin_page.jsp" class="navbar-brand">Home</a></li>--%>
            <li><a href="<%= request.getContextPath() %>/admin/admin_add_product.jsp" class="navbar-brand"><fmt:message key="locale.AddProductPage" /></a></li>
            <li><a href="<%= request.getContextPath() %>/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList" class="navbar-brand"><fmt:message key="locale.ViewProduct" /></a></li>
            <li><a href="<%= request.getContextPath() %>/FrontController?command=ADMIN_ORDER_CONTROLLER&action=showList&list=registeredList" class="navbar-brand"><fmt:message key="locale.ViewOrders" /></a></li>
            <li><a href="<%= request.getContextPath() %>/FrontController?command=ADMIN_CUSTOMER_CONTROLLER&action=showList&list=fullList" class="navbar-brand"><fmt:message key="locale.ViewCustomers" /></a></li>
            <li><a href="<%= request.getContextPath() %>/register/register.jsp" class="navbar-brand"><fmt:message key="locale.SignUp" /></a></li>
            <li><a href="<%= request.getContextPath() %>/FrontController?command=LOGOUT_COMMAND" class="navbar-brand"><fmt:message key="locale.Login" /></a></li>
        </ul>
    </nav>
</header>
