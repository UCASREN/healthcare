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
<%@ page import= "otc.healthcare.pojo.VMUser;"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <base href="<%=basePath%>">
 
  </head>
  
  <body>

  <form  action="/healthcare/adminpanel/listVMService" method="post" id = "form1">
   	<input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
  	<input type="button"  onclick = "listvm()" value="列出虚拟机" >
  	
  	<script language="JavaScript" type="text/javascript" > 
  	
  	function listvm()
  	{
  		document.getElementById("form1").submit();
  	}
  	</script>
  	
    
     <table border="0" cellPadding="1" cellSpacing="1"  bgcolor="#000000" width="706" height="20" >
     <tr bgcolor="#C0C0C0" class="fontnormal">
        <td ><p align="center" class="TABLE_HEAD"><font color="#000080">虚拟机ID</font></td>
        <td ><p align="center" class="TABLE_HEAD"><font color="#000080">虚拟机名称</font></td>
        <td ><p align="center" class="TABLE_HEAD"><font color="#000080">使用者</font></td>
 
    </tr>
    
     <%
       ArrayList<VMUser>  vmUserList =new ArrayList<VMUser>();
       

	       vmUserList =(ArrayList<VMUser>)request.getAttribute("vmUserList");
	       
	       if(vmUserList == null)
	       {
	         return ;
	       }
	       int n = vmUserList.size();
	      
	       for (int i = 0; i< n; i++)
	       {
	        VMUser vms = vmUserList.get(i);
	       
	        %>
	       
	       <tr bgcolor="#C0C0C0" class="fontnormal">
	       <td><%=vms.getVMid()%></td>
	       
	       <td><%= vms.getVMname()%></td>
	       
	       <td><%= vms.getVMuser()%></td>
	       <td> <%   if(vms.getVMuser()== "")
	                 {
	             %>
	            <input type="radio"    onclick="selectvm(this)"  name = "选择">
	            	  <% } %>
	            	  
	            	  
	             	 <script language="JavaScript" type="text/javascript" >
				 
					 	function selectvm(o)
					   {
					   	
					   		if (!o.checked) 
					   		{  
					                return;  
					        }  
					  	
					 	 var tr = o.parentNode.parentNode;  
				         var tds = tr.cells;  
				         
				         var cell_num = tr.cells.length;
	
					 	 document.getElementById("vmidSelect").value = tds[0].innerHTML;
					 	 document.getElementById("vmNameSelect").value =  tds[1].innerHTML;
					 	 document.getElementById("vmuser").value = "tempuser";  //使用者
					 	 
					//	 alert(document.getElementById("vmidSelect").value);	 	
					 	 
				     
					        
					         
					   }
					  </script>
	
	     	</td>
	     	
	    <%}%>  	
  
 								 
	</table>
  
</form> 


<form  action="/healthcare/adminpanel/saveVMService" method="post" id = "form3">

	 <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
	 <input type="hidden"  name="vmuser"  id ="vmuser"   value=""> 
	 <input type="hidden"  name="vmidSelect"  id="vmidSelect"  value=""> 
	 <input type="hidden" name="vmNameSelect"  id="vmNameSelect" value=""> 
	 <input type="button"  onclick = " dosave()" value="保存" >


 </form>
  
<script language="JavaScript" type="text/javascript" > 
   function dosave(){
    	// alert("vm id value is: " + document.getElementById("vmidSelect").value);
 	  	document.getElementById("form3").submit();
	}
</script>

</body> 
</html>

	
