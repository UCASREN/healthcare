<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title>医疗大数据分析平台</title>
<meta name="keywords" content="医疗,大数据,平台" />
<meta name="description" content="包含数据集成及发布、流程申请、虚拟环境搭建等功能" />

<!--#set var='compatible' value=''-->
<meta http-equiv="X-UA-Compatible"
	content="IE=11; IE=10; IE=9; IE=8; IE=7; IE=EDGE" />
<link rel="icon" href="home/icons/home/home.ico" type="image/x-icon" />

<!-- <script>
function FileManagerClass(){
		return this.root="http://s.lietou-static.com/",
		   	   this.base="http://core.pc.lietou-static.com",
		       this.cache={},
		       this.alis={},
		       this.init(),this
	}
	
	FileManagerClass.prototype.init=function(){
		return document.write(unescape('%3Cscript src="'+this.base+'/js/common/cdntest.js" type="text/javascript"%3E%3C/script%3E')),this
	},
	FileManagerClass.prototype.fetchCDN=function(){
		return this
	},
	FileManagerClass.prototype.extname=function(t){
		return(/(\.[^\\\/\.]+)$/g.exec(t)||[0,""])[1]
		},
	FileManagerClass.prototype.prefix=function(t){
			var e=this;
			return t=t.replace(/^\//,this.root),t.replace(/https?:\/\/(.*)\.lietou-static\.com/,function(t,s){return e.alis[s]===!1?"s"===s?t.replace(s,"i."+s):t.replace(".pc.",".ipc."):t})},
	FileManagerClass.prototype.get=function(){
				var t,e,s,i=arguments;for(s=0;s<i.length;s++)e=this.prefix(i[s]),this.cache[e]||(this.cache[e]=!0,t=this.extname(e),".css"===t?document.write(unescape('%3Clink href="'+e+'" rel="stylesheet" type="text/css"/%3E')):".js"===t&&document.write(unescape('%3Cscript src="'+e+'"%3E%3C/script%3E')));return this};
				var FileManager=new FileManagerClass;
</script>

<script>
	FileManager.get('http://core.pc.lietou-static.com/revs/css/port/www/common_6e2f763e.css',
			'http://www.pc.lietou-static.com/revs/v1/css/public_eb211bd4.css',
			'http://core.pc.lietou-static.com/revs/js/common/jquery-1.7.1.min_c7e0488b.js',
			'http://core.pc.lietou-static.com/revs/js/common/lt.core_a59af84a.js',
			'http://core.pc.lietou-static.com/revs/js/port/www/plugins/jquery.artDialog_d121c362.js',
			'http://core.pc.lietou-static.com/revs/js/common/plugins/jquery.loadingui_6a6316c4.js',
			'http://core.pc.lietou-static.com/revs/js/common/plugins/jquery.tipsui_03bbd011.js',
			'http://www.pc.lietou-static.com/revs/v1/js/lt.apps_f462ac2c.js');
</script> -->
<link rel="stylesheet" href="home/style/home/common_6e2f763e.css" />
<link rel="stylesheet" href="home/style/home/public_eb211bd4.css" />
<link rel="stylesheet" href="home/style/home/footer_47d2c33b.css" />
<link rel="stylesheet" href="home/style/home/header_c8045ce8.css" />

<script src="home/script/home/jquery-1.7.1.min_c7e0488b.js"></script>
<script src="home/script/home/lt.core_a59af84a.js"></script>
<script src="home/script/home/jquery.artDialog_d121c362.js"></script>
<script src="home/script/home/jquery.loadingui_6a6316c4.js"></script>
<script src="home/script/home/jquery.tipsui_03bbd011.js"></script>
<script src="home/script/home/lt.apps_f462ac2c.js"></script>
<script src="home/script/home/jquery.placeholderui_4ea8db00.js"></script>
<script src="home/script/home/jquery.checkboxui_1589ca87.js"></script>
<!-- <script src="home/script/home/jquery.validTip_3908ed67.js"></script> -->
<script src="home/script/home/jquery.valid_cbce39ff.js"></script>
<script src="home/script/home/quick_menu_3bf9fc99.js"></script>


<script type="text/javascript">
	var HeaderHelperConfig = {
		pageType : 0
	};
</script>

<!-- <script type="text/javascript">
  FileManager.get('http://s.lietou-static.com/revs/p/beta2/js/page/page.index.listener_cfefa656.js');
</script> -->

</head>

<body id="home">

	<header id="header-p-beta2">
		<div class="header">
			<div class="wrap">
				<div class="logo">
					<a href=""><img alt="图标" class="pngfix" src="home/images/home/change_logo.png"
						width="50px" height="50px" /><em><i
							class="icons16 icons16-home-white" title="首页"></i></em></a>
						<span style="color:#FFF;font-size:20px;font-weight:bold;">中国心脑血管病大数据平台</span>	
				</div>
				<nav>
					<!-- <ul>
						<li data-name="home"><a href="">首页</a></li>
						<li data-name="help"><a href="">帮助</a></li>
						<li data-name="haflow"><a href="http://124.16.137.206:8088/haflow/" target="_blank">haflow平台</a></li>
					</ul> -->
				</nav>
				<sec:authorize access="hasAnyRole('USER','ADMIN','SU1','SU2')">
				<div class="quick-menu">
					<div class="quick-menu-unlogined">
						<div id="NTGUID__1">
							<div class="link-menu-list">
								<span class="drop-menu-group"> 
								<a class="link-drop-menu"
									title="账户" target="_blank" href="javascript:;"><i
										data-icon="icons16-mobile-gray"
										data-icon-hover="icons16-mobile-white"
										class="icons16 icons16-mobile-gray"></i> <sec:authentication
											property="name" /></a>
								<a href="#" id="logoutbutton">退出</a>
								<c:url value="/logout" var="logoutUrl" />
								
								</span>
							</div>
						</div>
					</div>
				</div>
				</sec:authorize>
			</div>
		</div>
	</header>
	<!-- 用来退出登录的 -->
	<form action="${logoutUrl}" method="post" style="display:none;" id="logoutform">
									<input  name="${_csrf.parameterName}"
										value="${_csrf.token}" /> 
								</form> 
	<!--
 <script type="text/javascript">FileManager.get('http://core.pc.lietou-static.com/revs/js/common/header_126a5e4a.js');</script> 
 -->

	<script src="home/script/home/header_38df507b.js"></script>
	<script>
		HeaderHelper.navbar("home")
	</script>

	<div class="slider">
		<div class="slider-list">

			<div
				style="display: none;background: url(home/images/home/banner1.jpg) 50% 0px no-repeat rgb(77, 55, 34);">
				<a href="" target="_blank"></a>
			</div>

			<div
				style="display: block;background: url(home/images/home/banner2.jpg) 50% 0px no-repeat rgb(14, 81, 100);">
				<a href="" target="_blank"></a>
			</div>

		</div>

		<!-- js 追加 -->
		<div class="dot-list"></div>
	</div>
	<sec:authorize access="!hasAnyRole('USER','ADMIN','SU1','SU2')">
		<div class="wrap relative">
			<div class="form-box">
				<div class="form-title"></div>
				
				<div class="form-content" data-flag="0">
					<div class="candidate" style="left: 0px;">
						<!-- 经理人登录  -->
						<form action="javascripts:;" method="post" class="login-box" lt-plugins-valid="0.7450230794493109"
							style="left: -292px; display: none;">
						</form>
						
						<!-- 经理人注册 -->
						<form action="" method="post" class="register-box" lt-plugins-valid="0.698662273818627" 
							style="left: 0px;">
							<input type="hidden" name="isMd5" value="1"> 
							<input type="hidden" name="layer_from" value="wwwindex_rightbox_new">
							<div class="control relative">
								<input type="text" id="username" name="username" value="" 
									class="text input-xlarge" placeholder="邮箱/手机号"
									validate-title="邮箱/手机号" validate-rules="[['required']]">
							</div>
							<div class="control relative">
								<input type="password" id="password" name="password" value=""
									class="text input-xlarge" placeholder="密码" validate-title="密码"
									validate-rules="[['required','请输入$']]">
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> 
							<input type="submit" value="登 录" class="btn btn-login">
							<div class="controls clearfix">
							<!-- 	<label><input type="checkbox" id="remember-me"
									name="remember-me" checked="checked" autocomplete="off"
									style="display: none;">&nbsp;下次自动登录</label> <a class="regbtn"
									href="javascript:;" title="注册" data-selector="switchRegister">立即注册</a> 
								<a href="javascript:;" target="_blank">忘记密码？</a>
							-->
							</div>
						<!-- 	<div class="connect-login">
								<a href="javascript:;">使用其他方式登录</a>
								 <a class="icon-24 icon-24-account-qq" href="#"
									target="_blank"></a> <a class="icon-24 icon-24-account-weixin"
									href="#" target="_blank"></a> <a
									class="icon-24 icon-24-account-weibo" href="#" target="_blank"></a>
							</div> -->

						</form>
						
					</div>

				</div>
			</div>
		</div>
	</sec:authorize>
	
	<!-- 四个功能 -->
	<div class="box">
		<div class="wrap">
			<p class="subsite-btn  clearfix">
                 <a href="userdatabaseview" target="_blank"> <span class="icons48 icons48-it"></span> <b>元数据</b></a>
				 <a href="topicanalysis" target="_blank"> <span class="icons48 icons48-estate"></span> <b>主题分析</b></a>
				 <a href="applydata/applytable" target="_blank"> <span class="icons48 icons48-financial"></span> <b>数据服务</b></a>
				 <a href="applyenv/applytable" target="_blank"> <span class="icons48 icons48-medicine"></span> <b>虚拟计算</b></a>
            </p>
		</div>

	</div>
	
	<!-- 版权说明 -->
	<footer id="footer-p-beta2">
    	<hr>
		<div class="copy-footer">
        	<p>地址：西城区新街口外大街16号11楼403室（中国心脑血管病网） 邮编：100088 电话010-84022400 传真：010-84025262 </p>
            <p>Copyright © 2012-2015 中国心脑血管病网 All Rights Reserved 版权所有</p>
        	<p>技术支持：国家人口与健康科学数据共享平台-地方服务中心 </p>
            <p>京ICP备13002765号-2</p>
            <p>访客数:<span style="color:#FE6400; font-weight: bold;">9392401</span></p>
            <p><a title="51.La 网站流量统计系统" target="_blank" href="http://www.51.la/?15800568"><img style="border:none" src="home/images/home//icon_9.gif" alt="51.La 网站流量统计系统">
			</a></p> 
		</div>
	</footer>


	<script type="text/javascript" src="home/script/home/home_278079d0.js"></script>

	<script type="text/javascript" src="home/script/home/home.js"></script>

	<div style="display: none; position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; cursor: move; opacity: 0; 
		background: rgb(255, 255, 255);"></div><div id="scrollBar" class="scroll-bar">
		<ul></ul>
		<a class="back-top" href="/healthcare/#"><i></i><b></b><span class="hide">回到顶部</span></a></div>
</body>

</html>