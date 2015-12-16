<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>主题分析</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="icon" href="home/icons/home/logo.ico" type="image/x-icon" />
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
<!-- <link id="style_color" href="resources/css/darkblue.css" rel="stylesheet" type="text/css" />-->
<link id="style_color" href="resources/css/themes/light2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/custom.css" rel="stylesheet" type="text/css" />
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
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo">
	<!-- BEGIN HEADER -->
	<div class="page-header -i navbar navbar-fixed-top" style='background-color:#2c79a2;'>
		<!-- BEGIN HEADER INNER -->
		<div class="page-header-inner">
			<!-- BEGIN LOGO -->
			<div class="page-logo">
				<a href="/healthcare" id=""> <img src="img/change_logo_1.png"
					alt="logo" class="" />
				</a>
				<span style="color:#FFF;font-size:20px;font-weight:bold;font-family:SimHei;">中国心脑血管病大数据平台</span>	
				<!-- <div class="menu-toggler sidebar-toggler"></div> -->
			</div>
			<!-- END LOGO -->
			<!-- BEGIN HORIZANTAL MENU -->
			<div class="hor-menu hor-menu-light hidden-sm hidden-xs">
				<ul class="nav navbar-nav">
					<li class="classic-menu-dropdown" style="height:60px;"><a style="color:#c6cfda;" href="userdatabaseview">元数据 </a></li>
					<li class="classic-menu-dropdown"><a style="color:#c6cfda;" href="topicanalysis" target="_blank"> 主题分析 </a></li>
					<li class="classic-menu-dropdown"><a style="color:#c6cfda;" href="applydata/applytable" target="_blank"> 数据服务 </a></li>
					<li class="classic-menu-dropdown"><a style="color:#c6cfda;" href="applyenv/applytable" target="_blank"> 虚拟计算 </a></li>
				</ul>
			</div>
			<!-- END HORIZANTAL MENU -->
			<!-- BEGIN HEADER SEARCH BOX -->
