<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>患病地图</title>
<style type="text/css">
.s_p_part1 {
	width: 80%;
	height: 500px;
	margin-left: 10%;
}

.clear {
	clear: both
}

.s_p_part3 {
	width: 80%;
	height: 200px;
	margin-left: 10%;
	margin-top: 20px;
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
<script src="resources/js/echarts-all.js"></script>
</head>
<body class="body">
	<div class="s_p_part1">
		<div id="taskchartcontainera4" style="height: 100%; width: 100%;">
			<i class='ace-icon fa fa-spinner fa-spin green bigger-300'></i>
			
			<script type="text/javascript">
			jQuery.ajax({
				type : "GET",
				contentType : 'application/x-www-form-urlencoded; charset=utf-8',
				url : 'get_beillmap_data',
				dataType : 'json',
				success : function(data) {
				 if (data) {
						var arraydata=new Array();
						 var valuemax=0.0;
						for(var key in data){
						   if(parseFloat(parseFloat(data['max_value'])*100.0)>valuemax){
							   valuemax=parseFloat(parseFloat(data['max_value'])*100.0);
						   }
					 		arraydata.push({name:key,value:parseFloat(parseFloat(parseFloat(data[key])*100.0).toFixed(2))});
					 		
						} 
						console.log(arraydata);
					
			myChart = echarts
			.init(document
					.getElementById('taskchartcontainera4'));
			option = {
			title : {
			    text: '脑卒中患者的地区频率分布',
			    subtext: ' ',
			    x:'left'
			},
			tooltip : {
			    trigger: 'item',
			    formatter: "{a} <br/>{b} : {c} {d}%"
			},

			dataRange: {
			    min: 0,
			    max: valuemax,
			    x: 'left',
			    y: 'bottom',
			    text:['高','低'],           // 文本，默认为数值文本
			    calculable : true
			},
			toolbox: {
			    show: true,
			    orient : 'vertical',
			    x: 'right',
			    y: 'center',
			    feature : {
			        mark : {show: true},
			        dataView : {show: true, readOnly: false},
			        restore : {show: true},
			        saveAsImage : {show: true}
			    }
			},
			roamController: {
			    show: true,
			    x: 'right',
			    mapTypeControl: {
			        'china': true
			    }
			},
			series : [
			    {
			        name: '脑卒中患者',
			        type: 'map',
			        mapType: 'china',
			        roam: false,
			        itemStyle:{
			            normal:{label:{show:true}},
			            emphasis:{label:{show:true}}
			        },
			        data:arraydata
			    }
			    
			     
			]
			};
			                
			myChart.setOption(option);
				 }
				},
				error : function() {

				}
			}); 
			
			</script>
		</div>
	</div>

	<div class="clear"></div>

	<div class="s_p_part3">
		<h3 class="header smaller lighter red">图表说明</h3>

		<div class="well">
			<h4 class="green smaller lighter">脑卒中患者的的地区频率频数分布</h4>

		</div>

	</div>
</body>

</html>