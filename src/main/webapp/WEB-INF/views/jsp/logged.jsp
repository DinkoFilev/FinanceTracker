<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="stag" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Budgetbeat - ${pagename}</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/datepicker3.css" rel="stylesheet">
<link href="css/styles.css" rel="stylesheet">

<!--Icons-->
<script src="js/lumino.glyphs.js"></script>

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#sidebar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
			
		</button>
		<a class="navbar-brand" href="#"><span>Budget</span>Beat</a>
		<ul class="user-menu">
					<li class="dropdown pull-right"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"><svg
						class="glyph stroked male-user">
								<use xlink:href="#stroked-male-user"></use></svg>${user.firstName} ${user.lastName}<span
					class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="profile"><svg class="glyph stroked male-user">
										<use xlink:href="#stroked-male-user"></use></svg> Profile</a></li>
					<li><a href="settings"><svg class="glyph stroked gear">
										<use xlink:href="#stroked-gear"></use></svg> Settings</a></li>
					<li><a href="logout"><svg class="glyph stroked cancel">
										<use xlink:href="#stroked-cancel"></use></svg> Logout</a></li>
				</ul></li>
				
		</ul>	
		<a class="navbar-brand pull-right" href="?language=en"><img alt="" src="https://www.easterisland.travel/images/misc/flag-england-30x20.png"> </a>
		<a class="navbar-brand pull-right" href="?language=bg"><img alt="" src="http://www.rudi-2010.com/en/images/stories/rudi/flag_bulgaria_sm.gif"> </a>
			
	</div>
</div>
		<!-- /.container-fluid -->
	</nav>

	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<ul class="nav menu">
			<li><a href="dashboard"><svg
						class="glyph stroked dashboard-dial">
				<use xlink:href="#stroked-dashboard-dial"></use>
				</svg> Dashboard</a></li>
			<li><a href="reports"><svg
						class="glyph stroked line-graph">
				<use xlink:href="#stroked-line-graph"></use></svg> Reports</a></li>
			<li role="presentation" class="divider"></li>
			<li><a href="viewaccount"><svg class="glyph stroked chain">
				<use xlink:href="#stroked-chain" /></svg> Accounts</a></li>
			<li><a href="viewtag"><svg class="glyph stroked tag">
				<use xlink:href="#stroked-tag" /></svg> Tags</a></li>
			<li><a href="transactions"><svg class="glyph stroked star">
				<use xlink:href="#stroked-star" /></svg> Transactions</a></li>
			<li><a href="reminders"><svg class="glyph stroked calendar">
				<use xlink:href="#stroked-calendar" /></svg> Reminders</a></li>
		</ul>
	</div>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<c:import url="${model}"></c:import>  
		</div>
	<!--/.main-->

	<script>
		$('#calendar').datepicker({});

		!function($) {
			$(document)
					.on(
							"click",
							"ul.nav li.parent > a > span.icon",
							function() {
								$(this).find('em:first').toggleClass(
										"glyphicon-minus");
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
</body>

</html>