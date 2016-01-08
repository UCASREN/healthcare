<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf-8"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
request.setCharacterEncoding("UTF-8");   
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<meta http-equiv="X-Frame-Options" content="ALLOW-FROM"> 
<title>数据服务流程</title>
<link rel="icon" href="/healthcare/img/logo.ico" type="image/x-icon" />

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
<!-- <link id="style_color" href="../resources/css/themes/darkblue.css" rel="stylesheet" type="text/css"/> -->
<link id="style_color" href="../resources/css/themes/light2.css" rel="stylesheet" type="text/css"/>
<link href="../resources/css/custom.css" rel="stylesheet" type="text/css"/>
</head>

<body class="page-header-fixed page-quick-sidebar-over-content">
<!-- BEGIN HEADER -->
<div class="page-header -i navbar navbar-fixed-top" style='background-color:#2c79a2;'>
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="/healthcare" id="" style="margin-top: 7px;"> <img src="/healthcare/img/change_logo_1.png"
				alt="logo" class="" />
			</a>
			<span style="color:#FFF;font-size:20px;font-weight:bold;font-family:SimHei;">中国心脑血管病大数据共享平台</span>	
			<!-- <div class="menu-toggler sidebar-toggler"></div> -->
		</div>
		<!-- END LOGO -->
		
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown dropdown-user">
						<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true"> 
							<img alt="" class="img-circle" src="/healthcare/img/avatar3_small.jpg" />
							<span class="username username-hide-on-mobile"><sec:authentication property="name" /></span> <i class="fa fa-angle-down"></i>
						</a>
						
						<ul class="dropdown-menu dropdown-menu-default">
							<li><a href="userpanel" target="_blank"> <i
									class="icon-user"></i> 我的账户
							</a></li>
							<li><a href="/healthcare/applydata/applytable" target="_blank"> <i
									class="icon-envelope-open"></i> 数据服务 <span
									class="badge badge-danger"> 3 </span>
							</a></li>
							<li><a href="/healthcare/applyenv/applytable"> <i class="icon-rocket"></i>
									虚拟计算<span class="badge badge-success"> 7 </span>
							</a></li>
							
							<li class="divider"></li>
							<li><a href="javascript:;"> <i class="icon-lock"></i> 锁屏
							</a></li>
							
							<c:url value="/logout" var="logoutUrl" />
							<li><a href="#" id="logoutbutton"> <i class="icon-key"></i>
									登出
							</a>
							<form action="${logoutUrl}" method="post" style="display: none;" id="logoutform">
									<input name="${_csrf.parameterName}" value="${_csrf.token}" />
							</form></li>
						</ul>
						
					</li>
				</ul>
			</div>
		
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->

<div class="clearfix">
</div>

