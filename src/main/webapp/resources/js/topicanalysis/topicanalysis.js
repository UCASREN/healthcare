$().ready(function() {
	
	$("#logoutbutton").click(function(){
		$("#logoutform").submit();
	});
	
	//住院数据
	$("#topic_inhospital_home").click(function(){
		$('#main').attr('src', '/healthcare/topic_inhospital_home');
	});
	
	$("#topic_inhospital_patientsNum").click(function(){
		$('#main').attr('src', '/healthcare/topic_inhospital_patientsNum');
	});
	
	$("#topic_inhospital_approachAndillstate").click(function(){
		$('#main').attr('src', '/healthcare/topic_inhospital_approachAndillstate');
	});
	
	$("#topic_outhospital_approachAndillstate").click(function(){
		$('#main').attr('src', '/healthcare/topic_outhospital_approachAndillstate');
	});
	
	$("#topic_beInhospital_costs").click(function(){
		$('#main').attr('src', '/healthcare/topic_beInhospital_costs');
	});
	
}); // end ready
