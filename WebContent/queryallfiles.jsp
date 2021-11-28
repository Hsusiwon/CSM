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
</head>
<body>
已经上传的文件：
<table border="1" >
	<tr>
		<td>id</td>
		<td>上传的描述</td>
		<td>文件类型</td>
		<td>文件大小</td>
		<td>上传文件的路径</td>
		<td>上传文件的日期</td>
		<td>上传文件的新名字</td>	
		<td>上传者</td>
		<td>所属分类</td>
		<td>删除文件/下载文件</td>
	</tr>
	<c:forEach var="uf" items="${uploadfiles }">
	<tr>
		<td>${uf.id }</td>
		<td>${uf.fileName }</td>
		<td>${uf.fileType }</td>
		<td>${uf.fileSize }</td>
		<td>${uf.savePath }</td>
		<td>${uf.saveTime }</td>
		<td>${uf.saveName }</td>
		<td>${uf.writer }</td>
		<td>${uf.belong }</td>
		<td><a href="${pageContext.request.contextPath }/delete.up?id=${uf.id}">删除文件</a>|<a href="${pageContext.request.contextPath }/download.up?id=${uf.id}">下载文件</a></td>
	</tr>
	</c:forEach>
</table>

</body>
</html>