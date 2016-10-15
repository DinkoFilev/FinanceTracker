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
                                                              <c:forEach items="${graph}" var="entry">
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
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body">
						<div id="chart_div">${list}</div>
					</div>
				</div>
			</div>
		</div>
		<!--/.row-->
		<!--/Title in the page-->

		<!--Title in the page-->
		<div class="row row-eq-height">
			<div class="col-xs-6">
				<div class="panel panel-default">
					<div class="panel-body table-responsive"">
						<table class="table">
							<tr>
								<td>
								<a href="transactionform"><button type="submit"
											class="btn btn-primary">
											<msg:message code="add.new.transaction" />
										</button> </a>
								</td><td>
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
			<div class="col-xs-3">
				<div class="panel panel-default">
					<div class="panel-body"></div>
				</div>
			</div>
			<div class="col-xs-3">
				<div class="panel panel-default">
					<div class="panel-body">
						<div id="reportrange" class="pull-right"
							style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
							<i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;

							<span></span> <b class="caret"></b>
						</div>

						<form action="transactions_by_tag" method="post">

							<input id="from" name="from" value=""> <input id="to"
								name="to" value=""> <input type="hidden" name="tagId"
								value="${tag.tagId}">
							<button class="btn btn-default btn-block btn-default"
								title="<msg:message code="edit" />">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							</button>
						</form>




					</div>

				</div>
			</div>
		</div>
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
									<th data-sortable="true">Account</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="transaction" items="${transactions}">
									<tr>
										<td>
											<button type="button"
												class="btn btn-default btn-block btn-default">${transaction.description}</button>
										</td>

										<td>
											<button type="button"
												class="btn btn-default btn-block btn-default">${transaction.amount}</button>
										</td>

										<td>
											<button type="button"
												class="btn btn-default btn-block btn-default">${user.getAccount(transaction.ft_account_id).name}</button>
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
        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        
        $('#from').val(start.format('MMMM D, YYYY')) ; 
        $('#to').val(end.format('MMMM D, YYYY')) ; 
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