<!-- 			<form class="search-form" action="#" method="GET">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search..."
						name="query"> <span class="input-group-btn"> <a
						href="javascript:;" class="btn submit"><i
							class="icon-magnifier"></i></a>
					</span>
				</div>
			</form> -->
			<!-- END HEADER SEARCH BOX -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
			<!-- END RESPONSIVE MENU TOGGLER -->
					
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
									class="badge badge-danger"> 3 </span>
							</a></li>
							<li><a href="applyenv/applytable"> <i class="icon-rocket"></i>
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
			<div class="page-sidebar navbar-collapse collapse">
				<!-- BEGIN SIDEBAR MENU1 -->
				<ul class="page-sidebar-menu hidden-sm hidden-xs"
					data-auto-scroll="true" data-slide-speed="200">
					<li><a href="javascript:;" id="people_feature"> <i class="icon-basket"></i> <span
							class="title">人口学特征</span> <span class="arrow "></span>
					</a>
					</li>
					<li><a href="javascript:;" id="prevalence_rate"> <i class="icon-basket"></i> <span
							class="title"> 脑卒中患病率 </span> <span class="arrow "></span>
					</a>
						<ul class="sub-menu">
							<li><a href="javascript:;" id="prevalence_rate_l1"> <i class="icon-home"></i>
									脑卒中患病率分布
							</a></li>
							<li><a href="javascript:;" id="prevalence_rate_l2"> <i
									class="icon-basket"></i> 脑卒中患病率详情
							</a></li>
						</ul></li>
					<li><a href="javascript:;" id="danger_factor"> <i class="icon-basket"></i> <span
							class="title"> 危险因素分布 </span> <span class="arrow "></span>
					</a>
						<ul class="sub-menu">
							<li><a href="javascript:;" id="danger_factor_l1"> <i class="icon-home"></i>
									全人群危险因素分布
							</a></li>
							<li><a href="javascript:;" id="danger_factor_l2"> <i
									class="icon-basket"></i> 高危人群危险因素分布
							</a></li>
							<li><a href="javascript:;" id="danger_factor_l3"> <i
									class="icon-basket"></i> 脑卒中人群危险因素分布
							</a></li>
						</ul></li>
					<li><a href="javascript:;" id="treat_control"> <i class="icon-basket"></i> <span
							class="title"> 治疗与控制率 </span> <span class="arrow "></span>
					</a>
					</li>
					<li><a href="javascript:;" id="esrs_score"> <i class="icon-basket"></i> <span
							class="title"> ESRS评分 </span> <span class="arrow "></span>
					</a>
					</li>
					<li><a href="javascript:;" id="framinghan_danger_assess"> <i class="icon-basket"></i> <span
							class="title"> Framinghan风险评估 </span> <span class="arrow "></span>
					</a>
					</li>
					<li><a href="javascript:;" id="icvd_danger_assess"> <i class="icon-basket"></i> <span
							class="title"> ICVD风险评估 </span> <span class="arrow "></span>
					</a>
					</li>
					<li><a href="javascript:;" id="data_resource"> <i class="icon-basket"></i> <span
							class="title"> 数据资源 </span> <span class="arrow "></span>
					</a>
						<ul class="sub-menu">
							<li><a href="javascript:;" id="data_resource_inner"> <i class="icon-home"></i>
									脑卒中数据资源
							</a></li>
						</ul></li>
					<li><a href="javascript:;" id="cloud_label"> <i class="icon-basket"></i> <span
							class="title"> 云标签 </span> <span class="arrow "></span>
					</a>
						<ul class="sub-menu">
							<li><a href="javascript:;" id="cloud_label_l1"> <i class="icon-home"></i>
									年度分析
							</a></li>
							<li><a href="javascript:;" id="cloud_label_l2"> <i
									class="icon-basket"></i> 实时分析
							</a></li>
						</ul></li>
					
					<!-- 住院数据 -->
					<li><a href="javascript:;" id="inhospital_home"> <i class="icon-basket"></i> <span
							class="title">住院首页 </span> <span class="arrow"></span>
						</a>
					</li>
						
						
				</ul>
			</div>
		</div>
		<!-- END SIDEBAR -->
		<!-- BEGIN CONTENT -->
		<div class="page-content-wrapper">
			<div class="page-content">
				<iframe  id='main' src='' height=1000px  width="99%" frameborder="0" align="top"></iframe>
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
	<script src="resources/js/layout.js" type="text/javascript"></script>
	<script src="resources/js/quick-sidebar.js" type="text/javascript"></script>
	<script src="resources/js/demo.js" type="text/javascript"></script>
	<script src="resources/js/components-pickers.js" type="text/javascript"></script>
<!-- 	<script src="resources/js/datasetanalysis/datasetanalysis.js" type="text/javascript"></script>-->	
<script>
	jQuery(document).ready(function() {
		Metronic.init(); // init metronic core components
		Layout.init(); // init current layout
		QuickSidebar.init(); // init quick sidebar
		Demo.init(); // init demo features
		ComponentsPickers.init();
	});