<div class="page-container" style="margin-top:43px;">

	<!-- BEGIN SIDEBAR -->
	<div class="page-sidebar-wrapper">
		<!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
		<!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
		<div class="page-sidebar navbar-collapse collapse">
			<ul class="page-sidebar-menu" data-keep-expanded="false"
					data-auto-scroll="true" data-slide-speed="200">
					<li><a href="/healthcare/userdatabaseview"> <i class="icon-home"></i> <span
							class="title">元数据</span> <span class="arrow "></span>
					</a></li>
					<li><a href="/healthcare/topicanalysis"> <i class="icon-puzzle"></i> <span
							class="title">主题分析</span> <span class="arrow "></span>
					</a></li>
					<li><a href="/healthcare/applydata/applytable"> <i class="icon-rocket"></i> <span
							class="title">数据服务</span> <span class="arrow "></span>
					</a></li>
					<li><a href="/healthcare/applyenv/applytable"> <i class="icon-diamond"></i> <span
							class="title">虚拟计算</span> <span class="arrow "></span>
					</a></li>

				</ul>
		</div>
	</div>
	<!-- END SIDEBAR -->
	
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<div class="page-content">
			
			<h3 class="page-title">
			数据服务 <small>新建申请</small>
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
						<a href="#">数据服务申请</a>
					</li>
				</ul>
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
							<form action="" class="form-horizontal" id="submit_form" method="POST" accept-charset="UTF-8">
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
													<label class="control-label col-md-3">需求类别 
													</label>
													<div class="col-md-4" style="padding-top:6px">
														<div class="icheck-list">
															<label>
																<input type="radio" name="userDemandType" class="icheck" value="数据分析需求" 
																data-title="数据分析需求" data-radio="iradio_flat-blue" checked>
																数据分析需求 
															</label>
															<!-- <label>
																<input type="radio" name="userDemandType" class="icheck" value="数据使用需求" 
																data-title="数据使用需求" data-radio="iradio_flat-blue" checked>
																数据使用需求 
															</label> -->
														</div>
														<div id="form_demand_error">
														</div>
													</div>
												</div>
												
												<div class="form-group">
													<label class="control-label col-md-3">需求说明<span class="required">
													* </span>
													</label>
													<div class="col-md-5">
														<textarea class="form-control" cols="15" rows="4" name="userDemand" id="submit_form_userDemand"></textarea>
													</div>
												</div>
												
												<div class="form-group" style="margin-top:10px;margin-bottom:0px">
													<label class="control-label col-md-3">数据格式<span class="required">
													* </span> 
													</label>
													<div class="col-md-4">
														<div class="icheck-list">
															<label>
																<input type="radio" name="dataExportType" class="icheck" value="CSV" 
																data-title="CSV" data-radio="iradio_flat-blue" checked>
																CSV 
															</label>
															<label>
																<input type="radio" name="dataExportType" class="icheck" value="Excel" 
																data-title="Excel" data-radio="iradio_flat-blue">
																Excel 
															</label>
														</div>
														<div id="form_demand_error">
														</div>
													</div>
												</div>
												
												<div class="form-group" style="margin-top:3px">
													<hr class="col-md-12"/>
													<label class="control-label col-md-3">选择数据</label>
													<div class="col-md-4">
														<button id="db_select" class="btn yellow" >
															<i class="fa fa-database"></i>&nbsp;选择
														</button>
														<button id="shoppingCart" class="btn green" >
															<i class="fa fa-shopping-cart"></i>&nbsp;加载数据集
														</button>
														<button id="cleanShopingCart" class="btn red" >
															<i class="fa fa-trash-o"></i>&nbsp;清空
														</button>
													</div>
												</div>
												
												<div style="margin-top:3px">
													<div id="shoppanel" class="alert alert-success" style="margin-left:180px;margin-right:180px">
														<input type="hidden" id="applydata" name="applydata" value=""></input>
														
														<div id="emptyshoppingcart" class="row" style="display:none;">
															<div class="col-md-7">
																<ul>
																	<span>当前数据集为空！</span>
																</ul>
															</div>
														</div>
														
														<table id="shoptable" border="1" align="center" style="display:none;">
		    												<caption align="top">数据集列表</caption>
													    	<tr>
													  		  <th style="width:10%;text-align:center;">数据集名称</th>
													  		  <th style="width:10%;text-align:center;">数据表名称</th>
													  		  <th style="width:20%;text-align:center;">相关说明</th>
													  		</tr>
											  			</table>
														
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
												<div class="form-group" style="margin-bottom:20px;margin-top:10px">
													<label class="control-label col-md-3">支持课题情况 <span class="required">
													* </span>
													</label>
													<div class="col-md-4" style="margin-top: 8px;">
														<input type="checkbox" class="icheck" data-checkbox="icheckbox_flat-blue" id="projectApply"
															checked="checked" name="projectApply" value="projectApply" data-title="已申请课题 " >支持课题 </label>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题名称 <span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" class="form-control" name="projectName" id="submit_form_projectName"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">首席科学家(首要负责人)<span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" placeholder="" class="form-control" name="projectChairman" id="submit_form_projectChairman"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题来源<span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" placeholder="" class="form-control" name="projectSource" id="submit_form_projectSource"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">承担单位<span class="required">
													* </span>
													</label>
													<div class="col-md-4">
														<input type="text" placeholder="" class="form-control" name="projectUndertaking" id="submit_form_projectUndertaking"/>
														<span class="help-block">
														</span>
													</div>
												</div>
												<div class="form-group applytime" style="margin-bottom:0px">
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
												<input type="hidden" id="docid" name="docid" value="">
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
													<label class="control-label col-md-3">数据格式：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="dataExportType">
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
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题名称：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectName">
														</p>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">首席科学家(首要负责人)：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectChairman">
														</p>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">项目/课题来源：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectSource">
														</p>
													</div>
												</div>
												<div class="form-group projectItems" style="margin-bottom:0px">
													<label class="control-label col-md-3">承担单位：</label>
													<div class="col-md-4">
														<p class="form-control-static" data-display="projectUndertaking">
														</p>
													</div>
												</div>
												<div class="form-group applytime" style="margin-bottom:0px">
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
												<a href="javascript:;" class="btn green button-submit" >
												提交 <i class="m-icon-swapright m-icon-white"></i>
												</a>
												<a href="javascript:;" class="btn purple button-wordPreview">Word预览 </a>
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
		2015 &copy; 中国心脑血管病大数据共享平台 --- 中国卒中数据中心
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
<script src="../resources/js/apply/form-wizard.js"></script>
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