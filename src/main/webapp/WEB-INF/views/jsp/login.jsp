<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="stag" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@include file="head_stuff.html"%>
</head>
<body>
<body>
	
	
		<div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">Log in</div>
				<div class="panel-body">
					<sform:form action="login" role="form">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="E-mail" id="email" name="email" type="email" value="${email}" autofocus="">
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Password" name="password" type="password" value="">
							</div>
							
						
							
						</fieldset>
							<input class="btn btn-primary" type="submit" value="LOGIN">
							<a href="register">
   					<input class="btn btn-primary" type="button" value="REGISTER" />
					</a>
							<c:if test="${status != null}">
									
										<div class="alert alert-danger">
											<a href="#" class="close" data-dismiss="alert"
												aria-label="close">&times;</a>
											<c:out value="${status}"></c:out>
										</div>
									
								</c:if>
					</sform:form>
					
					
				</div>
			</div>
		</div><!-- /.col-->
	
	
		

	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/chart.min.js"></script>
	<script src="js/chart-data.js"></script>
	<script src="js/easypiechart.js"></script>
	<script src="js/easypiechart-data.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
	<script>
		!function ($) {
			$(document).on("click","ul.nav li.parent > a > span.icon", function(){		  
				$(this).find('em:first').toggleClass("glyphicon-minus");	  
			}); 
			$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
	</script>	
</body>

</body>
</html>