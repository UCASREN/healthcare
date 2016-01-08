<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>
</head>
<link rel="stylesheet" href="resources/css/base.css"  type="text/css"/>
<body>
<!--导航开始-->
<div id="nav_wraps"></div>
<!--导航结束-->
<!--content开始-->
              <div class="login_content">
                           <div class="header">
                                    <span><img src="img/logo2.png" width="48" height="48" /></span>
                                   <b>中国心脑血管病大数据共享平台</b>
                           </div>
                           <div class="zhdl"> 账号登陆</div>
                           <form action="${loginUrl}" method="post">
	                           <div class="login-md">
	                                <dl>
	                                        <dd>
	                                                <input type="text" class="txt"  id="username" name="username" value="输入用户名/手机号"/>
	                                        </dd>
	                                        <dd>
	                                                <input type="password" class="pasd"  id="password" name="password" value="输入密码"/>
	                                        </dd>
	                                        <dd class="zddl">
	                                                <input type="checkbox" class="jzmm"  id="remember-me" name="remember-me" value="1"/>下次自动登陆
	                                        		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	                                        </dd>
	                                        <dd>
	                                                <input type="submit" class="sub" value="立即登录"/>
	                                        </dd>
	                              </dl>
		                            <p class="mmzc"><a href="#">忘记密码?</a> ∣ <a href="#">马上注册</a></p>
		                            <p class="sq">Copyright © 2012-2015 中国心脑血管病网 All Rights Reserved 版权所有</p>
	                			</div>
                		</form>
		</div>
<!--content结束-->
</body>
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script>
$("#username").click(function(){
	if($("#username").val()=='输入用户名/手机号'){
		$("#username").val("");
	}
});
$("#password").click(function(){
	if($("#password").val()=='输入密码'){
		$("#password").val("");
	}
});
</script>
</html>