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
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
    // Load the Visualization API and the piechart package.
    google.load('visualization', '1.0', {
        'packages' : [ 'corechart' ]
    });
 
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawChart);
 
    // Callback that creates and populates a data table,
    // instantiates the pie chart, passes in the data and
    // draws it.
    function drawChart() {
 
        // Create the data table.    
        var data = google.visualization.arrayToDataTable([
                                                              ['Transactions', 'Amounth'],
                                                              <c:forEach items="${list}" var="entry">
                                                                  [ '${entry.key}', ${entry.value} ],
                                                              </c:forEach>
                                                        ]);
        // Set chart options
        var options = {
                       is3D : true,
            pieSliceText: 'label',
            tooltip :  {showColorCode: true},
                     'height' : 400

        };
 
        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
</script>
</head>

<body>


	<div>
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
					<msg:message code="transactions.by" />
					${tagName}
				</h1>

			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->

		<!--Title in the page-->
		<div class="row">
			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="form-group">
								<label>Account</label>
								<form:select path="ft_account_id" class="form-control required">
				                <form:option value="0" label="---Account---" />
				                <c:forEach var="acc" items="${user.getAccounts()}">
				                    <form:option value="${acc.key}"><c:out value="${acc.value.getName()}"/></form:option>
				                </c:forEach>
				                </form:select>
							
							</div>
					</div>
				</div>
			</div>
						<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="form-group">
								<label>Tag</label>
								<form:select path="fk_tag_id" class="form-control required">
				                <form:option value="0" label="---Tag---" />
				                <c:forEach var="tag" items="${user.getTags()}">
				                    <form:option value="${tag.key}"><c:out value="${tag.value.getName()}"/></form:option>
				                </c:forEach>
				                </form:select>
							</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<div id="chart_div">${list}</div>
					</div>
				</div>
			</div>
		</div>
		<!--/.row-->


							









		<!--content-->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<a href="transactionform"><button type="submit"
								class="btn btn-primary">ADD NEW TRANSACTION</button> </a>
						<table data-toggle="table" data-show-toggle="true"
							data-show-columns="true" data-search="true"
							data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="name" data-sort-order="desc">
							<thead>
								<tr>
									<th data-sortable="true">Description</th>
									<th data-sortable="true">Amount</th>
									<th data-sortable="true">Date</th>
									<th data-sortable="true">Account name</th>
									<th data-sortable="true">Tag name</th>
									<th data-sortable="true">Edit</th>
									<th data-sortable="true">Delete</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="trans" items="${user.getTransactions()}">
								
									<tr>
										<td>
											<form action="#" method="get">
												<input type="hidden" name="transactionId"
													value="${trans.key}">
												<button class="btn btn-default btn-block btn-default">
													${trans.value.description}</button>
											</form>
										</td>
										<td>
											<form action="#" method="get">
												<input type="hidden" name="transactionId"
													value="${trans.key}">
													<button class="btn btn-default btn-block btn-default">
												<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
													value="${trans.value.amount}"/></button>
											</form>
										</td>
										<td>
											<form action="#" method="get">
												<input type="hidden" name="transactionId"
													value="${trans.key}">
												<button class="btn btn-default btn-block btn-default">
													${trans.value.date}</button>
											</form>
										</td>
										<td>
											<form action="#" method="post">
												<input type="hidden" name="transactionId"
													value="${trans.key}">
												<button class="btn btn-default btn-block btn-default">
													${user.getAccount(trans.value.getFt_account_id()).getName()}</button>
											</form>
										</td>
										<td>
											<form action="#" method="post">
												<input type="hidden" name="transactionId"
													value="${trans.key}">
												<button class="btn btn-default btn-block btn-default">
													${user.getTag(trans.value.getFk_tag_id()).getName()}</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="edittransaction" method="post">
												<input type="hidden" name="action" value="edit"> <input
													type="hidden" name="transactionId" value="${trans.key}">
												<button class="btn btn-default btn-block btn-default"
													title="<msg:message code="edit" />">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
												</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="deletetransaction" method="post">
												<input type="hidden" name="action" value="delete"> <input
													type="hidden" name="transactionId" value="${trans.key}">
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
						<a href="transactionform"><button type="submit"
								class="btn btn-primary">ADD NEW TRANSACTION</button> </a>
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