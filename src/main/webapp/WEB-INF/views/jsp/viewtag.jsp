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
					<msg:message code="tags.manager" />
				</h1>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->




		<!--content-->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
					
						<a href="tagform"><button type="submit"
								class="btn btn-primary">
								<msg:message code="add.new.tag" />
							</button> </a>
						<table data-toggle="table" data-show-toggle="true"
							data-show-columns="true" data-search="true"
							data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="name" data-sort-order="desc">
							<thead>
								<tr>
									<th data-sortable="true"><msg:message code="tag.name" /></th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="tag" items="${user.getTags()}">
									<tr>
										<td>
											<form action="transactions_by_tag" method="post">
												<input type="hidden" name="tagId" value="${tag.value.tagId}">
												<input type="hidden" name="accountId" value="0">
												<button class="btn btn-default btn-block btn-default">
													${tag.value.name}</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="edittag" method="post">
												<input type="hidden" name="action" value="edit"> <input
													type="hidden" name="tagId" value="${tag.value.tagId}">
												<button class="btn btn-default btn-block btn-default"
													title="<msg:message code="edit" />">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
												</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="deletetag" method="post">
												<input type="hidden" name="action" value="delete"> <input
													type="hidden" name="tagId" value="${tag.value.tagId}">
												<button class="btn btn-default btn-block btn-default"
													title="<msg:message code="delete" />">
													<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
												</button>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<a href="tagform"><button type="submit"
								class="btn btn-primary">
								<msg:message code="add.new.tag" />
							</button> </a>
						<script>
							$(function() {
								$('#hover, #striped, #condensed')
										.click(
												function() {
													var classes = 'table';

													if ($('#hover').prop(
															'checked')) {
														classes += ' table-hover';
													}
													if ($('#condensed').prop(
															'checked')) {
														classes += ' table-condensed';
													}
													$('#table-style')
															.bootstrapTable(
																	'destroy')
															.bootstrapTable(
																	{
																		classes : classes,
																		striped : $(
																				'#striped')
																				.prop(
																						'checked')
																	});
												});
							});

							function rowStyle(row, index) {
								var classes = [ 'active', 'success', 'info',
										'warning', 'danger' ];

								if (index % 2 === 0
										&& index / 2 < classes.length) {
									return {
										classes : classes[index / 2]
									};
								}
								return {};
							}
						</script>
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