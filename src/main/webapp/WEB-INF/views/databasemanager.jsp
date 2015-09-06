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
<link href="resources/plugins/dropzone/css/dropzone.css"
	rel="stylesheet" />

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
<!-- BEGIN DIV STYLES -->
<style type="text/css">
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
	padding: 2px;
}
.paging_bootstrap_extended{
	font-size: 10px;
}
.btn-sm, .btn-xs {
	font-size: 12px;
}
</style>

<!-- END DIV STYLES -->
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>

	<!-- BEGIN EXAMPLE TABLE PORTLET-->
	<div class="portlet box blue">
		<div class="portlet-title">
			<div class="caption">
				<i class="fa fa-edit"></i>数据库管理
			</div>
			<div class="tools">
				<a href="javascript:;" class="collapse"> </a> <a
					href="#portlet-config" data-toggle="modal" class="config"> </a> <a
					href="javascript:;" class="reload"> </a> <a href="javascript:;"
					class="remove"> </a>
			</div>
		</div>
		<div class="portlet-body">
			<div class="row">
				<div class="col-md-2">
					<div class="portlet-body">
						<div id="tree" class="tree-demo"></div>
					</div>
				</div>
				<div class="col-md-10">
					<div class="row">
						<div class="col-md-12">
							<a class="btn default" data-toggle="modal"
								href="#fileuploadmodal"> 上传文件</a> <a class="btn default"
								data-toggle="modal" href="#changedatabaseinfo" id="setchangedatabasetitle">请从左侧选择要更改的数据库</a>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-12">
									<div class="table-toolbar">

										<div class="row">
											<div class="col-md-6">
												<div class="btn-group">
													<button id="editable_1_new" class="btn green">
														添加 <i class="fa fa-plus"></i>
													</button>
												</div>
											</div>
											<div class="col-md-6">
												<div class="btn-group pull-right">
													<button class="btn dropdown-toggle" data-toggle="dropdown">
														工具<i class="fa fa-angle-down"></i>
													</button>
													<ul class="dropdown-menu pull-right">
														<li><a href="javascript:;"> 打印</a></li>
														<li><a href="javascript:;"> 保存为PDF </a></li>
														<li><a href="javascript:;"> 导出Excel </a></li>
													</ul>
												</div>
											</div>
										</div>
									</div>

									<table class="table table-striped table-hover table-bordered"
										id="editable_1">
										<thead>
											<tr>
												<th>编号</th>
												<th>表名</th>
												<th>备注</th>
												<th>操作</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<p class="text-center" id="whichdatabase"></p>
									<p class="hidden" id="whichdatabaseid"></p>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="row">
								<div class="col-md-12">
									<div class="table-toolbar">

										<div class="row">
											<div class="col-md-6">
												<div class="btn-group">
													<button id="editable_2_new" class="btn green">
														添加 <i class="fa fa-plus"></i>
													</button>
												</div>
											</div>
											<div class="col-md-6">
												<div class="btn-group pull-right">
													<button class="btn dropdown-toggle" data-toggle="dropdown">
														工具<i class="fa fa-angle-down"></i>
													</button>
													<ul class="dropdown-menu pull-right">
														<li><a href="javascript:;"> 打印</a></li>
														<li><a href="javascript:;"> 保存为PDF </a></li>
														<li><a href="javascript:;"> 导出Excel </a></li>
													</ul>
												</div>
											</div>
										</div>
									</div>

									<table class="table table-striped table-hover table-bordered"
										id="editable_2">
										<thead>
											<tr>
												<th>编号</th>
												<th>列名</th>
												<th>备注</th>
												<th>操作</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<p class="text-center" id="whichtable"></p>
									<div class="hidden" id="whichtableid"></div>
									<div class="hidden" id="whichtableid_belong"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<!-- BEGIN Portlet PORTLET-->
							<div class="portlet gren">
								<div class="portlet-title">
									<div class="caption">
										<i class="fa fa-gift"></i>概要信息
									</div>
									<div class="tools">
										<a href="javascript:;" class="collapse"> </a> <a
											href="#portlet-config" data-toggle="modal" class="config">
										</a> <a href="javascript:;" class="reload"> </a> <a href=""
											class="fullscreen"> </a> <a href="javascript:;"
											class="remove"> </a>
									</div>
								</div>
								<div class="portlet-body">
									<div class="scroller" style="height: 200px"></div>
								</div>
							</div>
							<!-- END Portlet PORTLET-->

						</div>

					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- END EXAMPLE TABLE PORTLET-->
	<div class="modal fade bs-modal-lg" id="fileuploadmodal" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title">文件上传</h4>
					<h5>
						<label class="checkbox-inline" style="padding-left: 0px;">
							<i class="fa fa-database"></i> 数据库
						</label><label class="checkbox-inline" style="padding-left: 5px;">
							<select id="database" class="form-control select2me"
							data-placeholder="Select..." style="min-width: 100px;">
								<option value="">请选择一个数据库...</option>
						</select>
						</label> <label class="checkbox-inline" style="padding-left: 0px;">
							或者新建一个数据库 </label>
					</h5>
				</div>
				<div class="modal-body">
					<form
						action="dataresource/batchupload?${_csrf.parameterName}=${_csrf.token}"
						class="dropzone" id="myDropzone" enctype="multipart/form-data"
						method="post">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>
				</div>
				<div class="modal-footer" style="text-align: left;">
					<button type="button" class="btn blue" id="submit-all"
						disabled="disabled">上传</button>
					<button type="button" class="btn default" data-dismiss="modal">返回主界面</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<div id="changedatabaseinfo" class="modal fade" tabindex="-1"
		data-width="400">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title" >数据库信息更改</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<form class="form-horizontal" role="form" action="dataresource/databaseoperation?operation=update">
								<div class="form-body">
									<div class="form-group">
										<label class="col-md-2 control-label">名称</label>
										<div class="col-md-9">
											<input type="hidden" id="form_database_id">
											<input type="text" class="form-control" id="form_database_name"
												placeholder="输入数据库名称">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">备注</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="form_database_comments"
												placeholder="输入数据库备注">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">语种</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入语种">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">字符集</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入字符集">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">学科分类</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入学科分类">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">关键词</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入关键词">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">可信度</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入可信度">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">负责单位名称</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入负责单位名称">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">负责人姓名</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入负责人姓名">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">通讯地址</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入通讯地址">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">邮政编码</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入邮政编码">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">联系电话</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入联系电话">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">电子邮件</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入电子邮件地址">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">资源链接</label>
										<div class="col-md-9">
											<input type="text" class="form-control"
												placeholder="输入资源链接地址">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn">Close</button>
					<button type="button" class="btn red">Ok</button>
				</div>
			</div>
		</div>
	</div>


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
	<script type="text/javascript"
		src="resources/plugins/dropzone/dropzone.js"></script>

	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="resources/js/databasemanager/databasemanager_metronic.js"
		type="text/javascript"></script>
	<script src="resources/js/components-pickers.js" type="text/javascript"></script>
	<script src="resources/js/databasemanager/databasemanager.js"
		type="text/javascript"></script>
	<script src="resources/js/databasemanager/form-dropzone.js"
		type="text/javascript"></script>
	<!-- <script src="resources/js/databasemanager/tabletable.js" type="text/javascript"></script>
	<script src="resources/js/databasemanager/ajaxtree.js" type="text/javascript"></script> -->
	<script>
		jQuery(document).ready(function() {
			Metronic.init();
			ComponentsPickers.init();
			FormDropzone.init();
		});
	</script>
</body>
<!-- END BODY -->
</html>