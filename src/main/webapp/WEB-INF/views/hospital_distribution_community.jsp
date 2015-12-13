<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
<title>Insert title here</title>
<style type="text/css">
.body {
	background-color: white
}
.s_p_part3{
	width:80%;
	height:200px;
	 
	margin-left:10%;
	margin-top:20px;
}

</style>
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=929d48e011c8e5a5efaf09804b83e1df"></script>
<!-- bootstrap & fontawesome -->

<link href="resources/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />

</head>
<body class="body">
<div id="allmap"></div>     
	
</body>
</html>
<script type="text/javascript">
//百度地图API功能
var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(116.404, 39.915), 5);

var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
/*缩放控件type有四种类型:
BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/

map.addControl(top_left_control);        
map.addControl(top_left_navigation);     
map.addControl(top_right_navigation);  


//加载社区机构数据
var opts = {
		width : 250,     // 信息窗口宽度
		height: 80,     // 信息窗口高度
		title : "信息窗口" , // 信息窗口标题
		enableMessage:true//设置允许信息窗发送短息
	   };
options={ 
		type : "get",//请求方式 
		url : "getjoincommunityinfo",//发送请求地址
		dataType : "json", 
		success :function(data) {
			$.each(data, function(i,  communityinfo) {
				if(communityinfo.uull!== null){
					var uull_array=(communityinfo.uull).split(",");
					
					var marker = new BMap.Marker(new BMap.Point(parseInt(uull_array[0]),parseInt(uull_array[1])));  // 创建标注
					var content = "上级单位："+communityinfo.upName+"<br>"+"完成病例数："+communityinfo.endCount;
					map.addOverlay(marker);               // 将标注添加到地图中
					addClickHandler(content,marker,communityinfo.name);
				}
			});
		} 
	}
$.ajax(options);
function addClickHandler(content,marker,title){
	marker.addEventListener("click",function(e){
		openInfo(content,e,title)}
	);
}
function openInfo(content,e,title){
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	opts.title=title;
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}
</script>