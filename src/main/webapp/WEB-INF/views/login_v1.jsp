<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="utf-8" />
<title>用户登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description" />
<meta content="" name="author" />
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<!-- <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/> -->
<link href="resources/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="resources/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="resources/css/login.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME STYLES -->
<link href="resources/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
<link href="resources/css/plugins.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/layout.css" rel="stylesheet" type="text/css"/>
<link href="resources/css/light2.css" rel="stylesheet" type="text/css" id="style_color"/>
<link href="resources/css/custom.css" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->

</head>
<!-- BEGIN BODY -->
<body class="login">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<div class="logo">
	<a href="javascript:;">
	<img src="img/logo.png" alt=""/>
	</a>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" action="${loginUrl}" method="post">
		<h3 class="form-title">登录</h3>
		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			<span>
			输入用户名和密码 </span>
		</div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">用户名</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" id="username" name="username"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" id="password" name="password"/>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-success uppercase">登录</button>
			<label class="rememberme check">
			<input type="checkbox" id="remember-me" name="remember-me" value="1"/>记住我 </label>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<a href="javascript:;" id="forget-password" class="forget-password">忘记密码？</a>
		</div>
		<div class="login-options">
			<h4>其他方式登录</h4>
			<ul class="social-icons">
				<li>
					<a class="social-icon-color facebook" data-original-title="facebook" href="javascript:;"></a>
				</li>
				<li>
					<a class="social-icon-color twitter" data-original-title="Twitter" href="javascript:;"></a>
				</li>
				<li>
					<a class="social-icon-color googleplus" data-original-title="Goole Plus" href="javascript:;"></a>
				</li>
				<li>
					<a class="social-icon-color linkedin" data-original-title="Linkedin" href="javascript:;"></a>
				</li>
			</ul>
		</div>
		<div class="create-account">
			<p>
				<a href="javascript:;" id="register-btn" class="uppercase">注册新账户</a>
			</p>
		</div>
	</form>
	<!-- END LOGIN FORM -->
	<!-- BEGIN FORGOT PASSWORD FORM -->
	<form class="forget-form" action="index.html" method="post">
		<h3>忘记密码？</h3>
		<p>
			 输入您的邮箱重置密码
		</p>
		<div class="form-group">
			<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email"/>
		</div>
		<div class="form-actions">
			<button type="button" id="back-btn" class="btn btn-default">返回</button>
			<button type="submit" class="btn btn-success uppercase pull-right">提交</button>
		</div>
	</form>
	<!-- END FORGOT PASSWORD FORM -->
	<!-- BEGIN REGISTRATION FORM -->
	<form class="register-form" action="index.html" method="post">
		<h3>注册</h3>
		<p class="hint">
			 输入您账户详细信息
		</p>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">用户名</label>
			<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="用户名" name="username"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="密码" name="password"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">重新输入密码</label>
			<input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="再输入一遍密码" name="rpassword"/>
		</div>
		<div class="form-group margin-top-20 margin-bottom-20">
			<label class="check">
			<input type="checkbox" name="tnc"/> 我同意 <a href="javascript:;">
			服务 </a>
			& <a href="javascript:;">
			协议 </a>
			</label>
			<div id="register_tnc_error">
			</div>
		</div>
		<div class="form-actions">
			<button type="button" id="register-back-btn" class="btn btn-default">返回</button>
			<button type="submit" id="register-submit-btn" class="btn btn-success uppercase pull-right">提交</button>
		</div>
	</form>
	<!-- END REGISTRATION FORM -->
</div>
<div class="copyright">
	 2015 © 中国心脑血管病网.
</div>
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="resources/js/respond.min.js"></script>
<script src="resources/js/excanvas.min.js"></script> 
<![endif]-->
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery-migrate.min.js" type="text/javascript"></script>
<script src="resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="resources/js/jquery.blockui.min.js" type="text/javascript"></script>
<script src="resources/js/jquery.cokie.min.js" type="text/javascript"></script>
<script src="resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="resources/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="resources/js/metronic.js" type="text/javascript"></script>
<script src="resources/js/layout.js" type="text/javascript"></script>
<script src="resources/js/demo.js" type="text/javascript"></script>
<script src="resources/js/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
jQuery(document).ready(function() {     
Metronic.init(); // init metronic core components
Layout.init(); // init current layout
Login.init();
Demo.init();
});
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->


</html>