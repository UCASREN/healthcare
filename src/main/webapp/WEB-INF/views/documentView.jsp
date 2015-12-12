<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String swfFilePath = session.getAttribute("swfFilePath").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../resources/js/flexpaper_js/jquery.js"></script>
<script type="text/javascript" src="../resources/js/flexpaper_js/flexpaper_flash.js"></script>
<script type="text/javascript" src="../resources/js/flexpaper_js/flexpaper_flash_debug.js"></script>
<style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }   
			#flashContent { display:none; }
        </style> 

<title>在线预览</title>
</head>
<body> 
        <div style="position:absolute;left:250px;top:10px;">
	        <a id="viewerPlaceHolder" style="width:820px;height:850px;display:block"></a>
	        
	        <script type="text/javascript"> 
				var fp = new FlexPaperViewer(	
						 '/healthcare/resources/swf/FlexPaperViewer',
						 'viewerPlaceHolder', { config : {
						 SwfFile : escape('<%=swfFilePath%>'),
						 Scale : 1.2, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5,
						 ZoomInterval : 0.2,
						 FitPageOnLoad : false,
						 FitWidthOnLoad : false,
						 FullScreenAsMaxWindow : false,
						 ProgressiveLoading : false,
						 MinZoomSize : 0.2,
						 MaxZoomSize : 5,
						 SearchMatchAll : false,
						 InitViewMode : 'SinglePage',
						 
						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : true,
						 CursorToolsVisible : false,
						 SearchToolsVisible : false,
  						
  						 localeChain: 'en_US'
  						 //zh_CN
						 }});
	        </script>            
        </div>
</body>
</html>