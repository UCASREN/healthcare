<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>主题分析</title>
<meta name="keywords" content="医疗,大数据,平台" />
<meta name="description" content="包含数据集成及发布、流程申请、虚拟环境搭建等功能" />

<!--#set var='compatible' value=''-->
<meta http-equiv="X-UA-Compatible"
	content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="icon" href="home/icons/home/logo.ico" type="image/x-icon" />
<link rel="stylesheet" href="home/style/home/common_6e2f763e.css" />
<link rel="stylesheet" href="home/style/home/public_eb211bd4.css" />
<link rel="stylesheet" href="home/style/home/footer_47d2c33b.css" />
<link rel="stylesheet" href="home/style/home/header_c8045ce8.css" />

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
<link id="style_color" href="resources/css/themes/light2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/custom.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style-shop.css" rel="stylesheet" type="text/css" />
<link href="resources/css/components.css" id="style_components" rel="stylesheet" type="text/css" />
<link href="resources/css/docs.min.css" rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<script src="home/script/home/jquery-1.7.1.min_c7e0488b.js"></script>
<script src="home/script/home/lt.core_a59af84a.js"></script>
<script src="home/script/home/jquery.artDialog_d121c362.js"></script>
<script src="home/script/home/jquery.loadingui_6a6316c4.js"></script>
<script src="home/script/home/jquery.tipsui_03bbd011.js"></script>
<script src="home/script/home/lt.apps_f462ac2c.js"></script>
<script src="home/script/home/jquery.placeholderui_4ea8db00.js"></script>
<script src="home/script/home/jquery.checkboxui_1589ca87.js"></script>
<!-- <script src="home/script/home/jquery.validTip_3908ed67.js"></script> -->
<script src="home/script/home/jquery.valid_cbce39ff.js"></script>
<script src="home/script/home/quick_menu_3bf9fc99.js"></script>

</head>

<body id="home">

	<!-- BEGIN HEADER -->
	<div class="page-header -i navbar navbar-fixed-top" style='background-color:#2c79a2;'>
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner">
			<!-- BEGIN LOGO -->
			<div class="page-logo" >
				<a href="#" id="refreshalldatabaseinfo"> <img src="img/change_logo_1.png"
					alt="logo" class="" />
				</a>
				<span style="color:#FFF;font-size:20px;font-weight:bold;font-family:SimHei;">中国心脑血管病临床大数据平台</span>	
				<!-- <div class="menu-toggler sidebar-toggler" style="margin:4px 15px 0px 0px;"></div> -->
			</div>
			<!-- END LOGO -->
			<!-- BEGIN HORIZANTAL MENU -->
			<div class="hor-menu hor-menu-light hidden-sm hidden-xs">
				<ul class="nav navbar-nav">
					<li class="classic-menu-dropdown "><a href="userdatabaseview"  style="color:#c6cfda;">
							元数据</a></li>
					<li class="classic-menu-dropdown"><a
						href="topicanalysis" target="_blank" style="color:#c6cfda;"> 主题分析</a></li>
					<li class="classic-menu-dropdown"><a
						href="applydata/applytable" target="_blank" style="color:#c6cfda;">数据服务 </a></li>
					<li class="classic-menu-dropdown"><a href="applyenv/applytable"
						target="_blank" style="color:#c6cfda;">虚拟计算 </a></li>
				</ul>
			</div>
			<!-- END HORIZANTAL MENU -->
			
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
								<li><a href="userpanel" target="_blank"> <i
										class="icon-user"></i> 我的账户
								</a></li>
								<li><a href="applydata/applytable" target="_blank"> <i
										class="icon-envelope-open"></i> 数据服务 <span
										class="badge badge-danger"> 2 </span>
								</a></li>
								<li><a href="applyenv/applytable"> <i
										class="icon-rocket"></i> 虚拟计算<span
										class="badge badge-success"> 3 </span>
								</a></li>
								<li class="divider"></li>
								<li><a href="javascript:;"> <i class="icon-lock"></i>
										锁屏
								</a></li>
								<c:url value="/logout" var="logoutUrl" />
								<li><a href="#" id="logoutbutton"> <i class="icon-key"></i>
										登出
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
	
	<div class="container marketing " style="margin-top:104px;">
  <div class="row">
    <div class="col-lg-6"> <img class="img-thumbnail" src="resources/img/zysj.jpg" alt="Generic placeholder image" height="240" width="340">
      <h2>住院数据</h2>
      <p>Donec sed odio dui. Etiam porta sem malesuada magna mollis 
        euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi 
        leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo
        cursus magna.</p>
      <p><a class="btn btn-default" href="topicanalysis_hospital" role="button">点击进入 »</a></p>
    </div>
    <div class="col-lg-6"> <img class="img-thumbnail" src="resources/img/scsj.jpg" alt="Generic placeholder image" height="240" width="340">
      <h2>筛查数据</h2>
      <p>Duis mollis, est non commodo luctus, nisi erat porttitor 
        ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus 
        sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor 
        mauris condimentum nibh.</p>
      <p><a class="btn btn-default" href="year_info" role="button">点击进入 »</a></p>
    </div>
  </div>
</div>

</body>

</html>