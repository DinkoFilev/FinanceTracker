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
<title>BudgetBeat - ADD TRANSACTION</title>
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/chart.min.js"></script>
<script src="js/chart-data.js"></script>
<script src="js/easypiechart.js"></script>
<script src="js/easypiechart-data.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<script src="js/bootstrap-table.js"></script>



<!-- Isolated Version of Bootstrap, not needed if your site already uses Bootstrap -->
<link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" />

<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<script>
    $(document).ready(function(){
      var date_input=$('input[name="date"]'); //our date input has the name "date"
      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      var options={
    	 format: "yyyy-mm-dd",
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
    })
</script>
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
					Transaction Manager
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
						<form:form method="post" commandname="command" action="savetransaction">

							<div class="form-group">
								<label>Account</label>
								<form:select path="ft_account_id" class="form-control required">			               
				                <c:forEach var="acc" items="${user.getAccounts()}">
				                    <form:option value="${acc.key}"><c:out value="${acc.value.getName()}"/></form:option>
				                </c:forEach>
				                </form:select>
							
							</div>

							<div class="form-group">
								<label>Tag</label>
								<form:select path="fk_tag_id" class="form-control required">				               
				                <c:forEach var="tag" items="${user.getTags()}">
				                    <form:option value="${tag.key}"><c:out value="${tag.value.getName()}"/></form:option>
				                </c:forEach>
				                </form:select>
							</div>

							<div class="form-group">
								<label>Description</label>
								<form:input path="description" class="form-control required" required="required" maxlength="49" />
							</div>
							<div class="form-group">
								<label>Amount</label>
								<form:input path="amount" class="form-control required" required="required" type="number"  step="0.01" min="0" max="99999999" />
							</div>
							      <div class="form-group"> <!-- Date input -->
							        <label class="control-label" for="date">Date</label>
							        <form:input class="form-control" path="date" id="date" name="date" placeholder="YYYY-MM-DD" type="date"/>
							      </div>
							 <div class="form-group">
								<label>Transaction type</label>
								<form:select path="income" class="form-control required">
								   <form:option value="false" label="Expense" />
								  <form:option value="true" label="Income" />				                
				                </form:select>
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

	
	
	
	
	<script>
		$('#calendar').datepicker({
		});

		!function ($) {
		    $(document).on("click","ul.nav li.parent > a > span.icon", function(){          
		        $(this).find('em:first').toggleClass("glyphicon-minus");      
		    }); 
		    $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
	</script>	
</body>

</html>
