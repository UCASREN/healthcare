<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<p>
      Hello <b><c:out value="${pageContext.request.remoteUser}"/></b>
    </p>
	<c:url var="logoutUrl" value="/logout"/>
    <form class="form-inline" action="${logoutUrl}" method="post">
      <input type="submit" value="Log out" />
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

	<sec:authorize access="hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')">
		<p>Must have ROLE_ADMIN and ROLE_USER</p>
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
		<p>Must have ROLE_ADMIN or ROLE_USER</p>
	</sec:authorize>
	<sec:authorize access="!hasAnyRole('ROLE_ADMIN','ROLE_USER')">
		<p>Must not have ROLE_ADMIN or ROLE_USER</p>
	</sec:authorize>
</body>
</html>