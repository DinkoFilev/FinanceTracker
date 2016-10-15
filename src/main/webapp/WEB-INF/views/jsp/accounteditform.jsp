<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags"%>
<%
response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
response.addHeader("Pragma", "no-cache"); 
response.addDateHeader ("Expires", 0);
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="head_stuff.html"%>
<title>BudgetBeat - ${title}</title>

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
						<form:form method="POST" action="/Budgetbeat/editsaveaccount">
							<form:hidden path="accountId" />

							<div class="form-group">
								<label><msg:message code="account.name" /></label>
								<form:input path="name" class="form-control" maxlength="44"/>
																<c:if test="${error != null}">
									
										<div class="alert alert-danger">
											<a href="#" class="close" data-dismiss="alert"
												aria-label="close">&times;</a> <strong>Error!</strong>
											<c:out value="${error}"></c:out>
										</div>
									
								</c:if>
							</div>

							<div class="form-group">
								<label><msg:message code="institution" /></label>
								<form:input path="institution" class="form-control" maxlength="49"/>
							</div>

							<div class="form-group">
								<label><msg:message code="balance" /></label>
								<form:input path="balance" class="form-control" type="number" step="any" min="0" max="99999999"/>
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