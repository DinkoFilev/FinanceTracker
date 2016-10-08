<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<li class="active">Accounts</li>
				<li class="active"><msg:message code="accounts.page.title" /></li>
			</ol>
		</div>
		<!--/.row-->
		<!--Your position on site-->


		<!--Title in the page-->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					<msg:message code="accounts.page.title" />
				</h1>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->

		<!--content-->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<msg:message code="accounts.manager" />
					</div>
					<div class="panel-body">

						<sf:form method="POST" commandName="account" role="form">
							<div class="form-group">
								<label><msg:message code="account.name" /></label>
								<sf:input path="name" class="form-control" />
							</div>

							<div class="form-group">
								<label><msg:message code="institution.name" /></label>
								<sf:input path="institution" class="form-control" />
							</div>

							<div class="form-group">
								<label><msg:message code="balance" /></label>
								<sf:input path="balance" class="form-control" />
							</div>

							<div class="form-group">
								<label><msg:message code="status" /></label>
								<sf:checkbox path="status" class="form-control" />
							</div>


							<input type="submit" value="<msg:message code="add" />">

						</sf:form>
						
						<c:if test="${not empty accounts}">
							<table id="datatable" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th data-field="name" data-sortable="true"><msg:message
												code="tag.name" /></th>
										<th data-field="parenrId" data-sortable="true"><msg:message
												code="parent.tag" /></th>
										<th><msg:message code="actions" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${accounts}" var="current_account">
										<tr>
											<td><c:out value="${current_account.name}" /></td>
											<td><c:out value="${current_account.balance}" /></td>
											<td><c:out value="${current_account.status}" /></td>
											<td><a
												href="tags/edit/<c:out	value="${current_account.accountId}" />">Edit</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>

							</table>
						</c:if>

						<table data-toggle="table" data-url="tables/data1.json"
							data-show-refresh="true" data-show-toggle="true"
							data-show-columns="true" data-search="true"
							data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="name" data-sort-order="desc">
							<thead>
								<tr>
									<th data-field="state" data-checkbox="true">Item ID</th>
									<th data-field="id" data-sortable="true">Item ID</th>
									<th data-field="name" data-sortable="true">Item Name</th>
									<th data-field="price" data-sortable="true">Item Price</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		<!--/.row-->
		<!--/content-->
	</div>
	<!--/.main-->

	<%@include file="end_scripts.html"%>
</body>

</html>