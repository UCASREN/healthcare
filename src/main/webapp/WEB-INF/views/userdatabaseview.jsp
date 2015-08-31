<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>数据库管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<!-- <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/> -->
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
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>

	<!-- BEGIN EXAMPLE TABLE PORTLET-->
	<!-- Begin: life time stats -->
	<div class="portlet  box blue">
		<div class="portlet-title">
			<label class="checkbox-inline" style="padding-left: 0px;"> <i
				class="fa fa-database"></i> 数据库
			</label> <label class="checkbox-inline" style="padding-left: 0px;"> <select
				id="database" class="form-control select2me"
				data-placeholder="Select..." style="min-width: 100px;">
					<option value="">请选择一个数据库...</option>
			</select>
			</label> <label class="checkbox-inline" style="padding-left: 0px;"> <i
				class="fa fa-table"></i> 表
			</label> <label class="checkbox-inline" style="padding-left: 0px;"> <select
				id="table" class="form-control select2me"
				data-placeholder="Select..." style="min-width: 100px;">
					<option value="">请选择数据库下的表...</option>
			</select>
			</label>
			<div class="actions">
				<a href="javascript:;" class="btn default yellow-stripe"> <i
					class="fa fa-plus"></i> <span class="hidden-480"> 暂留 </span>
				</a>
				<div class="btn-group">
					<a class="btn default yellow-stripe" href="javascript:;"
						data-toggle="dropdown"> <i class="fa fa-share"></i> <span
						class="hidden-480"> 工具 </span> <i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu pull-right">
						<li><a href="javascript:;"> Export to Excel </a></li>
						<li><a href="javascript:;"> Export to CSV </a></li>
						<li><a href="javascript:;"> Export to XML </a></li>
						<li class="divider"></li>
						<li><a href="javascript:;"> Print Invoices </a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="portlet-body">
			<div class="row">
				<div class="col-md-2">
					<div id="tree" class="tree-demo"></div>
				</div>
				<div class="col-md-10">
					<div class="tabbable-line">
						<ul class="nav nav-tabs ">
							<li class="active"><a href="#showdatabaseinfo"
								data-toggle="tab"> 数据库信息 </a></li>
							<li><a href="#showtableinfo" data-toggle="tab"> 表信息 </a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="showdatabaseinfo">
								<div class="row">
									<div class="col-md-5">
										<p id="showdatabaseinfo_name"></p>
										<p id="showdatabaseinfo_comments"></p>
										<p id="showdatabaseinfo_others"></p>
										<p id="showdatabaseinfo_tablenumber"></p>
									</div>
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
								</div>
							</div>
							<div class="tab-pane" id="showtableinfo">
								<div class="row">
									<div class="col-md-5">

										<p id="showtableinfo_name"></p>
										<p id="showtableinfo_comments"></p>
										<p id="showtableinfo_others"></p>
										<p id="showtableinfo_fieldnumber"></p>

									</div>
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
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End: life time stats -->

	<!-- END EXAMPLE TABLE PORTLET-->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="js/respond.min.js"></script>
<script src="js/excanvas.min.js"></script> 
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

	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="resources/js/databasemanager/databasemanager_metronic.js"
		type="text/javascript"></script>
	<script src="resources/js/components-pickers.js" type="text/javascript"></script>
	<script src="resources/js/databasemanager/datatable.js"
		type="text/javascript"></script>
	<script src="resources/js/databasemanager/userdatabaseview.js"
		type="text/javascript"></script>
	<script>
		jQuery(document).ready(function() {
			Metronic.init();
			ComponentsPickers.init();

		});
	</script>
</body>
<!-- END BODY -->
</html>