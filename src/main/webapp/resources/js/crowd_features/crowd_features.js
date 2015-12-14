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
//$("#opt_province").change(function(){
//	if($("#opt_province").val()!=""){
//		$("#opt_city").empty();
//		$("#opt_city").append("<option value=''>全部</option>"); 
//		$.ajax({
//			type : "get",//请求方式
//			url : "getcityoptions",//发送请求地址
//			dataType : "json",
//			data:{
//				provinceid:$("#opt_province").val()
//			},
//			success : function(data) {
//				$(data).each(function (i,city) {
//					name=city.split("_")[1];
//					$("#opt_city").append("<option value='"+city+"'>"+name+"</option>");
//				});
//			}
//		});
//	}
//});