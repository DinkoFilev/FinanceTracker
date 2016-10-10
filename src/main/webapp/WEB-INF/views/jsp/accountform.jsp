<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="head_stuff.html"%>
<title>BudgetBeat - ${title}</title>

</head>

<body>
	<!--Top right navigation-->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<%@include file="top_nav.html"%>
	</nav>
	<!--/Top right navigation-->


	<!--Left navigation-->
	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<%@include file="navigation.html"%>
	</div>
	<!--/Left navigation-->


	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<!--Your position on site-->
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="index.jsp"><svg class="glyph stroked home">
							<use xlink:href="#stroked-home"></use></svg></a></li>
				<li class="active">${title}</li>
			</ol>
		</div>
		<!--/.row-->
		<!--Your position on site-->

		<!--Title in the page-->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					<msg:message code="accounts.manager" />
				</h1>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->

		<!--content-->
		<div class="row">
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-body">
						<form:form method="post" action="saveaccount">

							<div class="form-group">
								<label><msg:message code="account.name" /></label>
								<form:input path="name" class="form-control required" required="required" maxlength="44" />
							</div>

							<div class="form-group">
								<label><msg:message code="institution" /></label>
								<form:input path="institution" class="form-control required" required="required" maxlength="49" />
							</div>

							<div class="form-group">
								<label><msg:message code="balance" /></label>
								<form:input path="balance" class="form-control required" required="required" type="number"  min="0" max="99999999" />
							</div>
							<div class="form-group">
								<label><msg:message code="status" /></label>
								<form:checkbox path="status" class="form-control" />
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-primary">
									<msg:message code="save" />
								</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

		<!--/content-->
	</div>
	<!--/.main-->

	<%@include file="end_scripts.html"%>
</body>

</html>
