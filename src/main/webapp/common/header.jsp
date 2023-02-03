<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<header>
	<nav class="navbar navbar-expand-md navbar-dark"
		 style="background-color: tomato">
		<div>
			<a href="<%= request.getContextPath() %>/FrontController?command=USER_ORDER_COMMAND" class="navbar-brand"><fmt:message key="locale.MyOrders" /></a>
			<a href="<%= request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList" class="navbar-brand"><fmt:message key="locale.Catalog" /></a>
<%--			<a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=en<%request.getSession().setAttribute("mapParam", request.getParameterMap());%><%request.getSession().setAttribute("servletPath", request.getServletPath());%>">ENG</a>--%>
<%--			<a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=ua<%request.getSession().setAttribute("mapParam", request.getParameterMap()); request.getSession().setAttribute("servletPath", request.getServletPath());%>">UKR</a>--%>
			<a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=en<%request.getSession().setAttribute("MyURL", request.getRequestURL().append('?').append(request.getQueryString()));%>">ENG</a>
			<a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=ua<%request.getSession().setAttribute("MyURL", request.getRequestURL().append('?').append(request.getQueryString()));%>">UKR</a>
		</div>
		<ul class="navbar-nav navbar-collapse justify-content-end">

			<%--            <li><a href="<%= request.getContextPath() %>/catalog/catalog_page.jsp" class="navbar-brand">Catalog</a></li>--%>
<%--			<li><a href="<%= request.getContextPath() %>/FrontController?command=CATALOG_COMMAND&action=showCard" class="navbar-brand"><fmt:message key="locale.Card" /></a></li>--%>
			<li><a href="<%= request.getContextPath() %>/FrontController?command=USER_CARD_COMMAND&action=showGoodsList" class="navbar-brand"><fmt:message key="locale.Card" /></a></li>
			<li><a href="<%= request.getContextPath() %>/register/register.jsp" class="navbar-brand"><fmt:message key="locale.SignUp" /></a></li>
			<li><a href="<%= request.getContextPath() %>/FrontController?command=LOGOUT_COMMAND" class="navbar-brand"><fmt:message key="locale.Login" /></a></li>
		</ul>
	</nav>
</header>