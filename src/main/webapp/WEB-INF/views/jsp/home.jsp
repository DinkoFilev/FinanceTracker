<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="stag" uri="http://www.springframework.org/tags"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

		<sform:form commandName="register" role="form">
			First name : <input type="text" name = "firstName" ><br>
			Last name : <input type="text" name = "lastName" ><br>
			E-mail : <input type="email" name = "email" ><br>
			Password : <input type="password" name = "password" >
			
			<input type="submit" value="REGISTER">
		
		</sform:form>
		<h1>${status}</h1>
		
		<a href="tags">Tags</a>
</body>
</html>
