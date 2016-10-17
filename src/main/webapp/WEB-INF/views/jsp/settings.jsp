<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags"%>

<%
	response.addHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.addHeader("Pragma", "no-cache");
	response.addDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="head_stuff.html"%>
<title>BudgetBeat - ADD TRANSACTION</title>
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/chart.min.js"></script>
<script src="js/chart-data.js"></script>
<script src="js/easypiechart.js"></script>
<script src="js/easypiechart-data.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<script src="js/bootstrap-table.js"></script>

</head>

<body>

	<div>
		<!--Your position on site-->
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="logged"><svg class="glyph stroked home">
							<use xlink:href="#stroked-home"></use></svg></a></li>
				<li class="active">${title}</li>
			</ol>
		</div>
		<!--/.row-->
		<!--Your position on site-->

		<!--Title in the page-->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">User Settings</h1>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->

		<!--content-->
		<div class="row">
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-body">
						<form:form method="post" commandname="command"
							action="editsettings">
							<div class="form-group">
								<label>First Name</label>
								<form:input path="firstName" class="form-control required"
									required="required" maxlength="49" />
							</div>

							<div class="form-group">
								<label>Last Name</label>
								<form:input path="lastName" class="form-control required"
									required="required" maxlength="49" />
							</div>
							<div class="form-group">
								<label>E-mail</label>
								<form:input type="email" path="email"
									class="form-control required" required="required"
									maxlength="49" />
								
							</div>
							<div class="form-group">
								<label>Current Password</label>
								<form:input type="password" path="password"
									class="form-control required" required="required"
									maxlength="49" />
							</div>
							<div class="form-group">
								<label>New password</label> <input type="password"
									id="newpassword" name="newpassword"
									class="form-control required" required="required"
									maxlength="49" />

							</div>
							<div class="form-group">
								<label>Repeat new password</label> <input type="password"
									id="repeatpassword" name="repeatpassword"
									class="form-control required" required="required"
									maxlength="49" />
								<c:if test="${status != null}">

									<div class="alert alert-danger">
										<a href="#" class="close" data-dismiss="alert"
											aria-label="close">&times;</a>
									<c:out value="${status}"></c:out>
									</div>

								</c:if>

							</div>

							<div class="form-group ">
								<button type="submit" class="btn btn-primary">
									<msg:message code="save" />
								</button>
							</div>

						</form:form>
						<div class="form-group">
						<a href = "dashboard"><button type="submit"  class="btn btn-primary">Cancel
												</button></a>
								
							</div>
					</div>
				</div>
			</div>
		</div>

		<!--/content-->
	</div>

	<!--/.main-->






</body>

</html>
