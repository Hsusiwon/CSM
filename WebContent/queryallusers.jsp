<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>欢迎使用CSM系统</title>
<link rel="icon" href="img/favicon.ico">
<link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui-v2.6.7/css/layui.css" />
<script src="https://www.layuicdn.com/layui-v2.6.7/layui.js" charset="UTF-8"></script>
</head>
<body>
<div>
<table style="margin-left:100px;padding:50px;border:1px #ccc solid">
		<tr>
			<td>用户编号</td>
			<td>用户名</td>
			<td>用户密码</td>
			<td>电话号码</td>
			<td>用户等级</td>
			<td>注册日期</td>
			<td>操作</td>
		</tr>
		
		<c:if test="${not empty userList }">
		<c:forEach items="${userList }" var="user">
		<tr>
			<td>${user.id }</td>
			<td>${user.username }</td>
			<td>${user.password }</td>
			<td>${user.phone }</td>
			<td>${user.userlevel }</td>
			<td>${user.regdate }</td>
			<td><a href="${pageContext.request.contextPath }/edit.udo?id=${user.id }">修改</a>|<a href="${pageContext.request.contextPath }/delete.udo?id=${user.id }">删除</a></td>
		</tr>
		</c:forEach>
		</c:if>
</table>
</div>
</body>
</html>