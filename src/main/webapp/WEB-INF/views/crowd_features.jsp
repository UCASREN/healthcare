<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.s_p.part1 {
	width: 80%;
	float: left;
	margin-left: 200px;
	overflow: hidden;
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

</head>
<body class="body">
	<div class="navbar navbar-default navbar-fixed-top" role="navigation"
		id="head"
		style="height: 35px; line-height: 35px; background-color: #87CEEB; text-align: right; padding-top: 5px; padding-right: 20px">
		<div>

			<span class="text-primary">省</span> <select name="select"
				id="opt_province" class="xla_k">
				<option value=''>全部</option>
			</select> <span class="text-primary">基地医院</span> <select name="select"
				id="opt_basehospital" class="xla_k">
				<option value=''>全部</option>
			</select> <span class="text-primary">社区</span> <select name="select"
				id="opt_community" class="xla_k">
				<option value=''>全部</option>
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
	<div class="s_p_p2">
		<div id="taskchartcontainera2" style="height: 100%; width: 100%;">
			<i class='ace-icon fa fa-spinner fa-spin green bigger-300'></i>

		</div>
		<script type="text/javascript">
			
		</script>

	</div>
	<div class="s_p_part2">
		<div class="s_p_p3">
			<div id="taskchartcontainera3"
				style="height: 100%; width: 100%; line-height: 100%;">
				<i class='ace-icon fa fa-spinner fa-spin green bigger-300'></i>

			</div>
			<script type="text/javascript">
				
			</script>
		</div>

	</div>
	<div class="clear"></div>

	<div class="s_p_part3">
		<h3 class="header smaller lighter red">图表说明</h3>

		<div class="well">
			<h4 class="green smaller lighter">脑卒中患者的年龄频数分布</h4>

		</div>
		<hr
			style="margin: 0px; height: 1px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="well well-lg">
			<h4 class="blue">脑卒中患者的性别频数分布</h4>

		</div>
		<hr
			style="margin: 0px; height: 1px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="well well-lg">
			<h4 class="green smaller lighter">脑卒中患者的城乡频数分布</h4>

		</div>
		<hr
			style="margin: 0px; height: 1px; border: 0px; background-color: #D5D5D5; color: #D5D5D5;" />
		<div class="well well-lg">
			<h4 class="blue">脑卒中患者的受教育水平频数分布</h4>
			未统计缺失数据
		</div>
	</div>
	<!-- /.col -->

</body>
<script src="resources/js/crowd_features/crowd_features.js"
	type="text/javascript"></script>
<script src="resources/js/echarts-all.js"></script>
<script type="text/javascript">
option = {
	    title : {
	        text: '某站点用户访问来源',
	        subtext: '纯属虚构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:['男','女']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {
	                show: true, 
	                type: ['pie', 'funnel'],
	                option: {
	                    funnel: {
	                        x: '25%',
	                        width: '50%',
	                        funnelAlign: 'left',
	                        max: 1548
	                    }
	                }
	            },
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                
	            ]
	        }
	    ]
	};
	                    
	var myChart1 = echarts
			.init(document.getElementById('taskchartcontainera1'));
	//myChart1.showLoading({text: "图表数据正在努力加载..."});
	myChart1.setOption(option);
	function query() {
		myChart1.showLoading({
			text : "图表数据正在努力加载..."
		});
		$.ajax({
			type : "get",//请求方式
			url : "getgenderinfo",//发送请求地址
			dataType : "json",
			data:{
				provinceid:$("#opt_province").val(),
				accodeup:$("#opt_basehospital").val(),
				community:$("#opt_community").val()
			},
			success : function(data) {
				if (data) {
					console.log(data);
					var new_data=new Array();
					$(data).each(function(){ 
						var temp_map=this;
						var new_map={};
						new_map["name"]=temp_map["gender"];
						new_map["value"]=temp_map["percentage"];
						new_data.push(new_map);
				    }); 
					 
					myChart1.hideLoading();
					var temp_option = myChart1.getOption();
					temp_option.series[0].data=new_data;
					myChart1.setOption(temp_option);
				}
			}
		});

	}
	var myChart2 = echarts
			.init(document.getElementById('taskchartcontainera2'));
	var myChart3 = echarts
			.init(document.getElementById('taskchartcontainera3'));
</script>
</html>