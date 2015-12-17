<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>入院人次</title>
<style type="text/css">
.s_p_part1{
	width:80%;
	float:left;
	margin-left:200px;
	margin-top:20px;
	overflow:hidden;
}

.s_p_part2{
	width:80%;
	float:left;
	margin-top:20px;
	overflow:hidden;
}

.clear{ 
	clear:both
}

.s_p_part3{
	width:80%;
	height:380px;
	margin-left:10%;
	margin-top:20px;
}

.s_p_p1{
	width:40%;
	height:380px;
	float:left;
	margin-left:5%;
	border:3px solid;
	border-color:#cccccc;
	text-align:center;
	 
	 
	 
	
}
.s_p_p2{
	width:40%;
	height:380px;
	float:left;
	margin-left:7%;
	border:3px solid;
	border-color:#cccccc;
	line-height:300px;
	text-align:center;
}
.s_p_p3{
	width:40%;
	height:380px;
	float:left;
	margin-left:5%;
	border:3px solid;
	border-color:#cccccc;
	margin-top:20px;
	text-align:center;
	 
	 
	 
	 
}
.s_p_p4{
	width:40%;
	/* height:380px; */
	float:left;
	margin-left:7%;
	border:3px solid;
	border-color:#cccccc;
	margin-top:20px;
}
.body{background-color:white}
</style>
<link rel="shortcut icon" href="images/bitbug_favicon.ico" type="image/x-icon" /> 
<script src="resources/js/echarts-all.js"></script>
<script type="text/javascript" src="resources/js/jquery-2.1.1.js" charset="utf-8"></script>

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="resources/css/font-awesome.min.css"/>
<link rel="stylesheet" href="resources/css/ace.min.css"/>

<script type="text/javascript">		
function query(){
	fill_myChart1();
	fill_myChart2();
	fill_myChart3();
}

//填写科室
var fillHospitalDeps = function(){
	options1={ 
			type : "get",//请求方式 
			url : "/healthcare/getAllRYKB",//发送请求地址
			dataType : "json", 
			data:{ 
			}, 
			success :function(data) {
				var rykb_list = $('#hospital_dep'); 
				for(var i=0; i<data.length; i++){
					var tmp = data[i].split(',');
					var option = '<option value="'+tmp[0]+'">'+tmp[1]+'</option>';
					rykb_list.append(option);
				}
				fill_myChart1();
				fill_myChart2();
				fill_myChart3();
			} 
     }
     $.ajax(options1);
}
fillHospitalDeps();

