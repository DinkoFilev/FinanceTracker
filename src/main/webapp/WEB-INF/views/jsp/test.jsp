<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Chart - Servlet 3</title>

</head>
<body>
	<form action="transactions_by_tag" method="post">

		<input id="from" name="from" value="asd"> <input id="to"
			name="to" value="asd"> <input type="hidden" name="action"
			value="edit"> 
		<button class="btn btn-default btn-block btn-default"
			title="<msg:message code="edit" />">
			<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
		</button>
	</form>
</body>
</html>