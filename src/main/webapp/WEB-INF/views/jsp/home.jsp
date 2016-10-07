<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

		<form action="form" method="GET">
			First name : <input type="text" name = "firstName" ><br>
			Last name : <input type="text" name = "lastName" ><br>
			E-mail : <input type="email" name = "email" ><br>
			Password : <input type="password" name = "password" >
			
			<input type="submit" value="SUBMIT">
		
		</form>
</body>
</html>
