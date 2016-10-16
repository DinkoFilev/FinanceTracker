<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
				<c:if test="${error != null}">
					<div class="alert alert-danger">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong>Error!</strong>
						<c:out value="${error}"></c:out>
					</div>
				</c:if>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->




		<!--content-->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">


						<a href="accountform"><button type="submit"
								class="btn btn-primary">
								<msg:message code="add.new.account" />
							</button> </a>
						<c:set var="total1" scope="page" value="0" />
						<h4>
							<msg:message code="Total" />
							: <label id="total"></label>
						</h4>
						<table data-toggle="table" data-show-toggle="true"
							data-show-columns="true" data-search="true"
							data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="name" data-sort-order="desc">
							<thead>
								<tr>

									<th data-sortable="true"><msg:message code="name" /></th>
									<th data-sortable="true"><msg:message code="institution" /></th>
									<th data-sortable="true"><msg:message code="balance" /></th>
									<th data-sortable="true"><msg:message code="status" /></th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="account" items="${user.getAccounts()}">
									<tr>
										<td>${account.value.name}</td>
										<td>${account.value.institution}</td>
										<td class="text-right"><fmt:formatNumber type="NUMBER"
												maxFractionDigits="2" minFractionDigits="2"
												value="${account.value.balance}" /> <c:set var="total1"
												value="${total1 + account.value.balance}" scope="page" /></td>

										<td><input type="checkbox" class="form-control"
											onclick="return false" onkeydown="return false"
											<c:if test="${account.value.status}">checked="checked"</c:if> /></td>
										<td class="col-md-1">
											<form action="editaccount" method="post">
												<input type="hidden" name="action" value="edit"> <input
													type="hidden" name="accountId" value="${account.key}">
												<button class="btn btn-default btn-block btn-default"
													title="<msg:message code="edit" />">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
												</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="deleteaccount" method="post">
												<input type="hidden" name="action" value="delete"> <input
													type="hidden" name="accountId" value="${account.key}">
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
						<script>
							(function() {

								document.getElementById("total").innerHTML = "<fmt:formatNumber pattern="###,###,###,###.##" value="${total1}" type="NUMBER" />";
							})();
						</script>


						<a href="accountform"><button type="submit"
								class="btn btn-primary">
								<msg:message code="add.new.account" />
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