</script>
<script>
    $().ready(function() {
    	$('#whole_danger_factor').click(function(){
        	$('#main').attr('src', '<%=basePath%>' + "service_whole_danger_factor");
        	$('#content_page').text("全人群危险因素分布");
        });
        $('#total_danger_factor').click(function(){
        	$('#main').attr('src', '<%=basePath%>' + "service_danger_factor");
        	$('#content_page').text("脑卒中人群危险因素分布");
        });
        $('#dashboard').click(function(){
       	 
        	$('#main').attr('src', '<%=basePath%>' + "service_dashboard");
        	$('#content_page').text("脑卒中患病率分布");
        });
        $('#high_danger_factor').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'service_high_danger_factor');
        	$('#content_page').text("高危人群危险因素分布");
        
        });
        $('#content_page').click(function(){
        	var con=$('#content_page').text();
        	if(con.indexOf("高危人群危险因素分布") !=-1){
        		$('#main').attr('src','<%=basePath%>' + 'service_high_danger_factor');
        	}
        	else if(con.indexOf("脑卒中人群危险因素分布") != -1){
        		$('#main').attr('src', '<%=basePath%>' + "service_danger_factor");
        	}
        	else if(con.indexOf("全人群危险因素分布") != -1){
        		$('#main').attr('src', '<%=basePath%>' + "service_whole_danger_factor");
        	}
        	else if(con.indexOf("脑卒中患病率详情")!=-1){
        		$('#main').attr('src','<%=basePath%>service_stroke_patient');
        	}
        	else if(con.indexOf("人口学特征")!=-1){
        		$('#main').attr('src','<%=basePath%>people_features');
        	}
        	else if(con.indexOf("治疗与控制率")!=-1){
        		$('#main').attr('src','<%=basePath%>treat_control');
        	}
        	else if(con.indexOf("ESRS评分")!=-1){
        		$('#main').attr('src','<%=basePath%>ESRS_score');
        	}

        	else if(con.indexOf("脑卒中数据资源")!=-1){
        		$('#main').attr('src','<%=basePath%>dataResource/stroke_data');
        	}
        	else if(con.indexOf("脑卒中患病率分布")!=-1){
        		$('#main').attr('src','<%=basePath%>service_dashboard');
        	}
        	else if(con.indexOf("年度分析")!=-1){
        		$('#main').attr('src','<%=basePath%>year_analyse');
        	}
        	else if(con.indexOf("实时分析")!=-1){
        		$('#main').attr('src','<%=basePath%>real_time');
        	}
        	else if(con.indexOf("用户信息录入")!=-1){
        		$('#main').attr('src','<%=basePath%>user_info_add');
        	}
        	else if(con.indexOf("超重计算")!=-1){
        		$('#main').attr('src','<%=basePath%>overweight_calculate');
        	}else if(con.indexOf("参与机构分布")!=-1){
        		$('#main').attr('src','<%=basePath%>hospital_distribution');
        	}
        	
        });
        $('#tasktracker_font_color1').click(function(){
        	$('#main').attr('src','<%=basePath%>get_tasktracker');
        	
        });
        $('#home').click(function(){
        	$('#main').attr('src','<%=basePath%>service_dashboard');
        	
        });
        $('#stroke_patient').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'service_stroke_patient');
        	$('#content_page').text("脑卒中患病率详情");
        	
        });
        $('#people_features').click(function(){
        	$('#main').attr('src', '<%=basePath%>' + "people_features");
        	$('#content_page').text("人口学特征");
        	
        });
 
        $('#treat_control').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'treat_control');
        	$('#content_page').text("治疗与控制率");
        	 
        	
        });
        $('#esrs_score').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'ESRS_score');
        	$('#content_page').text("ESRS评分");
        	 
        	
        });
        $('#framinghan_score').click(function(){
                	$('#main').attr('src','<%=basePath%>' + 'Framinghan_score');
                	$('#content_page').text("Framinghan风险评估");
        });
        $('#icvd_score').click(function(){
                    $('#main').attr('src','<%=basePath%>' + 'ICVD_score');
                    $('#content_page').text("ICVD风险评估");
        });
        $('#stroke_data').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'dataResource/stroke_data');
        	$('#content_page').text("脑卒中数据资源");
        	 
        	
        });
        $('#year_analyse').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'year_analyse');
        	$('#content_page').text("年度分析");
        	 
        	
        });
        $('#real_time').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'real_time');
        	$('#content_page').text("实时分析");
        	 
        	
        });
        $('#user_info_add').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'user_info_add');
        	$('#content_page').text("用户信息录入");
        	 
        });
        
        $('#join_institution').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'hospital_distribution');
        	$('#content_page').text("用户信息录入");
        	 
        });
        $('#overweight_calculate').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'overweight_calculate');
        	$('#content_page').text("超重计算");
        });
        
        $('#inhospital_patientsNum').click(function(){
        	$('#main').attr('src','<%=basePath%>' + 'inhospital_patientsNum');
        	$('#content_page').text("住院人次");
        });
        
    }); // end ready
</script>

<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>