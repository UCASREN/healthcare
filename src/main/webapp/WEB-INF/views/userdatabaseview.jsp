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
<title>Membership</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<link rel="icon" href="home/icons/home/logo.ico" type="image/x-icon" />

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
<link href="resources/css/plugins.css" rel="stylesheet" type="text/css" />
<link href="resources/css/layout.css" rel="stylesheet" type="text/css" />
<link id="style_color" href="resources/css/themes/light2.css"
	rel="stylesheet" type="text/css" />
<link href="resources/css/custom.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style-shop.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/components.css" id="style_components"
	rel="stylesheet" type="text/css" />
<link href="resources/css/docs.min.css"
	rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<!-- BEGIN DIV STYLES -->
<style type="text/css">
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th,
	.table>thead>tr>td, .table>thead>tr>th {
	padding: 2px;
}

.paging_bootstrap_extended {
	font-size: 10px;
}

.btn-sm, .btn-xs {
	font-size: 12px;
}
</style>

<!-- END DIV STYLES -->
</head>
<!-- BEGIN BODY -->
<body
	class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo" style="background-color: #F6F6F6;">
	<!-- BEGIN HEADER -->
	<div class="page-header -i navbar navbar-fixed-top" style='background-color:#2c79a2;'>
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner">
			<!-- BEGIN LOGO -->
			<div class="page-logo" >
				<a href="#" id="refreshalldatabaseinfo"> <img src="img/change_logo_1.png"
					alt="logo" class="" />
				</a>
				<span style="color:#FFF;font-size:20px;font-weight:bold;font-family:SimHei;">Membership Management Module</span>	
				<!-- <div class="menu-toggler sidebar-toggler" style="margin:4px 15px 0px 0px;"></div> -->
			</div>
			<!-- END LOGO -->
			<!-- BEGIN HORIZANTAL MENU -->
			
			<!-- END HORIZANTAL MENU -->
			<!-- BEGIN HEADER SEARCH BOX -->
			
			<!-- END HEADER SEARCH BOX -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			<sec:authorize access="hasAnyRole('USER','ADMIN','SU1','SU2')">
				<div class="top-menu">
					<ul class="nav navbar-nav pull-right">
						<li class="dropdown dropdown-user"><a href="javascript:;"
							class="dropdown-toggle" data-toggle="dropdown"
							data-hover="dropdown" data-close-others="true"> <img alt=""
								class="img-circle" src="img/avatar3_small.jpg" /> <span
								class="username username-hide-on-mobile"> <sec:authentication
										property="name" />
							</span> <i class="fa fa-angle-down"></i>
						</a>
							<ul class="dropdown-menu dropdown-menu-default">
								<c:url value="/logout" var="logoutUrl" />
								<li><a href="#" id="logoutbutton"> <i class="icon-key"></i>
										Sign out
								</a>
									<form action="${logoutUrl}" method="post"
										style="display: none;" id="logoutform">
										<input name="${_csrf.parameterName}" value="${_csrf.token}" />
									</form></li>
							</ul></li>
						<!-- END USER LOGIN DROPDOWN -->

					</ul>
				</div>
			</sec:authorize>
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
					data-auto-scroll="true" data-slide-speed="200"
					>
					<li class="open">
					<a href="javascript:;">
					<i class="icon-folder"></i>
					<span class="title">Membership Manage</span>
					<span class="arrow open"></span>
					</a>
					<ul class="sub-menu" id="allclassificationlist" style="display:block;">
					
					</ul>
					</li>
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
					class="fa fa-database"></i> Databases
				</label> <label class="checkbox-inline" style="padding-left: 0px;">
					<select id="database" class="form-control select2me"
					data-placeholder="Select..." style="min-width: 100px;">
						<option value="">Please choose a database</option>
				</select>
				</label> <label class="checkbox-inline" style="padding-left: 0px;">
					<i class="fa fa-table"></i> Table
				</label> <label class="checkbox-inline" style="padding-left: 0px;">
					<select id="table" class="form-control select2me"
					data-placeholder="Select..." style="min-width: 100px;">
						<option value="">Please choose a table</option>
				</select>
				</label> 
				

				<p></p>
				
				<div id="showdetail" style="display: none;">
					<div class="row">
						<button type="button" class="btn btn-primary" style="margin-left:25px;" id="back">返回</button>
					</div>
					
					<div class="row">
						<h2 id="showdetail_db_zhname" style="text-align:center;"></h2>
					</div>
					<hr>
					<div class="media">
						<div class="media-left">
							<div class="color-swatch brand-success bg-primary" style="width:150px;height:130px;text-align:center;line-height:130px;font-size:16px;background:#E5FAE0;color:#000; ">描述*</div>
						</div>
						<div class="media-body highlight" style="line-height:25px;">
							<div id="showdetail_description"></div>
						</div>
					</div>
					<div class="media">
						<div class="media-left">
							<div class="color-swatch brand-info bg-danger" style="width:150px;height:150px;text-align:center;line-height:150px;font-size:16px;background:#F5E3C6;  ">负责单位信息*</div>
						</div>
						<div class="media-body highlight" style="line-height:25px;">
							<div id="showdetail_resinstitution"></div>
							<div id="showdetail_resaddress"></div>
							<div id="showdetail_respostalcode"></div>
							<div id="showdetail_resphone"></div>
							<div id="showdetail_resemail"></div>
						</div>
					</div>
					<div class="media">
						<div class="media-left">
							<div class="color-swatch brand-warning bg-info" style="width:150px;height:94px;text-align:center;line-height:94px;font-size:16px; background:#E8F7FC">其他信息*</div>
						</div>
						<div class="media-body highlight" style="line-height:25px;">
							<div id="showdetail_identifier"></div>
							<div id="showdetail_keywords"></div>
							<div id="showdetail_language"></div>
						</div>
					</div>
				</div>
				<p></p>
				<div class="row">
					<div class="col-md-10">
					<div id="showdatabaseinfo" style="display: none;">

					<p></p>
					<div class="alert alert-info">
						<div class="row">
							<span id="showdatabaseinfo_name"></span>
						</div>
						<div class="row">
							<span id="showdatabaseinfo_comments"></span>
						</div>
						<div class="row">
							<span id="showdatabaseinfo_tablenumber"></span>
						</div>
						<div class="row">
							<div class="col-md-9">
								<div class="table-container">
									<div class="table-actions-wrapper">
										<span> </span>

									</div>

									<table class="table table-striped table-bordered table-hover"
										id="datatable_ajax_database">
										<thead>
											<tr role="row" class="heading">
												<th width="1%"><input type="checkbox"
													class="group-checkable"></th>
												<th width="5%">id</th>
												<th>隐藏实际编号</th>
												<th width="5%">table_name</th>
												<th width="5%">desc</th>
												<th width="20%">content</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
							<div class="col-md-3">
								
								<p></p>
								<div class="row"></div>
							</div>
						</div>
					</div>
				</div>
					</div>
					<div class="col-md-2"></div>
				</div>
				
				<div class="row">
					<div class="col-md-7">
					<div id="showtableinfo" style="display: none;">
					<div class="alert alert-success">
						<div class="row">
							<span id="showtableinfo_name"></span>

						</div>
						<div class="row">
							<span id="showtableinfo_comments"></span>
						</div>
						<div class="row">
							<span id="showtableinfo_fieldnumber"></span>
						</div>
						<div class="row">
							<span id="showtableinfo_numrows"></span>
						</div>
					</div>
					<p></p>
					<div class="alert alert-info">
						<div class="row">
							'
							<div class="col-md-12">
								<div class="table-container">
									<div class="table-actions-wrapper">
										<span> </span>
									</div>

									<table class="table table-striped table-bordered table-hover"
										id="datatable_ajax">
										<thead>
											<tr role="row" class="heading">
												<th width="1%"><input type="checkbox"
													class="group-checkable"></th>
												<th width="5%">id</th>
												<th>隐藏实际编号</th>
												<th width="5%">列名</th>
												<th width="5%">中文名</th>
												<th width="15%">描述</th>
												<th width="20%">备注</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
					</div>
					<div class="col-md-5"></div>
				</div>
				
				<!-- End: life time stats -->

				<!-- END EXAMPLE TABLE PORTLET-->

			</div>
		</div>
		<!-- END CONTENT -->

	</div>
	<!-- END CONTAINER -->
	<div id="showshoppingcart_model" class="modal fade" tabindex="-1"
		data-width="400">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">已选数据集</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12" id="selectedtableinfo"></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn green" id="applaydata">数据申请</button>
					<button type="button" class="btn red" id="deleteallshoppingcart">清空</button>
					<button type="button" data-dismiss="modal" class="btn">确定</button>
				</div>
			</div>
		</div>
	</div>
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