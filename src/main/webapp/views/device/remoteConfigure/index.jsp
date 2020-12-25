<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />


</head>
<style>
#deviceDiv a{
  width: 350px;
  height: 100px;
  position:absolute;
  left:0;
  top: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  font-size: 28px;
  color: #0a7ede;
  text-decoration: underline;
}
</style>
<body>
<div style="height: 100%;width: 100%" id="deviceDiv">
<a href="http://deve.docomo-iot.com.cn:20001/" title="设备管理平台" target="_blank">跳转到设备远程管理平台</a>
</div>

</body>
<script>
closeLoading();
</script>
</html>