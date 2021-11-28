<%-- <%@page import="cn.sion.csm.model.User"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="text/html; charset=UTF-8">
<title>欢迎使用CSM系统</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui-v2.6.7/css/layui.css" />
<script src="https://www.layuicdn.com/layui-v2.6.7/layui.js" charset="UTF-8"></script>
<style>
.font-set{
				font-size: 12px;
				text-decoration: none; 
				margin-left: 160px;
				color:red;
				aligh:center;
			}
</style>
</head>
<body>

<form action="${pageContext.request.contextPath }/editsub.udo" class="layui-form" id="editform" style="padding:30px;">
	<div class="layui-form-item">
		<input type="hidden" name="id" value="${user.id }" />
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">用户名：</label>
		<div class="layui-input-block">
		<input type="text" name="username"  class="layui-input" value="${user.username }" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">密码：</label>
		<div class="layui-input-block">
		<input type="text" name="password"  class="layui-input" value="${user.password }"/>
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">电话号码：</label>
		<div class="layui-input-block">
		<input type="text" name="phone"  class="layui-input" value="${user.phone }"/>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn" lay-submit="" lay-filter="editsub">修改</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
	<a  class="font-set">注意：[`~!@#$%^&*()|;:',.;/?{}]", "这些均为无效字符</a>
</form>

<script>
layui.use('form', function(){
  var $ = layui.jquery
  ,form = layui.form
  ,layer=layui.layer;
  
  form.on('submit(editsub)', function (data) {

		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath }/editsub.udo', //请求路径
			data: $('#editform').serialize(),
			dataType: "json",
			success: function (data) {
					layer.msg(data.message);	
			}
		});
		return false;
	});
  
});
</script>

</body>
</html>