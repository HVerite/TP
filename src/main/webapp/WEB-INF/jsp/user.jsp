<%@ include file="/WEB-INF/jsp/init.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page session="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form modelAttribute="user">
		<form:input path="name" size="15" maxlength="100" />
		<form:errors path="name" />
		<br />
		<form:input path="login" size="15" maxlength="100" />
		<form:errors path="login" />
		<br />
		<form:input path="password" type="password" size="15" maxlength="100" />
		<form:errors path="password" />
		<br />
		<input type="submit" value="Save Changes" />
	</form:form>
</body>
</html>