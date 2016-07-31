<%@ page language="java"  pageEncoding="utf-8"%>

<%@ page import= "com.beyondsphere.xenapi.Connection" %>
<%@ page import= "com.beyondsphere.database.JDBCUtil" %>
<%@ page import= "java.util.List" %>
<%@ page import= "java.util.ArrayList" %>
<%@ page import= "java.net.URL" %>
<%@ page import= "com.beyondsphere.xenapi.Session" %>
<%@ page import= "com.beyondsphere.xenapi.Types" %>
<%@ page import= "com.beyondsphere.xenapi.Types.BadServerResponse" %>
<%@ page import= "com.beyondsphere.xenapi.Types.SessionAuthenticationFailed" %>
<%@ page import= "com.beyondsphere.xenapi.Types.XenAPIException" %>
<%@ page import= "com.beyondsphere.xenapi.VM" %>
<%@ page import= "java.awt.Window" %>
<%@ page import= "java.sql.DriverManager" %>
<%@ page import= "renlin.hunter.pojo.VMUser;"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<base href="<%=basePath%>">
<script src="./resources/js/jquery.min.js" type="text/javascript"></script>
<script src="./resources/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="./resources/plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
<script src="./resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="./resources/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
<script src="./resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="./resources/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="./resources/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="./resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="./resources/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="./resources/js/jquery.query.js"></script>
</head>
  
  <body>

   	<div>
   		<label>当前申请id： </label>
   		<input type="text" disabled="true" name="applydataid" id="applydataid" value=""/>
   	</div>
   	
  	<script language="JavaScript" type="text/javascript" >
	  	var applydataid = $.query.get('applydataid');
	  	$('#applydataid').val(applydataid);
	  	$('#applydataid').text(applydataid);
	  	
	  	listvm();
	  	
	  	function listvm(){
	  		$.ajax({ 
					type : "get",//请求方式 
					url : "/healthcare/adminpanel/listVMService",//发送请求地址
					dataType : "json", 
					success :function(data) {
						fillVM_table(data);
					} 
      		});
	  	};
	  	
	  	function fillVM_table(data){
	  		var vmTable = $('#vmTable');
	  		for(var i=0; i<data.length; i++){
    			var tr = $("<tr class='append'></tr>");
    			var vmid = data[i]['vmid'];
    			var vmName = data[i]['vmName'];
    			var vmUser = data[i]['vmUser'];
    			var vm_select = '<input type="radio" onclick="selectvm(this)" name = "选择">'
    			tr.append('<td style="text-align:center;">'+vmid+'</td>');
    			tr.append('<td style="text-align:center;">'+vmName+'</td>');
    			tr.append('<td style="text-align:center;">'+vmUser+'</td>');
    			if(vmUser == '')
    				tr.append('<td style="text-align:center;">'+vm_select+'</td>');
    			vmTable.append(tr);
    		}
	  	}
	  	
	  	
		function selectvm(o){
			if (!o.checked)
				return;  
		  	
		 	 var tr = o.parentNode.parentNode;  
	         var tds = tr.cells;  
	         
	         var cell_num = tr.cells.length;

		 	 document.getElementById("vmidSelect").value = tds[0].innerHTML;
		 	 document.getElementById("vmNameSelect").value =  tds[1].innerHTML;
		 	 document.getElementById("vmuser").value = $('#applydataid').val();  //使用者
		 }
	  	
  	</script>
  	
    
     <table id="vmTable" border="1" cellPadding="1" cellSpacing="1" width="706" height="20" >
	     <tr bgcolor="#C0C0C0" class="fontnormal">
	        <td ><p align="center" class="TABLE_HEAD"><font color="#000080">虚拟机ID</font></td>
	        <td ><p align="center" class="TABLE_HEAD"><font color="#000080">虚拟机名称</font></td>
	        <td ><p align="center" class="TABLE_HEAD"><font color="#000080">使用者</font></td>
	    </tr>
	</table>


	<form  action="/healthcare/adminpanel/saveVMService" method="post" id = "form3">
		 <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
		 <input type="hidden"  name="vmuser"  id ="vmuser" value=""> 
		 <input type="hidden"  name="vmidSelect"  id="vmidSelect"  value=""> 
		 <input type="hidden" name="vmNameSelect"  id="vmNameSelect" value=""> 
		 <input type="button"  onclick = "dosave()" value="保存" >
	</form>
  
<script language="JavaScript" type="text/javascript" > 
   function dosave(){
 	  	document.getElementById("form3").submit();
	}
</script>

</body> 
</html>

	
