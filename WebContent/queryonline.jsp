<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>欢迎使用CSM系统</title>
<link rel="icon" href="img/favicon.ico">
</head>
<body>
	<table border="1"  style="width:80%;margin:0 auto;">
		<tr>
			<td>在线用户的ssid</td>
			<td>在线用户的用户名</td>
			<td>在线用户的ip地址/td>
			<td>在线用户访问的页面</td>
			<td>在线用户进来的时间</td>
		</tr>
		<c:forEach var="onlineMap" items="${online }">
			<tr>
			<td>${onlineMap.ssid }</td>
			<td>${onlineMap.username }</td>
			<td>${onlineMap.ip }</td>
			<td>${onlineMap.page }</td>
			<td>${onlineMap.time }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>