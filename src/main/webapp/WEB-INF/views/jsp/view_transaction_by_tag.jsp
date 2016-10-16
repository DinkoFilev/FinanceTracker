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


<title>BudgetBeat - ${title}</title>

<link href="css/bootstrap-table.css" rel="stylesheet">
<script src="js/lumino.glyphs.js"></script>
<!-- Include Required Prerequisites -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
<script type="text/javascript"
	src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>

<!-- Include Date Range Picker -->
<script type="text/javascript"
	src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />


<script type="text/javascript" src="js/jsapi.js"></script>

<script type="text/javascript">

google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart1);
function drawChart1() {
  var dataIncome = google.visualization.arrayToDataTable([
                                                    ['Transactions', 'Amounth'],
                                                    <c:forEach items="${graphIncome}" var="entry">
                                                        [ '${entry.key}', ${entry.value} ],
                                                    </c:forEach>
                                              ]);

  var options = {
		  is3D : true,
          pieSliceText: 'label',
          tooltip :  {showColorCode: true},
  
      
          height: "280",

  chartArea: {
	  left:"5%",
      top: "0%",
      height: "280"
    }
  
  
 };

  var chartIncome = new google.visualization.PieChart(document.getElementById('chart_div_income'));  
  chartIncome.draw(dataIncome, options);
  }


google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart2);
function drawChart2() {
  var dataExpense = google.visualization.arrayToDataTable([
                                                    ['Transactions', 'Amounth'],
                                                    <c:forEach items="${graphExpense}" var="entry">
                                                        [ '${entry.key}', ${entry.value} ],
                                                    </c:forEach>
                                              ]);

  var options = {
		  is3D : true,
          pieSliceText: 'label',
          tooltip :  {showColorCode: true},
  
        
          
          height: "280",

          chartArea: {
        	  left:"10%",
               height: "500",
              width:"500"}
         };

  var chartExpense = new google.visualization.PieChart(document.getElementById('chart_div_expense'));  
  chartExpense.draw(dataExpense, options);
  }



google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart3);
function drawChart3() {
  var dataCompare = google.visualization.arrayToDataTable([
                                                    ['Transactions', 'Amounth'],
                                                    <c:forEach items="${graphCompare}" var="entry">
                                                        [ '${entry.key}', ${entry.value} ],
                                                    </c:forEach>
                                              ]);

  var options = {
		  is3D : true,
          pieSliceText: 'label',
          tooltip :  {showColorCode: true},
  
 
  height: "280",

  chartArea: {
	  left:"10%",
       height: "500",
      width:"500"}
 };

  var chartCompare = new google.visualization.PieChart(document.getElementById('chart_div_compare'));  
  chartCompare.draw(dataCompare, options);
  }



