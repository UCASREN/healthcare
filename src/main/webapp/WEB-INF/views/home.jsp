
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
	<!--#set var='compatible' value=''-->
	<!-- 
<script type="text/javascript">FileManager.get('http://core.pc.lietou-static.com/revs/css/common/header_c8045ce8.css');</script>
 -->


	<header id="header-p-beta2">
		<div class="header">
			<div class="wrap">
				<div class="logo">
					<a href=""><img alt="图标" class="pngfix" src="img/home.png"
						width="110" height="40" /><em><i
							class="icons16 icons16-home-white" title="首页"></i></em></a>
				</div>
				<nav>
					<ul>
						<li data-name="home"><a href="">首页</a></li>
						<li data-name="help"><a href="">帮助</a></li>
						<li data-name="haflow"><a href="http://124.16.137.206:8088/haflow/" target="_blank">haflow平台</a></li>
					</ul>
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
				style="background: url(home/images/home/55cc6f4990ee82c1813be31803a.jpg) center 0 no-repeat #4d3722;">
				<a href="" target="_blank"></a>
			</div>

			<div
				style="background: url(home/images/home/55cc6f4990ee82c1813be31803a.jpg) center 0 no-repeat #0e5164;">
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
						<form action="" method="post" class="login-box"
							lt-plugins-valid="0.7450230794493109"
							style="left: -292px; display: none;">
							<input type="hidden" name="isMd5" value="1"> <input
								type="hidden" name="layer_from" value="wwwindex_rightbox_new">
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
								value="${_csrf.token}" /> <input type="submit" value="登 录"
								class="btn btn-login">
							<div class="controls clearfix">
								<label><input type="checkbox" id="remember-me"
									name="remember-me" checked="checked" autocomplete="off"
									style="display: none;">&nbsp;下次自动登录</label> <a class="regbtn"
									href="javascript:;" title="注册" data-selector="switchRegister">立即注册</a>
								<a href="javascript:;" target="_blank">忘记密码？</a>
							</div>
							<div class="connect-login">
								使用其他方式登录 <a class="icon-24 icon-24-account-qq" href="#"
									target="_blank"></a> <a class="icon-24 icon-24-account-weixin"
									href="#" target="_blank"></a> <a
									class="icon-24 icon-24-account-weibo" href="#" target="_blank"></a>
							</div>
						</form>
						<!-- 经理人注册 -->
						<form action="javascript:;" method="post" class="register-box"
							lt-plugins-valid="0.698662273818627" style="left: 0px;">
							<input type="hidden" name="layer_from"
								value="wwwindex_rightbox_new"> <input type="hidden"
								name="regc_new_log" value="regc_new_log">
							<div class="control relative">
								<input autocomplete="off" type="text" name="username" value=""
									placeholder="邮箱/手机号" class="text input-xlarge"
									data-selector="checkEmail" validate-title="邮箱/手机号"
									validate-rules="[['required','请输入$'],['dynrule','checkPhoneEmail']]">
							</div>
							<div class="control relative">
								<input autocomplete="off" type="password" name="password"
									value="" placeholder="密码(6-16字母、数字、无空格)"
									class="text input-xlarge" validate-title="密码"
									validate-rules="[['required','请输入$'],['length',{min:6,max:16},'$1长度不能$2$3个字符'],['pattern',/^[a-zA-Z0-9]+$/ig,'$只能数字或字母']]">
							</div>
							<input type="submit" value="免费注册" class="btn btn-register">
							<div class="clearfix controls" validate-group="checkbox"
								validate-title="用户服务协议"
								validate-rules="[['required','您必须接受“$1”才能注册']]">
								<label><input type="checkbox" class="input-checkbox"
									checked="checked" autocomplete="off" style="display: none;">&nbsp;接受</label><a
									href="http://www.liepin.com/user/agreement.shtml" class="xieyi"
									target="_blank">用户服务协议</a>
								<p>
									已有帐号,&nbsp;&nbsp;<a href="javascript:;" title="登录"
										data-selector="switchLogin">马上登录</a>
								</p>
							</div>
						</form>
					</div>

				</div>
			</div>
		</div>
	</sec:authorize>
	<!-- 四个功能 -->
	<div class="box">
		<div class="wrap">
			<p class="subsite-btn clearfix">
				<a href="userdatabaseview" target="_blank"> <span
					class="icons48 icons48-it"></span> <b>元数据</b>
				</a> 
				
				<a href="datasetanalysis" target="_blank"> <span
					class="icons48 icons48-medicine"></span> <b>主题报告</b>
				</a>
				
				<a href="applydata/applytable" target="_blank"> <span
					class="icons48 icons48-estate"></span> <b>数据服务</b>
				</a> 
				
				<a href="applyenv/applytable" target="_blank"> <span
					class="icons48 icons48-financial"></span> <b>虚拟计算</b>
				</a> 
				
				<a href="#" target="_blank"> <span
					class="icons48 icons48-telephone"></span> <b>联系我们</b>
				</a> 

				<!--  <a href="">
	    <span class="icons48 icons48-car"></span>
	    <b>汽车·制造</b>
	  </a>
	  <a href="">
	    <span class="icons48 icons48-medicine"></span>
	    <b>医疗·化工</b>
	  </a> -->

			</p>
		</div>
	</div>

	<!-- 搜索框 -->
	<div class="box bg-gray">
		<div class="wrap search">
			<div class="search-top clearfix">
				<div class="search-main">
					<form id="search_form" method="get"
						action="http://www.liepin.com/zhaopin/">
						<input type="hidden" name="sfrom"
							value="click-pc_homepage-centre_searchbox-search_new" />
						<div class="search-main-top clearfix">
							<div class="input-main float-left">
								<input name="key" placeholder="输入关键词：如 医疗数据" type="text"
									maxlength="100" />
							</div>
							<button type="submit" class="search-btn float-right">
								<i class="home-sprite"></i>搜索
							</button>
						</div>
					</form>
					<p class="search-link">

						<a target="_blank"
							href="http://www.liepin.com/zhaopin/?searchField=1&key=%E4%BA%A7%E5%93%81%E7%BB%8F%E7%90%86#sfrom=click-pc_homepage-centre_keywordjobs-search_new"
							title="产品经理">link1</a> <a target="_blank" href="" title="销售总监">link2</a>

						<a target="_blank" href="" title="财务总监">link3</a> <a
							target="_blank" href="" title="投资经理">link4</a> <a target="_blank"
							href="" title="人力资源总监">link5</a>

					</p>
				</div>
			</div>
		</div>
	</div>

	<!-- 友情链接 -->
	<div class="box">
		<div class="wrap links">
			<div class="links-wrap clearfix">
				<span class="links-title float-left">友情链接：</span>
				<ul class="links-content float-right">
					<li><a href="http://www.xjhr.com/" target="_blank">新疆人才网</a>
					<li><a href="http://www.doctorjob.com.cn/" target="_blank">中国医疗人才网</a>
					<li><a href="http://www.job910.com/" target="_blank">教师招聘</a>
					<li><a href="http://www.zyue.com/" target="_blank">驾校</a>
					<li><a href="http://www.huoche.com/" target="_blank">火车票</a>
					<li><a href="http://company.liepin.com/qiye/" target="_blank">企业招聘</a>
					<li><a href="http://www.yinhangzhaopin.com/" target="_blank">银行招聘网</a>
					<li><a href="http://www.hunt007.com/" target="_blank">找工易</a>
					<li><a href="http://www.liepin.com/about/weblink/"
						target="_blank">更多</a></li>
				</ul>
			</div>
			<p class="clearfix">
				<span class="links-title float-left">常用链接：</span> <span
					class="links-content float-right"> <a
					href="http://a.liepin.com/" target="_blank" title="找工作">找工作</a> <a
					href="http://bj.liepin.com/" target="_blank" title="北京猎聘网">北京猎聘网</a>
					<a href="http://sh.liepin.com/" target="_blank" title="上海猎聘网">上海猎聘网</a>
					<a href="http://gz.liepin.com/" target="_blank" title="广州猎聘网">广州猎聘网</a>
					<a href="http://sz.liepin.com/" target="_blank" title="深圳猎聘网">深圳猎聘网</a>
					<a href="http://nj.liepin.com/" target="_blank" title="南京猎聘网">南京猎聘网</a>
					<a href="http://hz.liepin.com/" target="_blank" title="杭州猎聘网">杭州猎聘网</a>
					<a href="http://article.liepin.com/gw/" target="_blank"
					title="岗位职责">岗位职责</a>
				</span>
			</p>
		</div>
	</div>
	<!-- 
<script type="text/javascript">FileManager.get('http://core.pc.lietou-static.com/revs/css/common/footer_47d2c33b.css');</script> -->

	<!-- 版权说明 -->
	<footer id="footer-p-beta2">
		<hr />
		<div class="copy-footer">
			<p>医疗大数据分析平台---中国科学院软件研究所</p>
			<p>Copyright&copy;2000-2015 All Rights Reserved</p>
		</div>
	</footer>

	<!-- <script type="text/javascript">
  FileManager.get('http://www.pc.lietou-static.com/revs/v1/js/home_278079d0.js');
</script> -->

	<script type="text/javascript" src="home/script/home/home_278079d0.js"></script>
	<!-- <script type="text/javascript">window.FileManager&&FileManager.get('http://core.pc.lietou-static.com/revs/js/common/stat_f37a07ef.js');</script>
 -->

<script type="text/javascript" src="home/script/home/home.js"></script>
</body>

</html>