</script>
		 		 
	 
</head>
<body class="body">
	
	<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="head" style=" height:35px;line-height:35px; background-color:#87CEEB;text-align:right;padding-top:5px;padding-right:20px ">
       	<div>
            <span class="text-primary">时间范围</span>
            <select name="select" id="home_time_opt" class="">
            	<option value="week">最近一周</option>
             	<option value="month">最近一月</option>
             	<option value="year">最近一年</option>
            </select>
            
            <span class="text-primary">科室</span>
            <select name="select" id="hospital_dep" class="">
            	<option value="0">全部</option>
            </select>
            <button class="btn btn-danger" onclick="query()">查询</button>
      	</div>
    </div>

	<div class="s_p.part1" style="margin-top:20px;">
		<div class="s_p_p1">
			<div id="head" style=" height:8%;line-height:35px;text-align:right;">
	        	<div>
		            <span class="text-primary">病种</span>
		            <select name="select" id="part1_bz" class="">
		            	<option value="1">短暂性脑缺血发作</option>
		             	<option value="2">脑出血</option>
		             	<option value="3">脑梗死</option>		 
		             	<option value="4">蛛网膜下腔出血</option>
		            </select>
		            <button class="btn btn-danger" onclick="fill_myChart1()">查询</button>
	       		</div>
      		</div>
			<div id="taskchartcontainera1" style="height: 92%; width: 100%;">
				<span><i class='ace-icon fa fa-spinner fa-spin green bigger-300 '></i></span>
			</div>
			<script type="text/javascript">
			var fill_myChart1 = function(){
				myChart1 = echarts.init(document.getElementById('taskchartcontainera1'));
				var bingZhong = $('#part1_bz').val();
			    var timeType = $('#home_time_opt').val();
			   	var hospitalDeps = $('#hospital_dep').val();
				
			    myChart1.showLoading({text: "图表数据正在努力加载..."});
			     
				jQuery.ajax({
						type : "GET",
						contentType : 'application/x-www-form-urlencoded; charset=utf-8',
						data:{ 
							bingZhong : bingZhong,
							timeType : timeType,
							hospitalDeps : hospitalDeps
       					}, 
						url : 'inhospitalPatien_SexAgeConsist',
						dataType : 'json',
						success : function(data) {
						 if (data) {
							var keyarray = new Array();
							var valuearray = new Array();
							var v1 = new Array();
							var v2 = new Array();
							var v3 = new Array();
							for(var key in data[0]){
								if(key == 'XB')
									continue;
								keyarray.push(key);
								v1.push(parseFloat((data[0][key] * 1).toFixed(2)));
							}
							for(var key in data[1]){
								if(key == 'XB')
									continue;
								v2.push(parseFloat((data[1][key] * 1).toFixed(2)));
							}
							for(var key in data[2]){
								if(key == 'XB')
									continue;
								v3.push(parseFloat((data[2][key] * 1).toFixed(2)));
							}
							option = {
								    title : {
								        text: '性别年龄构成',
								        subtext: '入院情况'
								    },
								    tooltip : {
								        trigger: 'axis',
								    },
								    legend: {
								        data:['男','女','全部']
								    },
								    toolbox: {
								        show : true,
								        feature : {
								            mark : {show: true},
								            dataView : {show: true, readOnly: false},
								            magicType : {show: true, type: ['line', 'bar']},
								            restore : {show: true},
								            saveAsImage : {show: true}
								        }
								    },
								    calculable : true,
								    xAxis : [
								        {
								            type : 'category',
								            axisLabel: {
								            	rotate: 30,
								            },
								            boundaryGap : false,
								            data :  keyarray
								        }
								    ],
								    yAxis : [
								        {
								            type : 'value',
								            axisLabel: {
								            	formatter:'{value}'
								            }
								        }
								    ],
								    series : [
								        {
								            name:'男',
								            type:'line',
								            data: v1,
								            markPoint : {
								                data : [
								                    {type : 'max', name: '最大值'},
								                    {type : 'min', name: '最小值'}
								                ]
								            },
								            markLine : {
								                data : [
								                    {type : 'average', name: '平均值'}
								                ]
								            },
								     /*        itemStyle:{
								            	normal:{
								            		color:'#48D1CC'
								            	}
								            } */
								        },
								        {
								            name:'女',
								            type:'line',
								            data: v2,
								            markPoint : {
								                data : [
								                    {type : 'max', name: '最大值'},
								                    {type : 'min', name: '最小值'}
								                ]
								            },
								            markLine : {
								                data : [
								                    {type : 'average', name: '平均值'}
								                ]
								            },
								       /*      itemStyle:{
								            	normal:{
								            		color:'#48D1CC'
								            	}
								            } */
								        },
								        {
								            name:'全部',
								            type:'line',
								            data: v3,
								            markPoint : {
								                data : [
								                    {type : 'max', name: '最大值'},
								                    {type : 'min', name: '最小值'}
								                ]
								            },
								            markLine : {
								                data : [
								                    {type : 'average', name: '平均值'}
								                ]
								            },
								       /*      itemStyle:{
								            	normal:{
								            		color:'#48D1CC'
								            	}
								            } */
								        }
								         
								    ]
								};
							
							myChart1.hideLoading();
							myChart1.setOption(option);
								
							}
						},
						error : function() {
							domMessage.innerHTML = '数据缺失，无法显示！';
							myChart1.showLoading({text: "数据缺失，无法显示！"});
						}
					});//end ajax
			}
			</script>
		</div>
		
		<div class="s_p_p2">
			<div id="head" style="height:8%;line-height:35px;text-align:right;">
	        	<div>
		            <span class="text-primary">统计粒度</span>
		            <select name="select" id="part2_showsize" class="">
		            	<option value="day">按天统计</option>
		            	<!-- <option value="week">按周统计</option>
		             	<option value="month">按月统计</option>
		             	<option value="quarter">按季统计</option>
		             	<option value="year">按年统计</option> -->
		            </select>
		            <button class="btn btn-danger" onclick="fill_myChart2()">查询</button>
	       		</div>
	      	</div>
			<div id="containera2" style="height: 92%; width: 100%;">
				<i class='ace-icon fa fa-spinner fa-spin green bigger-300'></i>
			 </div>
			<script type="text/javascript">
			var fill_myChart2 = function(){
				 myChart2 = echarts.init(document.getElementById('containera2') ,'infographic');
				 var showSize = $('#part2_showsize').val();
				 var timeType = $('#home_time_opt').val();
				 var hospitalDeps = $('#hospital_dep').val();
			     
			     myChart2.showLoading({text: "图表数据正在努力加载..."});
				 
				 jQuery.ajax({
						type : "GET",
						contentType : 'application/x-www-form-urlencoded; charset=utf-8',
						url : 'inhospitalPatienNum_bytime',
						data : {
							showSize:showSize,
							timeType:timeType,
							hospitalDeps:hospitalDeps
						},
						dataType : 'json',
						success : function(data) {
						 if (data) {
							var size = $('#part2_showsize').text();
							var keyarray = new Array();
							var valuearray = new Array();
							for(var key in data){
								keyarray.push(key);
								valuearray.push(parseFloat((data[key] * 1).toFixed(2)));
							}
								
							option = {
								title : {
									text : '入院患者时间变化',
									subtext : '入院情况',
									x : 'left'
								},
								tooltip : {
									trigger : 'item',
									//formatter : "{a} <br/>{b} : {c} %"
								},
								toolbox: {
								        show : true,
								        feature : {
								            mark : {show: true},
								            dataView : {show: true, readOnly: false},
								            magicType : {show: true, type: ['line', 'bar']},
								            restore : {show: true},
								            saveAsImage : {show: true}
								        }
								},
								calculable : true,
								xAxis : [
									        {
									            type : 'category',
									            boundaryGap : false,
									            axisLabel: {
									            	rotate: 30,
									            },
									            data :  keyarray
									        }
									    ],
							    yAxis : [
							        {
							            type : 'value',
							            axisLabel: {
							            	formatter:'{value}'
							            }
							        }
							    ],
							    series : [
							        {
							            name:size,
							            type:'line',
							            data: valuearray,
							            markPoint : {
							                data : [
							                    {type : 'max', name: '最大值'},
							                    {type : 'min', name: '最小值'}
							                ]
							            },
							            markLine : {
							                data : [
							                    {type : 'average', name: '平均值'}
							                ]
							            },
							            itemStyle:{
							            	normal:{
							            		color:'#48D1CC'
							            	}
							            }
							        }
							    ]
							};
							myChart2.hideLoading();
							myChart2.setOption(option);
								}
							},
							error : function() {
	
							}
					});//end ajax 
				}
			</script>

   </div>
	</div>
	
	<div class="s_p.part2">
		<div class="s_p_p3" >
			<div id="head" style=" height:8%;line-height:35px;text-align:right;">
		        	<div>
			            <span class="text-primary">性别</span>
			            <select name="select" id="part3_sex" class="">
			            	<option value="0">全部</option>
			             	<option value="1">男</option>
			             	<option value="2">女</option>
			            </select>
			           <span class="text-primary">年龄段</span>
			            <select name="select" id="part3_age" class="xla_k">
				            <option value="(0,200)">全部</option>
				            <option value="(0,20)">0-20</option>
				            <option value="(21,40)">21-40</option> 
				            <option value="(41,60)">41-60</option>
				            <option value="(61,80)">61-80</option> 
				            <option value="(81,200)">80+</option>    
			           </select>
			           <button class="btn btn-danger" onclick="fill_myChart3()">查询</button>
		       		</div>
	      		</div>
			<div id="taskchartcontainera3" style="height:92%; width: 100%; line-height:100%;">
				<i class='ace-icon fa fa-spinner fa-spin green bigger-300'></i>
			</div>
			<script type="text/javascript">
			var fill_myChart3 = function(){
				myChart3 = echarts.init(document.getElementById('taskchartcontainera3'), 'infographic');
				var paramMap = new Object();
			    paramMap["age"] = $('#part3_age').val();
			    paramMap["sex"] = $('#part3_sex').val()
			    paramMap["timeType"] = $('#home_time_opt').val();
			    paramMap["hospitalDeps"] = $('#hospital_dep').val();
			     
			    myChart3.showLoading({text: "图表数据正在努力加载..."});
				jQuery.ajax({
					type : "GET",
					contentType : 'application/x-www-form-urlencoded; charset=utf-8',
					url : '/healthcare/inhospitalPatienConsist',
					data:paramMap,
					dataType : 'json',
					success : function(data) {
						 if (data) {
								var array=new Array();
								for(var key in data){
									array.push({name:key,value:new Number(data[key] * 100).toFixed(2)});
						}
						option = {
							title : {
								text : '入院患者病种构成',
								subtext : '入院情况',
								x : 'left'
							},
							tooltip : {
								trigger : 'item',
								formatter : "{a} <br/>{b} : {c} %"
							},

							toolbox : {
								show : true,
								feature : {
									mark : {
										show : false
									},
									dataView : {
										show : true,
										readOnly : false
									},
									magicType : {
										show : true,
										type : [
												'pie',
												'funnel' ],
										option : {
											funnel : {
												x : '25%',
												width : '50%',
												funnelAlign : 'left',
												max : 1548
											}
										}
									},
									restore : {
										show : true
									},
									saveAsImage : {
										show : true
									}
								}
							},
							calculable : true,
							series : [ {
								name : '病种比例',
								type : 'pie',
								radius : '55%',
								center : [
										'50%',
										'60%' ],
								data : array
							} ]
						};
						myChart3.hideLoading();
						myChart3.setOption(option);
							}
						},
						error : function() {

						}
					}); 
			}
			</script>
		</div>
	
	</div>
	
	<div class="clear"></div>
     
    <div class="s_p_part3">
    	<!-- <h3 class="header smaller lighter red">图表说明</h3>

		<div class="well">
			<h4 class="green smaller lighter">40岁以上人群的年龄频数分布</h4>
			40岁以上人群按照年龄段的频数分布
		</div>
                               <hr style="margin:0px;height:1px;border:0px;background-color:#D5D5D5;color:#D5D5D5;"/>
		<div class="well well-lg">
			<h4 class="blue">40岁以上人群的性别频数分布</h4>
			40岁以上人群按照性别的频数分布
		</div>
		  <hr style="margin:0px;height:1px;border:0px;background-color:#D5D5D5;color:#D5D5D5;"/>
		<div class="well well-lg">
			<h4 class="green smaller lighter">40岁以上人群的城乡频数分布</h4>
			40岁以上人群按照城乡的频数分布
		</div> -->
	</div>
         
    
</body>
</html>