$(window).resize(function(){
	  drawChart1();
	  drawChart2();
	  drawChart3();
	});
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
			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="tabbable-panel">
						<div class="tabbable-line">
							<ul class="nav nav-tabs nav-justified">
								<li class="active"><a role="presentation" data-toggle="tab"
									href="#incomeTab">Income</a></li>
								<li><a role="presentation" data-toggle="tab"
									href="#expenseTab">Expense</a></li>
								<li><a role="presentation" data-toggle="tab"
									href="#compareTab">Compare</a></li>
							</ul>
						</div>
						<div class="tabbable-panel">
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane fade in active"
									id="incomeTab">
									<div id="chart_div_income"></div>
								</div>
								<div role="tabpanel" class="tab-pane" id="expenseTab">
									<div id="chart_div_expense"></div>
								</div>
								<div role="tabpanel" class="tab-pane fade in" id="compareTab">
									<div id="chart_div_compare"></div>

								</div>
							</div>

						</div>
					</div>
				</div>
			</div>

			<div class="col-lg-6">
				<div class="panel panel-default">
					<div class="panel-body table-responsive">
						<table class="table">
							<tr>
								<td><a href="transactionform"><button type="submit"
											class="btn btn-primary">
											<msg:message code="new.transaction" />
										</button> </a></td>
								<td>
									<h4>
										<msg:message code="INCOME" />
										:
									</h4>

								</td>
								<td><h4>${income}</h4></td>
								<th></th>
							</tr>
							<tr>
								<td></td>
								<td>
									<h4>
										<msg:message code="EXPENSE" />
										:
									</h4>
								</td>
								<td><h4>${expence}</h4></td>

							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="panel panel-default">

					<div class="container">

						<h2>Search</h2>
					</div>
					<div class="panel-body">
						<div id="reportrange" class="pull-right"
							style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
							<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;
							<span></span> <b class="caret"></b>
						</div>
					</div>
					<div class="panel-body">
						<form action="transactions_by_tag" method="post"
							class="form-inline">
							<div class="form-group">
								<label for="accountId">Account</label> <select name='accountId'
									class="form-control" id="accountId">
									<option value="0">All</option>
									<c:forEach items="${user.getAccounts()}" var="role">
										<option
											<c:if test="${role.key == accountId}">selected="selected"</c:if>
											value="${role.key}">${role.value.getName()}</option>
									</c:forEach>


								</select>
							</div>


							<div class="form-group">
								<label for="tagId">Tag</label> <select name='tagId'
									class="form-control" id="tadId">
									<option value="0">All</option>
									<c:forEach items="${user.getTags()}" var="role">
										<option
											<c:if test="${role.key==tagId}">selected="selected"</c:if>
											value="${role.key}">${role.value.getName()}</option>
									</c:forEach>
								</select>
							</div>

							<input type="hidden" id="fromDate" name="fromDate" value="">
							<input type="hidden" id="toDate" name="toDate" value="">


							<button class="btn btn-info" title="<msg:message code="edit" />">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>Search
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->

		<!--Title in the page-->

		<!--/.row-->
		<!--/Title in the page-->





		<!--content-->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">


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
								<c:forEach var="trans" items="${transactions}">

									<tr>
										<td>
											<form>
												<input type="hidden" name="transactionId"
													value="${trans.getTransaction_id()}">
												<button type="reset"
													class="btn btn-default btn-block btn-default">
													${trans.description}</button>
											</form>
										</td>
										<td>
											<form>
												<input type="hidden" name="transactionId"
													value="${trans.getTransaction_id()}">
												<button type="reset" class="btn btn-default btn-block">
													<fmt:formatNumber type="number" maxFractionDigits="2"
														minFractionDigits="2" value="${trans.amount}" />
												</button>
											</form>
										</td>
										<td>
											<form>
												<input type="hidden" name="transactionId"
													value="${trans.getTransaction_id()}">
												<button type="reset" class="btn btn-default btn-block">
													${trans.date}</button>
											</form>
										</td>
										<td>
											<form action="transactions_by_tag" method="post">
												<input type="hidden" name="tagId" value="0"> <input
													type="hidden" name="accountId"
													value="${trans.getFt_account_id()}">
												<button class="btn btn-default btn-block btn-default">
													${user.getAccount(trans.getFt_account_id()).getName()}</button>
											</form>
										</td>
										<td>
											<form action="transactions_by_tag" method="post">
												<input type="hidden" name="tagId"
													value="${trans.getFk_tag_id()}"> <input
													type="hidden" name="accountId" value="0">
												<button class="btn btn-default btn-block btn-default">
													${user.getTag(trans.getFk_tag_id()).getName()}</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="edittransaction" method="post">
												<input type="hidden" name="action" value="edit"> <input
													type="hidden" name="transactionId"
													value="${trans.getTransaction_id()}">
												<button class="btn btn-default btn-block btn-default"
													title="<msg:message code="edit" />">
													<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
												</button>
											</form>
										</td>
										<td class="col-md-1">
											<form action="deletetransaction" method="post">
												<input type="hidden" name="action" value="delete"> <input
													type="hidden" name="transactionId"
													value="${trans.getTransaction_id()}">
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
								class="btn btn-primary">
								<msg:message code="add.new.transaction" />
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


	<script src="js/bootstrap.min.js"></script>
	<script src="js/chart.min.js"></script>
	<script src="js/chart-data.js"></script>
	<script src="js/easypiechart.js"></script>
	<script src="js/easypiechart-data.js"></script>

	<script src="js/bootstrap-table.js"></script>
	<script>
	!function($) {
		$(document).on("click", "ul.nav li.parent > a > span.icon", function() {
			$(this).find('em:first').toggleClass("glyphicon-minus");
		});
		$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
	}(window.jQuery);

	$(window).on('resize', function() {
		if ($(window).width() > 768)
			$('#sidebar-collapse').collapse('show')
	})
	$(window).on('resize', function() {
		if ($(window).width() <= 767)
			$('#sidebar-collapse').collapse('hide')
	})
</script>
	<script type="text/javascript">
$(function() {

    var start = moment().subtract(29, 'days');
    var end = moment();

    function cb(start, end) {
        $('#reportrange span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
        
        $('#fromDate').val(start.format('YYYY-MM-DD')) ; 
        $('#toDate').val(end.format('YYYY-MM-DD')) ; 
    }
    
    

    $('#reportrange').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
           'Today': [moment(), moment()],
           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
           'This Month': [moment().startOf('month'), moment().endOf('month')],
           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, cb);

    cb(start, end);
    
});
</script>
</body>

</html>