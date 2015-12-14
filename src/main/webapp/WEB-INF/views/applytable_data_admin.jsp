<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>数据服务(管理员)</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all"
	rel="stylesheet" type="text/css" />
<link href="../resources/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="../resources/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="../resources/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="../resources/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<link
	href="../resources/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="../resources/plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/plugins/bootstrap-datepicker/css/datepicker.css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="../resources/css/components.css" id="style_components"
	rel="stylesheet" type="text/css" />
<link href="../resources/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="../resources/css/layout.css" rel="stylesheet"
	type="text/css" />
<link id="style_color" href="../resources/css/darkblue.css"
	rel="stylesheet" type="text/css" />
<link href="../resources/css/custom.css" rel="stylesheet"
	type="text/css" />
<link href="../resources/css/slider/style.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES 图标-->
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body class="page-header-fixed page-quick-sidebar-over-content ">
	<!-- BEGIN HEADER -->
	<div class="page-header -i navbar navbar-fixed-top">
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner">
			<!-- BEGIN LOGO -->
			<div class="page-logo">
				<a href="/healthcare" id="refreshalldatabaseinfo"> <img src="../resources/img/change_logo_1.png"
				alt="logo" class="" />
				</a>
				<span style="color:#FFF;font-size:15px;font-weight:bold;">中国心脑血管病<br>大数据平台</span>
				<div class="menu-toggler sidebar-toggler hide">
					<!-- DOC:f Remove the above "hide" to enable the sidebar toggler button on header -->
				</div>
			</div>
			<!-- END LOGO -->

			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler"
				data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->
			
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown dropdown-user"><a href="javascript:;"
						class="dropdown-toggle" data-toggle="dropdown"
						data-hover="dropdown" data-close-others="true"> <img alt=""
							class="img-circle" src="/healthcare/img/avatar3_small.jpg" /> <span
							class="username username-hide-on-mobile"> <sec:authentication property="name" />
						</span> <i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu dropdown-menu-default">
							<li><a href="userpanel" target="_blank"> <i
									class="icon-user"></i> 我的账户
							</a></li>
							<li><a href="applydatatable" target="_blank"> <i
									class="icon-envelope-open"></i> 数据服务 <span
									class="badge badge-danger"> 3 </span>
							</a></li>
							<li><a href="applyenvtable"> <i class="icon-rocket"></i>
									虚拟计算<span class="badge badge-success"> 7 </span>
							</a></li>
							<li class="divider"></li>
							<li><a href="javascript:;"> <i class="icon-lock"></i> 锁屏
							</a></li>
							<c:url value="/logout" var="logoutUrl" />
							<li><a href="#" id="logoutbutton"> <i class="icon-key"></i>
									登出
							</a>
								<form action="${logoutUrl}" method="post" style="display: none;"
									id="logoutform">
									<input name="${_csrf.parameterName}" value="${_csrf.token}" />
								</form></li>
						</ul></li>
					<!-- END USER LOGIN DROPDOWN -->

				</ul>
			</div>
			
		</div>
		<!-- END HEADER INNER -->
	</div>
	<!-- END HEADER -->

	<div class="clearfix"></div>

	<!-- BEGIN CONTAINER -->
	<div class="page-container">

		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar-wrapper">
			<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
			<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
			<div class="page-sidebar navbar-collapse collapse">
				<ul class="page-sidebar-menu" data-keep-expanded="false"
					data-auto-scroll="true" data-slide-speed="200">
					<li><a href="/healthcare/databasemanager"> <i class="icon-home"></i> <span
							class="title">元数据</span> <span class="arrow "></span>
					</a></li>
					<li><a href="/healthcare/topicanalysis"> <i class="icon-puzzle"></i> <span
							class="title">主题分析</span> <span class="arrow "></span>
					</a></li>
					<li><a href="applydatatable"> <i class="icon-rocket"></i> <span
							class="title">数据服务</span> <span class="arrow "></span>
					</a></li>
					<li><a href="applyenvtable"> <i class="icon-diamond"></i> <span
							class="title">虚拟计算</span> <span class="arrow "></span>
					</a></li>
				</ul>
			</div>
		</div>
		<!-- END SIDEBAR -->

		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">


				<!--DOC: Aplly "modal-cached" class after "modal" class to enable ajax content caching-->
				<div class="modal fade draggable-modal" id="ajax" tabindex="-1"
					role="basic" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title">
									申请状态查询 --- <label id="modal_applyid">序号null</label>
								</h4>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="col-md-12">
										<div>
											<label class="col-md-3 control-label">申请者 ：</label> <label
												id="modal_username" class="col-md-8 control-label">null</label>
										</div>
										<div>
											<label class="col-md-3 control-label">项目名称 ：</label> <label
												id="modal_projectname" class="col-md-8 control-label">null</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="project-screening">
											<div class="project-screening-yellow"></div>
											<div class="select-1-yellow"></div>
											<div class="screening-select select-1 current"><a href="javascript:;">待审核</a></div>
											<div class="screening-select select-2" selval="4,6"><a href="javascript:;" selval="4,6">卒中中心</a></div>
											<div class="screening-select select-3" selval="6,7"><a href="javascript:;" selval="6,7">卒中防治委员会</a></div>
											<div class="screening-select select-4" selval="7,10"><a href="javascript:;" selval="7,10">数据集分配</a></div>
											<div id="status_final" class="screening-select select-5" selval="10,-1"><a href="javascript:;" selval="10,-1">审核成功</a></div>
										</div>
									</div>
								</div>
							</div>
							<div id='failPanel' class="modal-body" style="display: none">
								<h5>
									<b>系统提示---申请失败原因</b>
								</h5>
								<div class="row">
									<div class="col-md-12">
										<textarea class="form-control" name="rejectReason"
											id="rejectReason"></textarea>
										<span class="help-block"></span>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn default" data-dismiss="modal">确定</button>
								<!-- 	<button type="button" class="btn blue">Save changes</button>-->
							</div>
						</div>
					</div>
				</div>


				<!-- BEGIN PAGE HEADER-->
				<h3 class="page-title">
					数据服务 <small>历史清单</small>
				</h3>
				<div class="page-bar">
					<ul class="page-breadcrumb">
						<li><i class="fa fa-home"></i> <a href="/healthcare">主页</a> <i
							class="fa fa-angle-right"></i></li>
						<li><a href="#">数据服务(管理员) </a></li>
					</ul>
				</div>
				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->
				<div class="row">
					<div class="col-md-12">

						<!-- Begin: life time stats -->
						<div class="portlet">
							<div class="portlet-body">
								<div class="table-container">
									<div class="table-actions-wrapper1">
										<!-- 	<button  class="btn btn-sm red table-group-action-submit"><i class="fa fa-minus"></i> 删除记录</button>
									<button  class="btn btn-sm yellow" onclick="window.open('/healthcare/applyenv/applyenv')" ><i class="fa fa-plus"></i> 新建申请</button> -->
										<button class="btn btn-sm blue table-advanced-search">
											<i class="fa fa-search"></i> 高级搜索 <i class="fa fa-arrow-down"></i>
										</button>
									</div>
									<table class="table table-striped table-bordered table-hover"
										id="datatable_products">
										<thead>
											<tr role="row" class="heading">
												<th width="2%"><input type="checkbox"
													class="group-checkable">
												</th>
												<th width="4%">
													 序号
												</th>
												<th width="7%">
													申请者
												</th>
												<th width="10%">
													所在单位
												</th>
												<th width="10%">
													 项目名称
												</th>
												<th width="20%">
													  数据需求
												</th>
												<th width="11%">
													申请时间
												</th>
												<th width="10%">
													 申请状态
												</th>
												<th width="27%">
													 操作
												</th>
											</tr>
											<tr role="row" class="filter" id="filter_panel">
												<td></td>
												<td><input type="text"
													class="form-control form-filter input-sm"
													name="applyData_id"></td>
												<td><input type="text"
													class="form-control form-filter input-sm"
													name="applyData_userName"></td>
												<td><input type="text"
													class="form-control form-filter input-sm"
													name="applyData_userDepartment"></td>
												<td><input type="text"
													class="form-control form-filter input-sm"
													name="applyData_projectName"></td>
												<td><input type="text"
													class="form-control form-filter input-sm"
													name="applyData_dataDemand" /></td>
												<td>
													<div class="input-group date date-picker margin-bottom-5"
														data-date-format="yyyy/mm/dd">
														<input type="text"
															class="form-control form-filter input-sm" readonly
															name="product_created_from" placeholder="From"> <span
															class="input-group-btn">
															<button class="btn btn-sm default" type="button">
																<i class="fa fa-calendar"></i>
															</button>
														</span>
													</div>
													<div class="input-group date date-picker"
														data-date-format="yyyy/mm/dd">
														<input type="text"
															class="form-control form-filter input-sm" readonly
															name="product_created_to" placeholder="To"> <span
															class="input-group-btn">
															<button class="btn btn-sm default" type="button">
																<i class="fa fa-calendar"></i>
															</button>
														</span>
													</div>
												</td>
												<td><select name="product_status"
													class="form-control form-filter input-sm">
														<option value="">状态过滤</option>
														<option value="notCheck">待审核</option>
														<option value="Checking">审核中</option>
														<option value="Checked">审核通过</option>
														<option value="unChecked">审核未通过</option>
												</select></td>
												<td>
													<div class="margin-bottom-5">
														<button
															class="btn btn-sm yellow filter-submit margin-bottom">
															<i class="fa fa-search"></i> 搜索
														</button>
													</div>
													<button class="btn btn-sm red filter-cancel">
														<i class="fa fa-times"></i> 重置
													</button>
												</td>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- End: life time stats -->
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
		</div>
		<!-- END CONTENT -->

	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="page-footer">
		<div class="page-footer-inner">2015 &copy; 医疗大数据分析平台 ---
			中国科学院软件研究所</div>
		<div class="scroll-to-top">
			<i class="icon-arrow-up"></i>
		</div>
	</div>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
	<script src="../resources/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="../resources/plugins/jquery-migrate.min.js"
		type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="../resources/plugins/jquery-ui/jquery-ui.min.js"
		type="text/javascript"></script>
	<script src="../resources/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
	<script
		src="../resources/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
		type="text/javascript"></script>
	<script
		src="../resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
		type="text/javascript"></script>
	<script src="../resources/plugins/jquery.blockui.min.js"
		type="text/javascript"></script>
	<script src="../resources/plugins/jquery.cokie.min.js"
		type="text/javascript"></script>
	<script src="../resources/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
	<script
		src="../resources/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
		type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script type="text/javascript"
		src="../resources/plugins/select2/select2.min.js"></script>
	<script type="text/javascript"
		src="../resources/plugins/datatables/media/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="../resources/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
	<script type="text/javascript"
		src="../resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="../resources/js/apply/apply_metronic.js"
		type="text/javascript"></script>
	<script src="../resources/js/layout.js" type="text/javascript"></script>
	<script src="../resources/js/quick-sidebar.js" type="text/javascript"></script>
	<script src="../resources/js/demo.js" type="text/javascript"></script>
	<script src="../resources/js/apply/apply_datatable_admin.js"></script>
	<script src="../resources/js/apply/applyDatatable_admin.js"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script>
		jQuery(document).ready(function() {
			Metronic.init(); // init metronic core components
			Layout.init(); // init current layout
			QuickSidebar.init(); // init quick sidebar
			applyDatatable.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>