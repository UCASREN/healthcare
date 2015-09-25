<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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

<link rel="shortcut icon" href="../resources/img/bitbug_favicon.ico" type="image/x-icon" />
<script src="../resources/js/haflow/echarts-all.js"></script>
<script type="text/javascript" src="../resources/js/haflow/jquery-2.1.1.js"
	charset="utf-8"></script>

<!-- bootstrap & fontawesome -->

<link rel="stylesheet" href="../resources/css/haflow/font-awesome.min.css" />
<link rel="stylesheet" href="../resources/css/ace.min.css" />

<!--
<script type="text/javascript">
 
function changeSelect(obj){
	 
	 if( obj.value.indexOf("频率")!=-1){
		 jQuery.ajax({
				type : "GET",
				contentType : 'application/json; charset=utf-8',
				url : 'get_dashboard_data',
				dataType : 'json',
				success : function(data) {
				 if (data) {
					 
						var arraydata=new Array();
						
						for(var key in data){
						   
					 		arraydata.push({name:key,value:data[key]});
					 		
						} 
						var option = myChart.getOption();
						option.series[0].data = arraydata;
						option.dataRange.max = data['max_value'],
						myChart.setOption(option);
				 }}});
						
	}
	else if(obj.value.indexOf("频数")!=-1){
		jQuery.ajax({
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			url : 'get_dashboard_data_frequent',
			dataType : 'json',
			success : function(data) {
			 if (data) {
					var arraydata=new Array();
					 
					for(var key in data){
					   
				 		arraydata.push({name:key,value:data[key]});
				 		
					} 
					
					var option = myChart.getOption();
					option.series[0].data = arraydata;
					option.dataRange.max = data['max_value'],
					myChart.setOption(option);
			 }}});		
	} 
}
</script> -->
</head>

<body class="body">
<!--  
<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="head" style=" height:35px;line-height:35px; background-color:#87CEEB;text-align:right; ">
     <div>
            <span class="text-primary">主题</span>
            <select name="select" id="opt" class="xla_k" onchange="changeSelect(this)">
             <option value="频率">频率</option>
             <option value="频数">频数</option> 
           </select>
            <span id='wrong-message' style="color:red"></span>
            </div>
    </div>-->

	<div class="s_p_part1">
		<div id="taskchartcontainera4" style="height: 100%; width: 100%;">
			<i class='ace-icon fa fa-spinner fa-spin green bigger-300'></i>
			
			<script type="text/javascript">
			jQuery.ajax({
				type : "POST",
				contentType : 'application/x-www-form-urlencoded; charset=utf-8',
				url : 'get_dashboard_data',
			/* 	data : {  
			          // place: encodeURI('全国 englisth'),
					 place: '全国 englisth',
			       }, */
				dataType : 'json',
				success : function(data) {
				 if (data) {
						var arraydata=new Array();
						 
						for(var key in data){
						   
					 		arraydata.push({name:key,value:data[key]});
					 		
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
			    max: data['max_value'],
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
	<!-- /.col -->

</body>
</html>