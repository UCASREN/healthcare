$().ready(function() {
	
	$("#logoutbutton").click(function(){
		$("#logoutform").submit();
	});
	
	//住院数据
	$("#joinhospital").click(function(){
		$('#main').attr('src', 'hospital_distribution_base');
	});
	
	$("#joincommunity").click(function(){
		$('#main').attr('src', 'hospital_distribution_community');
	});
	
	$("#crowdfeature").click(function(){
		$('#main').attr('src', 'crowd_features');
	});
	
	$("#diseaseburden_map").click(function(){
		$('#main').attr('src', 'be_ill_map');
	});
	
	$("#diseaseburden_picture").click(function(){
		$('#main').attr('src', 'disease_burden');
	});
	$("#dangerfactor").click(function(){
		$('#main').attr('src', 'danger_factor');
	});
	
}); // end ready
