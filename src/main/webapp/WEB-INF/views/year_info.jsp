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
<title>住院首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
<link id="style_color" href="resources/css/darkblue.css"
	rel="stylesheet" type="text/css" />
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
<body>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<h3 class="page-title" style="padding:10px 100px;">筛查首页 <small>主题分析</small></h3>
			
      		
			<div class="row" style="padding:10px 100px;">
				<div class="col-md-4">
					<div class="dashboard-stat blue-madison" style="height:280px;">
						<div class="visual">
							<i class="fa fa-briefcase fa-icon-medium"></i>
						</div>
						<div class="details">
							<div class="desc">
								覆盖范围
							</div>
							<div id="province_city_count_2011" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 参与基地医院
							</div>
							<div id="join_basehospital_count_2011" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 参与社区卫生医疗机构
							</div>
							<div id="join_community_count_2011" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 完成筛查样本
							</div>
							<div id="join_end_count_2011" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 高危人群检出数
							</div>
							<div id="join_danger_count_2011" class="desc">
								 加载中...
							</div>
							<div class="desc">
								脑卒中检出数
							</div>
							<div id="join_stroke_count_2011" class="desc">
								 加载中...
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="dashboard-stat red-intense" style="height:280px;">
						<div class="visual">
							<i class="fa fa-briefcase fa-icon-medium"></i>
						</div>
						<div class="details">
							<div class="desc">
								覆盖范围
							</div>
							<div id="province_city_count_2012" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 参与基地医院
							</div>
							<div id="join_basehospital_count_2012" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 参与社区卫生医疗机构
							</div>
							<div id="join_community_count_2012" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 完成筛查样本
							</div>
							<div id="join_end_count_2012" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 高危人群检出数
							</div>
							<div id="join_danger_count_2012" class="desc">
								 加载中...
							</div>
							<div class="desc">
								脑卒中检出数
							</div>
							<div id="join_stroke_count_2012" class="desc">
								 加载中...
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="dashboard-stat green-haze" style="height:280px;">
						<div class="visual">
							<i class="fa fa-briefcase fa-icon-medium"></i>
						</div>
						<div class="details">
							<div class="desc">
								覆盖范围
							</div>
							<div id="province_city_count_2013" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 参与基地医院
							</div>
							<div id="join_basehospital_count_2013" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 参与社区卫生医疗机构
							</div>
							<div id="join_community_count_2013" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 完成筛查样本
							</div>
							<div id="join_end_count_2013" class="desc">
								 加载中...
							</div>
							<div class="desc">
								 高危人群检出数
							</div>
							<div id="join_danger_count_2013" class="desc">
								 加载中...
							</div>
							<div class="desc">
								脑卒中检出数
							</div>
							<div id="join_stroke_count_2013" class="desc">
								 加载中...
							</div>
						</div>
					</div>
				</div>
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
	<script src="resources/js/screen_statistics/screen_statistics.js" type="text/javascript"></script>
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