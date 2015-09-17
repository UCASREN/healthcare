$("#logoutbutton").click(function(){
	$("#logoutform").submit();
});
$("#database_manager").click(function(){
	$('#main').attr('src', 'http://localhost:8080/healthcare/'+'databasemanager');
});
$("#applydata").click(function(){
	$('#main').attr('src', 'http://localhost:8080/healthcare/'+ "applydata/applytable");
});
$("#applyenv").click(function(){
	$('#main').attr('src', 'http://localhost:8080/healthcare/'+ "applyenv/applytable");
});