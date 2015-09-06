<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>用户界面</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="resources/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="resources/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="resources/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="resources/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css"
	href="resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />

<link rel="stylesheet" type="text/css"
	href="resources/plugins/clockface/css/clockface.css" />
<link rel="stylesheet" type="text/css"
	href="resources/plugins/bootstrap-datepicker/css/datepicker3.css" />
<link rel="stylesheet" type="text/css"
	href="resources/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" />
<link rel="stylesheet" type="text/css"
	href="resources/plugins/bootstrap-colorpicker/css/colorpicker.css" />
<link rel="stylesheet" type="text/css"
	href="resources/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="resources/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />

<link rel="stylesheet" type="text/css"
	href="resources/plugins/jstree/dist/themes/default/style.min.css" />

<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="resources/css/components.css" id="style_components"
	rel="stylesheet" type="text/css" />
<link href="resources/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="resources/css/layout.css" rel="stylesheet" type="text/css" />
<link id="style_color" href="resources/css/darkblue.css"
	rel="stylesheet" type="text/css" />
<link href="resources/css/custom.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
</head>
<!-- BEGIN BODY -->
<body
	class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo">
	<!-- BEGIN HEADER -->
	<div class="page-header -i navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner">
			<!-- BEGIN LOGO -->
			<div class="page-logo">
				<a href="index.html"> <img src="img/logo.png" alt="logo"
					class="logo-default" />
				</a>
				<div class="menu-toggler sidebar-toggler"></div>
			</div>
			<!-- END LOGO -->
			<!-- BEGIN HORIZANTAL MENU -->
			<div class="hor-menu hor-menu-light hidden-sm hidden-xs">
				<ul class="nav navbar-nav">
					<li class="classic-menu-dropdown "><a href="userdatabaseview"> 数据发布 </a></li>
					<li class="classic-menu-dropdown"><a href="#"> 数据申请 </a></li>
					<li class="classic-menu-dropdown"><a href="#"> 虚拟环境申请 </a></li>
					<li class="classic-menu-dropdown"><a href="#"> 数据分析 </a></li>
				</ul>
			</div>
			<!-- END HORIZANTAL MENU -->
			<!-- BEGIN HEADER SEARCH BOX -->
			<form class="search-form" action="#" method="GET">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search..."
						name="query"> <span class="input-group-btn"> <a
						href="javascript:;" class="btn submit"><i
							class="icon-magnifier"></i></a>
					</span>
				</div>
			</form>
			<!-- END HEADER SEARCH BOX -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->

		</div>
		<!-- END HEADER INNER -->
	</div>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<div class="page-sidebar navbar-collapse collapse">
				<!-- BEGIN SIDEBAR MENU1 -->
				<ul class="page-sidebar-menu hidden-sm hidden-xs"
					data-auto-scroll="true" data-slide-speed="200" id="alldatabaselist">

				</ul>
				<!-- END SIDEBAR MENU1 -->

			</div>
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<!-- BEGIN EXAMPLE TABLE PORTLET-->
				<!-- Begin: life time stats -->
				<label class="checkbox-inline" style="padding-left: 0px;"> <i
					class="fa fa-database"></i> 数据库
				</label> <label class="checkbox-inline" style="padding-left: 0px;">
					<select id="database" class="form-control select2me"
					data-placeholder="Select..." style="min-width: 100px;">
						<option value="">请选择一个数据库...</option>
				</select>
				</label> <label class="checkbox-inline" style="padding-left: 0px;">
					<i class="fa fa-table"></i> 表
				</label> <label class="checkbox-inline" style="padding-left: 0px;">
					<select id="table" class="form-control select2me"
					data-placeholder="Select..." style="min-width: 100px;">
						<option value="">请选择数据库下的表...</option>
				</select>
				</label>
				<p></p>
				<div  id="showdatabaseinfo" style="display:none;">
					<div class="row">
						<span id="showdatabaseinfo_name"></span>
						<span id="showdatabaseinfo_comments"></span>
						<span id="showdatabaseinfo_others"></span>
						<span id="showdatabaseinfo_tablenumber"></span>
					</div>
					<p></p>
					<div class="row">
						<div class="col-md-7">
						<div class="table-container">
							<div class="table-actions-wrapper">
								<span> </span>
							</div>

							<table class="table table-striped table-bordered table-hover"
								id="datatable_ajax_database">
								<thead>
									<tr role="row" class="heading">
										<th width="2%"><input type="checkbox"
											class="group-checkable"></th>
										<th width="5%">编号</th>
										<th width="15%">名称</th>
										<th width="15%">备注</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						</div>
						<div class="col-md-5">
						</div>
					</div>
				</div>
				<div  id="showtableinfo" style="display:none;">
					<div class="row">

						<span id="showtableinfo_name"></span>
						<span id="showtableinfo_comments"></span>
						<span id="showtableinfo_others"></span>
						<span id="showtableinfo_fieldnumber"></span>

					</div>
					<p></p>
					<div class="row">'
						<div class="col-md-7">
						<div class="table-container">
							<div class="table-actions-wrapper">
								<span> </span>
							</div>

							<table class="table table-striped table-bordered table-hover"
								id="datatable_ajax">
								<thead>
									<tr role="row" class="heading">
										<th width="2%"><input type="checkbox"
											class="group-checkable"></th>
										<th width="5%">编号</th>
										<th width="15%">名称</th>
										<th width="15%">备注</th>
										<th width="10%">最小值</th>
										<th width="10%">最大值</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						</div>
						<div class="col-md-5">
						</div>
					</div>
				</div>
				<!-- End: life time stats -->

				<!-- END EXAMPLE TABLE PORTLET-->

			</div>
		</div>
		<!-- END CONTENT -->

	</div>
	<!-- END CONTAINER -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="resources/js/respond.min.js"></script>
<script src="resources/js/excanvas.min.js"></script> 
<![endif]-->
	<script src="resources/js/jquery.min.js" type="text/javascript"></script>
	<script src="resources/js/jquery-migrate.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="resources/plugins/jquery-ui/jquery-ui.min.js"
		type="text/javascript"></script>
	<script src="resources/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="resources/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="resources/js/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="resources/js/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="resources/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="resources/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script type="text/javascript"
		src="resources/plugins/select2/select2.min.js"></script>
	<script type="text/javascript"
		src="resources/plugins/datatables/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>

	<script type="text/javascript"
		src="resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript"
		src="resources/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js"></script>
	<script type="text/javascript"
		src="resources/plugins/clockface/js/clockface.js"></script>
	<script type="text/javascript"
		src="resources/plugins/bootstrap-daterangepicker/moment.min.js"></script>
	<script type="text/javascript"
		src="resources/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
	<script type="text/javascript"
		src="resources/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js"></script>
	<script type="text/javascript"
		src="resources/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="resources/plugins/jstree/dist/jstree.min.js"></script>
	<script src="resources/js/databasemanager/databasemanager_metronic.js"
		type="text/javascript"></script>
	<script src="resources/js/databasemanager/userdatabaseview_layout.js"
		type="text/javascript"></script>
	<script src="resources/js/quick-sidebar.js" type="text/javascript"></script>
	<script src="resources/js/demo.js" type="text/javascript"></script>
	<script src="resources/js/components-pickers.js" type="text/javascript"></script>
	<script src="resources/js/databasemanager/datatable.js"
		type="text/javascript"></script>
	<script src="resources/js/databasemanager/userdatabaseview.js"
		type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout
			QuickSidebar.init(); // init quick sidebar
			Demo.init(); // init demo features
			ComponentsPickers.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>