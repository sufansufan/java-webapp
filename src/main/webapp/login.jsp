<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html><head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, minimum-scale=1.0, user-scalable=no, target-densitydpi=device-dpi" data-reactid="7">
<title>智慧工厂IoT远程监控系统</title>

<meta name="renderer" content="webkit|ie-comp|ie-stand">
<link rel="shortcut icon" href="${ctx}/css/images/favicon.ico" type="image/x-icon">

<link rel="stylesheet" href="${ctx}/js/plugins/layui/css/layui.css?v=2.5.4">
<link rel="stylesheet" href="${ctx}/css/login.css">
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.8.3.min.js"></script>
<script src="${ctx}/js/plugins/layui/layui.js?v=2.5.4"></script>
<link id="layuicss-layer" rel="stylesheet" href="http://www.xm-kld.com:9000/sl_platform/js/plugins/layui/css/modules/layer/default/layer.css?v=3.1.1" media="all"></head>
<body>

	<div class="zhlq_bg_cs"></div>
	<div class="safe" style="height: 400px">
		<div class="zhlq_title" style="color: rgb(192,0,0);font-family: 微软雅黑">智慧工厂IoT远程监控系统</div>
		<div class="zhlq_login_box_z">
			<div class="zhlq_login_box ">
				<div class="zhlq_login_box_a">
					<div class="msg_error">
						<label id="pwderror" style=""></label>
					</div>
				
				</div>
			</div>
		</div>
		<div class="lqzh_box">
			<div class="">
				<span class="xtdl_text fl" style="color: rgb(192,0,0)">系统登录 <span class="xtdl_text_lg">LOGIN</span></span>
			   <div class="user_icon" >
                    <img src="${ctx}/css/images/langqi/user_icon1.jpg" alt="">
                </div>
                <div class="user_icon user_icon_b" >
                    <img src="${ctx}/css/images/langqi/mima_icon1.jpg" alt="">
                </div>
				<div class="layui-form">
					<input type="text" id="username" class="user_name user_name_a" placeholder="用户名">
					<input type="password" id="password" class="user_name user_name_b" placeholder="密码">
					
					<div class="zhlq_mima remember layui-form-item">
						<input id="rememberMe" name="rememberMe" type="checkbox" title="记住密码" lay-skin="primary"><div class="layui-unselect layui-form-checkbox" lay-skin="primary"><span>记住密码</span><i class="layui-icon layui-icon-ok"></i></div>
						<div id="valid-content">
							<input id="validCode" name="validCode" class="validCode" placeholder="请输入验证码" type="text">
							<img id="codevalidate" src="validCode" width="90" height="38" style="padding-bottom: 0px; float: right" title="看不清？点击换一张">
						</div>
						
						<div style="float:right; line-height: 38px;display:none;">
							<a href="" class="right">忘记密码?</a>
						</div>
						
					</div>
					
					<button class="layui-btn zhlq_bt" id="login_button" onclick="login()" style="background-color: rgb(192,0,0)">登录</button>
					
				</div>
			</div>
		</div>
	</div>

<!-- <div class="footer" style="padding: 0;position: fixed;">Copyright 都客梦（上海）通信技术有限公司</div> -->
<div class="footer" style="padding: 0;position: fixed;background-color: rgb(192,0,0)"> 武汉高木汽车部件有限公司 </div>

<script type="text/javascript">

	var form;
	layui.use([ 'form', 'layer' ], function() {
		form = layui.form;
		form.render();
	});
	
	$(function() {

		$("#codevalidate").click(function() {
			$(this).attr("src", "validCode?" + Math.random());
		})

		//记住密码功能
		var str = getCookie("loginInfo");
		if (str != "") {
			//str = str.substring(1,str.length);
			var username = str.split(":")[0];
			var password = str.split(":")[1];
			var flagRemember = str.split(":")[2];
			//自动填充用户编号和密码
			$("#username").val(username);
			$("#password").val(password);

			if (flagRemember == true || flagRemember == "true") {
				$("#rememberMe").attr("checked", true);
			} else {
			}
		} else {
			$("#rememberMe").removeAttr("checked");
		}

		var ec = getCookie("ec");
		if (ec != "" && Number(ec) >= 3) {
			$("#valid-content").show();
		}

		//回车登录
		$("#username").keyup(function(event) {
			if (event.keyCode == 13) {
				login();
			}
		});
		$("#password").keyup(function(event) {
			if (event.keyCode == 13) {
				login();
			}
		});
		$("#validCode").keyup(function(event) {
			if (event.keyCode == 13) {
				login();
			}
		});

	});

	function isIE() { //ie?
		if (!!window.ActiveXObject || "ActiveXObject" in window)
			return true;
		else
			return false;
	}
	function login() {
// 		清空所有缓存
		sessionStorage.clear()
		$("#login_button").removeAttr("onclick");
		$("#login_button").html("正在登录...");
		
		$("#pwderror").hide();
		
		var username = $("#username").val();
		var password = $("#password").val();
		if (username == "" || password == "") {
			$("#pwderror").html("用户编号和密码不能为空！");
			$("#pwderror").show();
			$("#login_button").attr("onclick", "login()");
			$("#login_button").html("登录");
			return;
		}

		var captcha = $("#validCode").val();
		if (!$("#valid-content").is(":hidden") && captcha == "") {
			$("#pwderror").html("请输入验证码！");
			document.getElementById("pwderror").style.display = "inline";
			$("#login_button").attr("onclick", "login()");
			$("#login_button").html("登&nbsp;&nbsp;录");
			return;
		}
		
		$.ajax({
			url : 'login.do',
			type : 'POST',
			data : {
				userName : username,
				passWord : password,
				rememberMe : $("#rememberMe").attr("checked") == "checked" ? 1 : 0,
				isValid : !$("#valid-content").is(":hidden"),
				captcha : captcha
			},
			dataType : 'JSON',
			cache : false,
			success : function(data) {
				if (!data.success) {
					$("#pwderror").html(data.msg);
					document.getElementById("pwderror").style.display = "inline";
					$("#login_button").attr("onclick", "login()");
					$("#login_button").html("登&nbsp;&nbsp;录");
					if (data.msg.indexOf("验证码输入错误") == -1) {
						$("#codevalidate").click();
					}
					var ec = getCookie("ec");
					if (ec != "" && Number(ec) >= 3) {
						$("#valid-content").show();
					}
					return;
				} else {
					reload();
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert('登录失败，请检查网络情况', {
					shadeClose : true,
					title : "提示"
				});
				$("#login_button").attr("onclick", "login()");
				$("#login_button").html("登录");
			}
		});
	}

	function reload() {
		window.location.href = '${ctx}';
	}
	//获取cookie
	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			if (c.indexOf(name) != -1)
				return c.substring(name.length, c.length);
		}
		return "";
	}
	
	
</script>
<script src="${ctx}/js/download.min.js" type="text/javascript"></script>

</body></html>

