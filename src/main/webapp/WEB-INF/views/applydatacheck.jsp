<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf-8"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
request.setCharacterEncoding("UTF-8");   
%>

<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>数据服务审批</title>
<link rel="icon" href="/healthcare/img/logo.ico" type="image/x-icon" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="../resources/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="../resources/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css"/>
<link rel="stylesheet" type="text/css" href="../resources/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
<link rel="stylesheet" type="text/css" href="../resources/plugins/bootstrap-datepicker/css/datepicker.css"/>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="../resources/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="../resources/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="../resources/css/darkblue.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
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
				<a href="/healthcare" id=""> <img src="/healthcare/img/change_logo_1.png"
					alt="logo" class="" />
				</a>
				<span style="color:#FFF;font-size:20px;font-weight:bold;font-family:SimHei;">中国心脑血管病临床大数据平台</span>	
				<!-- <div class="menu-toggler sidebar-toggler"></div> -->
			</div>
		<!-- END LOGO -->
		
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
<div class="clearfix">
</div>
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
		
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title" id="rejectTitle">确认拒绝本次申请？</h4>
						</div>
						<div class="modal-body">
								<div>
									<textarea class="form-control" placeholder="请填写拒绝理由" name="rejectReason" id="rejectReason"></textarea>
									<span class="help-block"></span>
								</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn blue" data-dismiss="modal" id="reject_button">确定</button>
							<button type="button" class="btn default" data-dismiss="modal">取消</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
					
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			申请审批 <small>数据服务</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="/healthcare">主页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="/healthcare/adminpanel/applydatatable">申请清单</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">数据服务审批（管理员）</a>
					</li>
				</ul>
				
			</div>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN VALIDATION STATES-->
					<div class="portlet box green">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-gift"></i>申请内容
							</div>
							<!-- <div class="tools">
								<a href="javascript:;" class="collapse">
								</a>
								<a href="#portlet-config" data-toggle="modal" class="config">
								</a>
								<a href="javascript:;" class="reload">
								</a>
							</div> -->
						</div>
						
						<div class="portlet-body form">
							<!-- BEGIN FORM-->
							<div class="form-horizontal" id="applycheck_form">
								<div class="form-body">
									<h4 class="form-section">用户信息</h4>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">用户姓名：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="userName">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">所在单位：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="userDepartment">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">地址：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="userAddress">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">联系电话：</label>
										<div class="col-md-4">
											<p class="form-control-static" id=userTel>
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">Email：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="userEmail">
											</p>
										</div>
									</div>
									
									<h4 class="form-section">数据需求</h4>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">需求类别：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="userDemandType">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">需求说明：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="userDemand">
											</p>
										</div>
									</div>
									
									<h4 class="form-section">使用目的</h4>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">应用领域：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="useFields">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">项目/课题名称：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="projectName">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">首席科学家(首要负责人)：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="projectChairman">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">项目/课题来源：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="projectSource">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">承担单位：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="projectUndertaking">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">申请时间(MM/YYYY)：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="applyDate">
											</p>
										</div>
									</div>
									<div class="form-group" style="margin-bottom:0px">
										<label class="control-label col-md-3">应用说明：</label>
										<div class="col-md-4">
											<p class="form-control-static" id="projectRemarks">
											</p>
										</div>
									</div>
								</div>
									<input type="hidden" id="applydataid" name="applydataid" value="">
								<div class="form-actions">
									<div class="row">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn green" id="submit_button"><i class="fa fa-check"></i>同意申请</button>&nbsp;&nbsp;&nbsp;
											<a href="#portlet-config" data-toggle="modal">
												<button class="btn red" id="cancle_button"><i class="fa fa-times"></i>拒绝申请</button>
											</a>
										</div>
									</div>
								</div>
							</div>
							<!-- END FORM-->
						</div>
					</div>
					<!-- END VALIDATION STATES-->
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
	<div class="page-footer-inner">
		 2015 &copy; 中国心脑血管病临床大数据平台 --- 中国卒中数据中心
	</div>
	<div class="scroll-to-top">
		<i class="icon-arrow-up"></i>
	</div>
</div>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../resources/plugins/respond.min.js"></script>
<script src="../resources/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="../resources/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../resources/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="../resources/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="../resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../resources/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="../resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="../resources/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="../resources/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="../resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="../resources/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../resources/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../resources/plugins/jquery-validation/js/additional-methods.min.js"></script>
<script type="text/javascript" src="../resources/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
<script type="text/javascript" src="../resources/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-markdown/js/bootstrap-markdown.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-markdown/lib/markdown.js"></script>
<script type="text/javascript" src="../resources/plugins/jquery-validation/js/additional-methods.min.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<script src="../resources/js/jquery.query.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL STYLES -->
<script src="../resources/js/apply/apply_metronic.js" type="text/javascript"></script>
<script src="../resources/js/layout.js" type="text/javascript"></script>
<script src="../resources/js/apply/applydatacheck.js"></script>
<!-- END PAGE LEVEL STYLES -->
<script>
	jQuery(document).ready(function() {   
 	  // initiate layout and plugins
   	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	applydataCheck.init();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>