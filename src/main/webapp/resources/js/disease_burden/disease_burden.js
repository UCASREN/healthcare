$.ajax({
	type : "get",//请求方式
	url : "getprovinceoptions",//发送请求地址
	dataType : "json",
	success : function(data) {
		$.each(data,function(id,provincename){
			$("#opt_province").append("<option value='"+id+"000000'>"+provincename+"</option>"); 
		});
	}
});
$.ajax({
	type : "get",//请求方式
	url : "getjoinbasehospitalinfo",//发送请求地址
	dataType : "json",
	success : function(data) {
		$.each(data, function(i,  basehospitalinfo) {
			$("#opt_basehospital").append("<option value='"+basehospitalinfo.uuCode+"'>"+basehospitalinfo.name+"</option>"); 
		});
	}
});
$("#opt_province").change(function(){
	if($("#opt_province").val()!=""){
		$("#opt_basehospital").empty();
		$("#opt_basehospital").append("<option value=''>全部</option>"); 
		$.ajax({
			type : "get",//请求方式
			url : "getjoinbasehospitalfromprovinceinfo",//发送请求地址
			dataType : "json",
			data:{
				provinceid:$("#opt_province").val()
			},
			success : function(data) {
				$.each(data, function(i,  basehospitalinfo) {
					$("#opt_basehospital").append("<option value='"+basehospitalinfo.uuCode+"'>"+basehospitalinfo.name+"</option>"); 
				});
			}
		});
	}
});
$("#opt_basehospital").change(function(){
if($("#opt_basehospital").val()!=""){
	$("#opt_community").empty();
	$("#opt_community").append("<option value=''>全部</option>"); 
	$.ajax({
		type : "get",//请求方式
		url : "getjoincommunityinfofromaccode",//发送请求地址
		dataType : "json",
		data:{
			accode:$("#opt_basehospital").val()
		},
		success : function(data) {
			$.each(data, function(i,  communityinfo) {
				$("#opt_community").append("<option value='"+communityinfo.uuCode+"'>"+communityinfo.name+"</option>");
			});
		}
	});
}
});

var myChart1 = echarts.init(document.getElementById('taskchartcontainera1'));
var myChart2 = echarts.init(document.getElementById('taskchartcontainera2'));
var myChart3 = echarts.init(document.getElementById('taskchartcontainera3'));
var myChart4 = echarts.init(document.getElementById('taskchartcontainera4'));
function query() {
	myChart1.showLoading({
		text : "图表数据正在努力加载..."
	});
	myChart2.showLoading({
		text : "图表数据正在努力加载..."
	});
	myChart3.showLoading({
		text : "图表数据正在努力加载..."
	});
	myChart4.showLoading({
		text : "图表数据正在努力加载..."
	});
	//加载chart1的数据
	$.ajax({
		type : "get",//请求方式
		url : "getgenderstrokeinfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			accodeup : $("#opt_basehospital").val(),
			community : $("#opt_community").val()
		},
		success : function(data) {
			if (data) {
				var keyarray=new Array();
				var valuearray=new Array();
				$(data).each(function() {
					var temp_map = this;
					if(temp_map["gender"]!=null){
						keyarray.push(temp_map["gender"]);
						valuearray.push(temp_map["percentage"]);
					}
				});

				myChart1.hideLoading();
				var option1 = {
					    title : {
					        text: '性别患病率',
					        subtext: ''
					    },
					    tooltip : {
					        trigger: 'axis',
					        formatter: '{b}: {c} %'
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
					            data :  keyarray,
					            axisLabel: {interval: 0, rotate: 30}
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel: {formatter:'{value} %',}
					        }
					    ],
					    series : [
					        {
					            name:'',
					            type:'bar',
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
				myChart1.setOption(option1);
			}
		}
	});
	//加载chart2的数据
	$.ajax({
		type : "get",//请求方式
		url : "getagestrokeinfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			accodeup : $("#opt_basehospital").val(),
			community : $("#opt_community").val()
		},
		success : function(data) {
			if (data) {
				var keyarray=new Array();
				var valuearray=new Array();
//				var keyarray=['40-44','45-49','50-54','55-59','60-64','65-69','70-74','75-79','80+']
				$(data).each(function() {
					var temp_map = this;
					if(temp_map["age"]!=null){
						keyarray.push(temp_map["age"]);
						valuearray.push(temp_map["percentage"]);
					}
				});

				myChart2.hideLoading();
				option2 = {
					    title : {
					        text: '年龄患病率',
					        subtext: ''
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['变化曲线']
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
					            data :keyarray //['40-44','45-49','50-54','55-59','60-64','65-69','70-74','75-79','80+']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel : {
					                formatter: '{value} %'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'变化情况',
					            type:'line',
					            data:valuearray,
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
					            }
					        }
					    ]
					};
					                    
				myChart2.setOption(option2);
			}
		}
	});
	//加载chart3的数据
	$.ajax({
		type : "get",//请求方式
		url : "getregionstrokeinfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			accodeup : $("#opt_basehospital").val(),
			community : $("#opt_community").val()
		},
		success : function(data) {
			if (data) {
				var keyarray=new Array();
				var valuearray=new Array();
				$(data).each(function() {
					var temp_map = this;
					if(temp_map["region"]!=null){
						keyarray.push(temp_map["region"]);
						valuearray.push(temp_map["percentage"]);
					}
				});

				myChart3.hideLoading();
				var option3 = {
					    title : {
					        text: '城乡患病率',
					        subtext: ''
					    },
					    tooltip : {
					        trigger: 'axis',
					        formatter: '{b}: {c} %'
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
					            data :  keyarray,
					            axisLabel: {interval: 0, rotate: 30}
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel: {formatter:'{value} %',}
					        }
					    ],
					    series : [
					        {
					            name:'',
					            type:'bar',
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
				myChart3.setOption(option3);
			}
		}
	});
	//加载chart4的数据
	$.ajax({
		type : "get",//请求方式
		url : "geteducationstrokeinfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			accodeup : $("#opt_basehospital").val(),
			community : $("#opt_community").val()
		},
		success : function(data) {
			if (data) {
				var keyarray=new Array();
				var valuearray=new Array();
				$(data).each(function() {
					var temp_map = this;
					if(temp_map["education"]!=null){
						keyarray.push(temp_map["education"]);
						valuearray.push(temp_map["percentage"]);
					}
				});

				myChart4.hideLoading();
				var option4 = {
					    title : {
					        text: '受教育程度患病率',
					        subtext: ''
					    },
					    tooltip : {
					        trigger: 'axis',
					        formatter: '{b}: {c} %'
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
					            data :  keyarray,
					            axisLabel: {interval: 0, rotate: 30}
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel: {formatter:'{value} %',}
					        }
					    ],
					    series : [
					        {
					            name:'',
					            type:'bar',
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
				myChart4.setOption(option4);
			}
		}
	});
};
//页面加载完成后就执行
$(function(){ 
	query(); 
}); 
