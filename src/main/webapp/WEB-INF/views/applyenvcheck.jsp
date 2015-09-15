<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf-8"
%>
<%
request.setCharacterEncoding("UTF-8");   
%>

<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>虚拟环境申请审批</title>

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
<link rel="shortcut icon" href="favicon.ico"/>
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
			<a href="index.html">
			<img src="../resources/img/logo.png" alt="logo" class="logo-default"/>
			</a>
			<div class="menu-toggler sidebar-toggler hide">
				<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
			</div>
		</div>
		<!-- END LOGO -->
	
		
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->
<div class="clearfix">
</div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
	
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
		
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">确认拒绝本次申请？</h4>
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
			
			<!-- BEGIN STYLE CUSTOMIZER -->
			<div class="theme-panel hidden-xs hidden-sm">
				<div class="toggler">
				</div>
				<div class="toggler-close">
				</div>
				<div class="theme-options">
					<div class="theme-option theme-colors clearfix">
						<span>
						THEME COLOR </span>
						<ul>
							<li class="color-default current tooltips" data-style="default" data-container="body" data-original-title="Default">
							</li>
							<li class="color-darkblue tooltips" data-style="darkblue" data-container="body" data-original-title="Dark Blue">
							</li>
							<li class="color-blue tooltips" data-style="blue" data-container="body" data-original-title="Blue">
							</li>
							<li class="color-grey tooltips" data-style="grey" data-container="body" data-original-title="Grey">
							</li>
							<li class="color-light tooltips" data-style="light" data-container="body" data-original-title="Light">
							</li>
							<li class="color-light2 tooltips" data-style="light2" data-container="body" data-html="true" data-original-title="Light 2">
							</li>
						</ul>
					</div>
					<div class="theme-option">
						<span>
						Layout </span>
						<select class="layout-option form-control input-sm">
							<option value="fluid" selected="selected">Fluid</option>
							<option value="boxed">Boxed</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Header </span>
						<select class="page-header-option form-control input-sm">
							<option value="fixed" selected="selected">Fixed</option>
							<option value="default">Default</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Top Menu Dropdown</span>
						<select class="page-header-top-dropdown-style-option form-control input-sm">
							<option value="light" selected="selected">Light</option>
							<option value="dark">Dark</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Mode</span>
						<select class="sidebar-option form-control input-sm">
							<option value="fixed">Fixed</option>
							<option value="default" selected="selected">Default</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Menu </span>
						<select class="sidebar-menu-option form-control input-sm">
							<option value="accordion" selected="selected">Accordion</option>
							<option value="hover">Hover</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Style </span>
						<select class="sidebar-style-option form-control input-sm">
							<option value="default" selected="selected">Default</option>
							<option value="light">Light</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Sidebar Position </span>
						<select class="sidebar-pos-option form-control input-sm">
							<option value="left" selected="selected">Left</option>
							<option value="right">Right</option>
						</select>
					</div>
					<div class="theme-option">
						<span>
						Footer </span>
						<select class="page-footer-option form-control input-sm">
							<option value="fixed">Fixed</option>
							<option value="default" selected="selected">Default</option>
						</select>
					</div>
				</div>
			</div>
			<!-- END STYLE CUSTOMIZER -->
			
			<!-- BEGIN PAGE HEADER-->
			<h3 class="page-title">
			申请审批 <small>虚拟环境申请</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="/healthcare">主页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="/healthcare/adminpanel/applyenvtable">申请清单</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">虚拟环境申请审批（管理员）</a>
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
		 2015 &copy; 医疗大数据分析平台 --- 中国科学院软件研究所
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
<script src="../resources/js/apply/applyenvcheck.js"></script>
<!-- END PAGE LEVEL STYLES -->
<script>
	jQuery(document).ready(function() {   
 	  // initiate layout and plugins
   	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	applyenvCheck.init();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>