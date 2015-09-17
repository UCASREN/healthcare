$("#logoutbutton").click(function(){
	$("#logoutform").submit();
});
$("#people_feature").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "people_features");
});
$("#prevalence_rate_l1").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "service_dashboard");
});
$("#prevalence_rate_l2").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "service_stroke_patient");
});
$("#danger_factor_l1").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "service_whole_danger_factor");
});
$("#danger_factor_l2").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "service_high_danger_factor");
});
$("#danger_factor_l3").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "service_danger_factor");
});
$("#treat_control").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "treat_control");
});
$("#esrs_score").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "ESRS_score");
});
$("#framinghan_danger_assess").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "Framinghan_score");
});
$("#icvd_danger_assess").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "ICVD_score");
});
$("#data_resource_inner").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "dataResource/stroke_data");
});
$("#cloud_label_l1").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "year_analyse");
});
$("#cloud_label_l2").click(function(){
	$('#main').attr('src', 'http://133.133.133.73:8080/haflow/' + "real_time");
});