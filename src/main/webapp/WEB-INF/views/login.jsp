<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8"%>

<html>
<head>
<title>Login</title>
</head>

<body>
	<h1>Login</h1>
	<form action="${loginUrl}" method="post">
		<p>
			<label for="username">User:</label>
		</p>
		<input type="text" id="username" name="username" />

		<p>
			<label for="password">Password:</label>
		</p>
		<input type="password" id="password" name="password">

		<p>
			<label for="remember-me">Remember Me(Implemented,but not security enough)</label>
		</p>
		<input type="checkbox" id="remember-me"
			name="remember-me" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div>
			<input name="submit" type="submit" />
		</div>
	</form>

</body>
</html>