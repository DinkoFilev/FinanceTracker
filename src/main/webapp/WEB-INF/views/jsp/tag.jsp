<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="head_stuff.html"%>
<title>BudgetBeat - <msg:message code="tags" /></title>

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
				<li class="active"><msg:message code="tags" /></li>
			</ol>
		</div>
		<!--/.row-->
		<!--Your position on site-->


		<!--Title in the page-->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					<msg:message code="tags.manager" />
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
						<sf:form commandName="tag" role="form">
							<div class="form-group">
								<label><msg:message code="tag.name" /></label>
								<sf:input path="name" class="form-control" />
								<input type="submit" value="<msg:message code="add" />">
							</div>
						</sf:form>

						<c:if test="${not empty tags}">
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
									<c:forEach items="${tags}" var="current_tag">
										<tr>
											<td><c:out value="${current_tag.name}" /></td>
											<td><c:out value="${current_tag.parentId}" /></td>
											<td><form action="/Budgetbeat/edit_tag" method="post">
													<input type="hidden" name="editTagId"
														value="<c:out value="${current_tag.tagId}" />"> <input
														type="submit" value="Edit">
												</form></td>
										</tr>
									</c:forEach>
								</tbody>

							</table>
						</c:if>

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