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
	//加载chart1的数据
	
	$.ajax({
		type : "get",//请求方式
		url : "getgenderinfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			accodeup : $("#opt_basehospital").val(),
			community : $("#opt_community").val()
		},
		success : function(data) {
			if (data) {
		//				console.log(data);
				var new_data = new Array();
				$(data).each(function() {
					var temp_map = this;
					if(temp_map["gender"]!=null){
						var new_map = {};
						new_map["name"] = temp_map["gender"];
						new_map["value"] = temp_map["percentage"];
						new_data.push(new_map);
					}
				});

				myChart1.hideLoading();
				//设置option
				option1 = {
						title : {
							text : '人群性别构成',
							subtext : '统计',
							x : 'center'
						},
						tooltip : {
							trigger : 'item',
							formatter : "{a} <br/>{b} : {c} ({d}%)"
						},
						legend : {
							orient : 'vertical',
							x : 'left',
							data : [ '男', '女' ]
						},
						toolbox : {
							show : true,
							feature : {
								mark : {
									show : true
								},
								dataView : {
									show : true,
									readOnly : false
								},
								magicType : {
									show : true,
									type : [ 'pie', 'funnel' ],
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
							name : '性别',
							type : 'pie',
							radius : '55%',
							center : [ '50%', '60%' ],
							data : [

							]
						} ]
					};
				myChart1.setOption(option1);
				var temp_option = myChart1.getOption();
				temp_option.series[0].data = new_data;
				myChart1.setOption(temp_option);
			}
		}
	});
	
	//加载chart2的数据
	option2 = {
			title : {
				text : '人群城乡分布',
				subtext : '统计',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : [ '城市', '乡镇' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'pie', 'funnel' ],
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
				name : '城乡',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : [

				]
			} ]
		};
	myChart2.setOption(option2);
	$.ajax({
		type : "get",//请求方式
		url : "getregioninfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			accodeup : $("#opt_basehospital").val(),
			community : $("#opt_community").val()
		},
		success : function(data) {
			if (data) {
				console.log(data);
				var new_data = new Array();
				$(data).each(function() {
					var temp_map = this;
					if(temp_map["region"]!=null){
						var new_map = {};
						new_map["name"] = temp_map["region"];
						new_map["value"] = temp_map["percentage"];
						new_data.push(new_map);
					}
				});

				myChart2.hideLoading();
				var temp_option = myChart2.getOption();
				temp_option.series[0].data = new_data;
				myChart2.setOption(temp_option);
			}
		}
	});
	//加载chart3的数据
	$.ajax({
		type : "get",//请求方式
		url : "getageinfo",//发送请求地址
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
					if(temp_map["age"]!=null){
						keyarray.push(temp_map["age"]);
						valuearray.push(temp_map["percentage"]);
					}
				});

				myChart3.hideLoading();
				var option3 = {
					    title : {
					        text: '人群年龄构成',
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
					            axisLabel: {formatter:'{value}',}
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
};
//页面加载完成后就执行
$(function(){ 
	query(); 
}); 
