<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>疾病负担</title>
<style type="text/css">
.s_p.part1 {
	width: 80%;
	float: left;
	margin-left: 200px;
	overflow: hidden;
	margin-top:100px;
	padding-top: 100px;
}
.s_p_part1 {
	padding-top: 100px;
}

.s_p.part2 {
	width: 80%;
	float: left;
	margin-top: 20px;
	overflow: hidden;
}

.clear {
	clear: both
}

.s_p_part3 {
	width: 80%;
	height: 300px;
	margin-left: 10%;
	margin-top: 20px;
}

.s_p_p1 {
	width: 40%;
	height: 300px;
	float: left;
	margin-left: 10%;
	border: 3px solid;
	border-color: #cccccc;
	text-align: center;
}

.s_p_p2 {
	width: 40%;
	height: 300px;
	float: left;
	margin-left: 1%;
	border: 3px solid;
	border-color: #cccccc;
	line-height: 300px;
	text-align: center;
}

.s_p_p3 {
	width: 40%;
	height: 300px;
	float: left;
	margin-left: 10%;
	border: 3px solid;
	border-color: #cccccc;
	margin-top: 20px;
	text-align: center;
}

.s_p_p4 {
	width: 40%;
	height: 300px;
	float: left;
	margin-left: 1%;
	border: 3px solid;
	border-color: #cccccc;
	margin-top: 20px;
	text-align: center;
}

.body {
	background-color: white
}
</style>
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/plugins/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- bootstrap & fontawesome -->

<link href="resources/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/ace.min.css" />
</head>
<body class="body">
	<div class="navbar navbar-default navbar-fixed-top" role="navigation"
		id="head"
		style="height: 35px; line-height: 35px; background-color: #87CEEB; text-align: right; padding-top: 5px; padding-right: 20px;margin-bottom:30px;">
		<div>
			<span class="text-primary">省</span> <select name="select"
				id="opt_province" class="xla_k">
				<option value=''>全部</option>
			</select> <span class="text-primary">危险因素</span> <select name="select"
				id="opt_danger_factor" class="xla_k">
				<option value='dfHypertension'>高血压</option>
				<option value='dfLDL'>血脂异常</option>
				<option value='dfGlycuresis'>糖尿病</option>
				<option value='dfAF'>房颤或心率不齐</option>
				<option value='dfSmoking'>吸烟史</option>
				<option value='dfSportsLack'>体育锻炼缺乏</option>
				<option value='dfOverweight'>超重</option>
				<option value='dfStrokeFamily'>脑卒中家族史</option>
			</select> <span class="text-primary">年龄</span> <select name="select"
				id="opt_age" class="xla_k">
				<option value=''>全部</option>
				<option value='1'>男</option>
				<option value='2'>女</option>
			</select>
			<button class="btn btn-danger" onclick="query()">查询</button>
			<span id='wrong-message' style="color: red"></span>
		</div>
	</div>
	<div class="s_p_part1">
		<div class="s_p_p1">

			<div id="taskchartcontainera1" style="height: 100%; width: 100%;">
				<span><i
					class='ace-icon fa fa-spinner fa-spin green bigger-300 '></i></span>
			</div>
			<script type="text/javascript">
				
			</script>
		</div>
	</div>
	<div class="clear"></div>

	<div class="s_p_part3">
		<h3 class="header smaller lighter red">图表说明</h3>

		<div class="well">
			<h4 class="green smaller lighter">人群性别分布</h4>

		</div>
		<hr
			style="margin: 0px; height: 1px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="well well-lg">
			<h4 class="blue">人群城乡分布</h4>

		</div>
		<hr
			style="margin: 0px; height: 1px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="well well-lg">
			<h4 class="green smaller lighter">人群年龄分布</h4>

		</div>
		<hr
			style="margin: 0px; height: 1px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="well well-lg">
			<h4 class="blue">人群构成与六普人口构成差异比较</h4>
		</div>
	</div>

</body>
<script src="resources/js/echarts-all.js"></script>
<script src="resources/js/danger_factor/danger_factor.js"
	type="text/javascript"></script>
</html>