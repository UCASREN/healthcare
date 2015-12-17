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

var myChart = echarts.init(document.getElementById('taskchartcontainera1'));
function query() {
	myChart.showLoading({
		text : "图表数据正在努力加载..."
	});
	//加载chart2的数据
	$.ajax({
		type : "get",//请求方式
		url : "getgenderdangerfactorinfo",//发送请求地址
		dataType : "json",
		data : {
			provinceid : $("#opt_province").val(),
			dangertype : $("#opt_danger_factor").val(),
			ageclassification : $("#opt_age_class").val()
		},
		success : function(data) {
			if (data) {
				line_all_data=data["all"];
				line_man_data=data["1"];
				line_woman_data=data["2"];
				
				var keyarray=new Array();
				var line_all_valuearray=new Array();
				var line_man_valuearray=new Array();
				var line_woman_valuearray=new Array();
				$(line_all_data).each(function() {
					var temp_map = this;
					if(temp_map["age"]!=null){
						keyarray.push(temp_map["age"]);//x轴坐标点只做一次就行了
						line_all_valuearray.push(temp_map["count"]);
					}
				});
				$(line_man_data).each(function() {
					var temp_map = this;
					if(temp_map["age"]!=null){
						line_man_valuearray.push(temp_map["count"]);
					}
				});
				$(line_woman_data).each(function() {
					var temp_map = this;
					if(temp_map["age"]!=null){
						line_woman_valuearray.push(temp_map["count"]);
					}
				});
				
				myChart.hideLoading();
				option = {
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
					                formatter: '{value}'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'全部',
					            type:'line',
					            data:line_all_valuearray,
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
					        },{
					            name:'男',
					            type:'line',
					            data:line_man_valuearray,
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
					        },{
					            name:'女',
					            type:'line',
					            data:line_woman_valuearray,
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
					                    
				myChart.setOption(option);
			}
		}
	});
};
//页面加载完成后就执行
$(function(){ 
	query(); 
}); 
