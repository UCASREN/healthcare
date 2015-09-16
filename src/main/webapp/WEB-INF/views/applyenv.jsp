<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf-8"
%>
<%
request.setCharacterEncoding("UTF-8");   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>虚拟环境申请流程</title>

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<!-- <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/> -->
<link href="../resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
<link href="../resources/plugins/icheck/skins/flat/_all.css" rel="stylesheet" type="text/css"/
<link href="../resources/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>>
<!-- END GLOBAL MANDATORY STYLES -->

<!-- BEGIN PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css" href="../resources/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="../resources/plugins/bootstrap-datepicker/css/datepicker.css"/>
<!-- END PAGE LEVEL SCRIPTS -->

<!-- BEGIN THEME STYLES -->
<link href="../resources/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="../resources/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/layout.css" rel="stylesheet" type="text/css"/>
<link id="style_color" href="../resources/css/themes/darkblue.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/custom.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="page-container">

	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM  设置菜单-->
			<div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
							<h4 class="modal-title">Modal title</h4>
						</div>
						<div class="modal-body">
							 Widget settings form goes here
						</div>
						<div class="modal-footer">
							<button type="button" class="btn blue">Save changes</button>
							<button type="button" class="btn default" data-dismiss="modal">Close</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			
			<!-- BEGIN STYLE CUSTOMIZER 系统主题-->
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
			
			<!-- BEGIN PAGE HEADER -->
			<h3 class="page-title">
			虚拟环境申请 <small>医疗大数据分析平台</small>
			</h3>
			<div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="/healthcare">主页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">申请</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">虚拟环境申请</a>
					</li>
				</ul>
				<div class="page-toolbar">
					<div class="btn-group pull-right">
						<button type="button" class="btn btn-fit-height grey-salt dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">
						Actions <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right" role="menu">
							<li>
								<a href="#">Action</a>
							</li>
							<li>
								<a href="#">Another action</a>
							</li>
							<li>
								<a href="#">Something else here</a>
							</li>
							<li class="divider">
							</li>
							<li>
								<a href="#">Separated link</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box blue" id="form_wizard_1">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-gift"></i> 填写流程  <span class="step-title">
								 1 / 4 </span>
							</div>
							<div class="tools hidden-xs">
								<a href="javascript:;" class="collapse">
								</a>
								<a href="#portlet-config" data-toggle="modal" class="config">
								</a>
								<a href="javascript:;" class="reload">
								</a>
								<a href="javascript:;" class="remove">
								</a>
							</div>
						</div>
						<div class="portlet-body form">
							<form action="/healthcare/applyenv/createdataword" class="form-horizontal" id="submit_form" method="POST" accept-charset="UTF-8">
							 	<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
								<div class="form-wizard">
									<div class="form-body">
										<ul class="nav nav-pills nav-justified steps">
											<li>
												<a href="#tab1" data-toggle="tab" class="step">
												<span class="number">
												1 </span>
												<span class="desc">
												<i class="fa fa-check"></i> 基本信息 </span>
												</a>
											</li>
											<li>
												<a href="#tab2" data-toggle="tab" class="step">
												<span class="number">
												2 </span>
												<span class="desc">
												<i class="fa fa-check"></i> 数据需求 </span>
												</a>
											</li>
											<li>
												<a href="#tab3" data-toggle="tab" class="step active">
												<span class="number">
												3 </span>
												<span class="desc">
												<i class="fa fa-check"></i> 使用目的 </span>
												</a>
											</li>
											<li>
												<a href="#tab4" data-toggle="tab" class="step">
												<span class="number">
												4 </span>
												<span class="desc">
												<i class="fa fa-check"></i> 确认 </span>
												</a>
											</li>
										</ul>
										<div id="bar" class="progress progress-striped" role="progressbar">
											<div class="progress-bar progress-bar-success">
											</div>
										</div>
										<div class="tab-content">
											<div class="alert alert-danger display-none">
												<button class="close" data-dismiss="alert"></button>
												您的填写有误，请检查后重新提交！
											</div>
											<div class="alert alert-success display-none">
												<button class="close" data-dismiss="alert"></button>
												您的表单验证成功！
											</div>
											<div class="tab-pane active" id="tab1">
												<h3 class="block">请提供你的详细信息</h3>
												
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">用户姓名 <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" class="form-control" name="userName" id="submit_form_userName"/>
														<!-- <span class="help-block">
														请提供您的姓名 </span> -->
														<span class="help-block">
														</span>
													</div>
												</div>
												
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">所在单位 <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" class="form-control" name=userDepartment id="submit_form_userDepartment"/>
														<!-- <span class="help-block">
														请提供您的所在单位 </span> -->
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">地址 <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" class="form-control" name=userAddress id="submit_form_userAddress"/>
														<!-- <span class="help-block">
														请提供您的地址 </span> -->
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">联系电话 <span class="required">
													* </span>
													</label>
													<div class="col-md-4" style="margin-bottom:0px">
														<input type="text" class="form-control" name=userTel id="submit_form_userTel"/>
														<!-- <span class="help-block">
														请提供您的联系方式 </span> -->
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">Email <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" class="form-control" name="userEmail" id="submit_form_userEmail"/>
														<!-- <span class="help-block">
														请提供您的Email </span> -->
														<span class="help-block">
														</span>
													</div>
												</div>
											</div>
											
										 	<div class="tab-pane" id="tab2">
												<h3 class="block">请填写数据需求</h3>
												<div class="form-group">
													<label class="control-label col-md-3">需求类别 <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<div class="icheck-list">
															<label>
																<input type="radio" name="userDemandType" class="icheck" value="数据分析需求" 
																data-title="数据分析需求" data-radio="iradio_flat-blue">
																数据分析需求 
															</label>
															<label>
																<input type="radio" name="userDemandType" class="icheck" value="数据使用需求" 
																data-title="数据使用需求" data-radio="iradio_flat-blue">
																数据使用需求 
															</label>
														</div>
														<div id="form_demand_error">
														</div>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">需求说明<span class="required">
													* </span>
													</label>
													<div class="col-md-5">
														<textarea class="form-control" cols="15" rows="4" name="userDemand" id="submit_form_userDemand"></textarea>
													</div>
												</div>
											</div>
											
											<div class="tab-pane" id="tab3"> 
												<h3 class="block">请填写数据使用目的</h3>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">应用领域 <span class="required">
													* </span>
													</label>
													<div class="col-md-9">
														<!-- <div class="icheck-inline checkbox-list"> -->
														<div>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue"
															name="useFields" value="政府决策" data-title="政府决策"> 政府决策 </label>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue"
															name="useFields" value="科学研究" data-title="科学研究"> 科学研究 </label>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue"
															name="useFields" value="教学" data-title="教学"> 教学 </label>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue"
															name="useFields" value="博士论文" data-title="博士论文"> 博士论文 </label>
															</br>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue"
															name="useFields" value="硕士论文" data-title="硕士论文"> 硕士论文 </label>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue"
															name="useFields" value="商业应用" data-title="商业应用 "> 商业应用 </label>
															<label>
															<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue" id="checkbox_other"
															name="useFields" value="其他" data-title="其他"> 其他 
															</label>
															<input type="text" style="width:100px;" name="others" id="others" placeholder="请填写其他应用"/>
															<!-- <div class=" col-md-3 pull-right">
															</div> -->
														</div>
														<input type="hidden" name="allUseField" id="allUseField" value="">
														<div id="form_useFields_error">
														</div>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题名称 <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" class="form-control" name="projectName" id="submit_form_projectName"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">首席科学家(首要负责人)<span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" placeholder="" class="form-control" name="projectChairman" id="submit_form_projectChairman"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题来源<span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" placeholder="" class="form-control" name="projectSource" id="submit_form_projectSource"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">承担单位<span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" placeholder="" class="form-control" name="projectUndertaking" id="submit_form_projectUndertaking"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">申请时间(DD/MM/YYYY) <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<div class="input-group input-medium date date-picker" data-date-format="yyyy/mm/dd" data-date-start-date="+0d">
															<input type="text" class="form-control" readonly name="applyDate" id="submit_form_applyDate">
															<span class="input-group-btn">
																<button class="btn default" type="button"><i class="fa fa-calendar"></i></button>
															</span>
														</div>
													<!-- 	<span class="help-block">
														例 ：  11/11/2020 </span> -->
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">应用说明 <span class="required">
													* </span>
													</label>
													<div class="col-md-5">
														<textarea class="form-control" cols="15" rows="4" name="projectRemarks" id="submit_form_projectRemarks"></textarea>
														<span class="help-block">
														具体说明服务对象的情况，如该数据集在项目中的作用以及所产生的成果的描述等</span>
													</div>
												</div>
											</div>
											
											<div class="tab-pane" id="tab4">
												<input type="hidden" id="applydataid" name="applydataid" value="">
												<h3 class="block">请确认您填写的信息</h3>
												<h4 class="form-section">用户信息</h4>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">用户姓名：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="userName">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">所在单位：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="userDepartment">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">地址：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="userAddress">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">联系电话：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display=userTel>
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">Email：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="userEmail">
														</p>
													</div>
												</div>
												
												<h4 class="form-section">数据需求</h4>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">需求类别：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="userDemandType">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">需求说明：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="userDemand">
														</p>
													</div>
												</div>
												
												<h4 class="form-section">使用目的</h4>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">应用领域：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="useFields" id="useFields_display">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题名称：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectName">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">首席科学家(首要负责人)：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectChairman">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题来源：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectSource">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">承担单位：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectUndertaking">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">申请时间(MM/YYYY)：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="applyDate">
														</p>
													</div>
												</div>
												<div class="form-group" style="margin-bottom:0px">
													<label class="control-label col-md-3">应用说明：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectRemarks">
														</p>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="form-actions">
										<div class="row">
											<div class="col-md-offset-3 col-md-9">
												<a href="javascript:;" class="btn default button-previous">
												上一步 <i class="m-icon-swapleft"></i> 
												</a>
												<a href="javascript:;" class="btn blue button-next">
												下一步 <i class="m-icon-swapright m-icon-white"></i>
												</a>
												<a href="javascript:;" class="btn green button-submit">
												提交 <i class="m-icon-swapright m-icon-white"></i>
												</a>
												<a href="/healthcare/apply/wordonline" target="_blank" class="btn purple button-wordPreview">Word预览 </a>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
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
		 2014 &copy; Metronic by keenthemes.
	</div>
	<div class="scroll-to-top">
		<i class="icon-arrow-up"></i>
	</div>
</div>
<!-- END FOOTER -->


<!-- JS CONFIG -->
<!-- BEGIN CORE PLUGINS -->
<script src="../resources/js/jquery.min.js" type="text/javascript"></script>
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
<script src="../resources/js/jquery.query.js"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../resources/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../resources/plugins/jquery-validation/js/additional-methods.min.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="../resources/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="../resources/plugins/icheck/icheck.min.js"></script>
<script type="text/javascript" src="../resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../resources/js/apply/apply_metronic.js" type="text/javascript"></script>
<script src="../resources/js/layout.js" type="text/javascript"></script>
<script src="../resources/js/quick-sidebar.js" type="text/javascript"></script>
<script src="../resources/js/demo.js" type="text/javascript"></script>
<script src="../resources/js/apply/formEnv-wizard.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->

<script>
jQuery(document).ready(function() {       
   // initiate layout and plugins
   Metronic.init(); // init metronic core components
   Layout.init(); // init current layout
  // QuickSidebar.init(); // init quick sidebar
   Demo.init(); // init demo features
   FormWizard.init();
});
</script>

</body>
</html>