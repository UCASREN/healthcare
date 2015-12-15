$.ajax({
	type : "get",//请求方式
	url : "getyearinfo",//发送请求地址
	dataType : "json",
	success : function(data) {
		$.each(data,function(key,values){     
		    console.log(key);     
		    $("#province_city_count_"+key).text(values["provinceCount"]+"省"+values["cityCount"]+"市");
		    $("#join_basehospital_count_"+key).text(values["joinBaseHospitalCount"]);
		    $("#join_community_count_"+key).text(values["joinCommunityCount"]);
		    $("#join_end_count_"+key).text(values["endCount"]);
		    $("#join_danger_count_"+key).text(values["dangerCount"]);
		    $("#join_stroke_count_"+key).text(values["strokeCount"]);
		    
		 });
	}
});