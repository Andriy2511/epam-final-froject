<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value= "${sessionScope.lang}"/>
<fmt:setBundle basename="locale" />
<%@ taglib tagdir="/WEB-INF/tags" prefix="tf" %>
<header>
	<nav class="navbar navbar-expand-md navbar-dark"
		 style="background-color: tomato">
		<div>
			<a href="<%= request.getContextPath() %>/FrontController?command=USER_ORDER_COMMAND" class="navbar-brand"><fmt:message key="locale.MyOrders" /></a>
			<a href="<%= request.getContextPath()%>/FrontController?command=CATALOG_COMMAND&action=showGoodsList" class="navbar-brand"><fmt:message key="locale.Catalog" /></a>
			<tf:language/>
		</div>
		<ul class="navbar-nav navbar-collapse justify-content-end">
			<li><a href="<%= request.getContextPath() %>/FrontController?command=USER_CARD_COMMAND&action=showGoodsList" class="navbar-brand"><fmt:message key="locale.Card" /></a></li>
			<li><a href="<%= request.getContextPath() %>/register/register.jsp" class="navbar-brand"><fmt:message key="locale.SignUp" /></a></li>
			<li><a href="<%= request.getContextPath() %>/FrontController?command=LOGOUT_COMMAND" class="navbar-brand"><fmt:message key="locale.Login" /></a></li>
		</ul>
	</nav>
</header>