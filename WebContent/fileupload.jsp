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
<style>
.note-set{
				font-size: 12px;
				text-decoration: none; 
				margin-left: 135px;
			}
</style>
</head>
<body>
<div class="layui-header">
		<ul class="layui-nav" lay-filter="">
		  <li class="layui-nav-item"><a href="${pageContext.request.contextPath }/index.jsp">最新活动</a></li>
		  <li class="layui-nav-item layui-this">
		    <a href="javascript:;">文件系统</a>
		    <dl class="layui-nav-child"> <!-- 二级菜单 -->
		      <dd><a href="${pageContext.request.contextPath }/filesystem.jsp">文件查看</a></dd>
		      <dd><a href="${pageContext.request.contextPath }/fileupload.jsp">文件录入</a></dd>
		    </dl>
		  </li>
		  <li class="layui-nav-item">
		    <a href="javascript:;">工作追踪</a>
		    <dl class="layui-nav-child"> <!-- 二级菜单 -->
		      <dd><a href="${pageContext.request.contextPath }/tracedisplay.jsp">工作查看</a></dd>
		      <dd><a href="${pageContext.request.contextPath }/tracerecord.jsp">新建待办</a></dd>
		    </dl>
		  </li>
		  <c:if test="${user.username == 'admin' }">
		  <li class="layui-nav-item"><a href="${pageContext.request.contextPath }/query.udo" target="_blank">查询所有用户信息</a></li>
		  <li class="layui-nav-item"><a href="${pageContext.request.contextPath }/online.udo"  target="_blank">在线人数查询</a></li>
		  <li class="layui-nav-item"><a href="${pageContext.request.contextPath }/display.up"  target="_blank">所有文件查询</a></li>
		  </c:if>
		  <li class="layui-nav-item layui-col-md-offset2">
		    <a href="">欢饮回来：${user.username }</a>
		    <dl class="layui-nav-child">
		      <dd><a href="#" onclick="edit('${user.id }');">修改个人信息</a></dd>
		      <dd><a href="${pageContext.request.contextPath }/logout.udo">退出登录</a></dd>
		    </dl>
		  </li>
		</ul>
</div>
		
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>文件上传方式</legend>
</fieldset>
        		<form class="layui-form" action="${pageContext.request.contextPath }/upload.up" method="post" enctype="multipart/form-data">
					<div class="layui-form-item">
					<label class="layui-form-label">请选择上传的文件</label>
						<input type="file" name="file" />
					</div>
					<div class="layui-form-item">
					<label class="layui-form-label">请选择文件所属类别</label>
				      <div class="layui-input-inline">
				        <select name="belong" lay-verify="required" lay-search="">
				          <option value="11">LC相关文档</option>
				          <option value="12">SC相关文档</option>
				          <option value="13">POST相关文档</option>
				          <option value="14">GATE相关文档</option>
				          <option value="15">TVM相关文档</option>
				          <option value="21">应急预案</option>
				          <option value="41">其他文件</option>
				        </select>
				     </div>
				  	</div>
				  	<div class="layui-form-item">
				     <input type="hidden" name="username" value="${user.username }" />
				    </div>
				  <div class="layui-form-item">
				    <div class="layui-input-block">
				      <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">立即上传</button>
				      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
				    </div>
				  </div>
				</form>
				
				<c:if test="${not empty noteMsg1 }"> 
					<div class="note-set" style="color:red">${noteMsg1 }</div>
				</c:if>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>文件录入方式</legend>
</fieldset>
				
<form class="layui-form" action="${pageContext.request.contextPath }/write.up" method="post">
  	<div class="layui-form-item">
    	<label class="layui-form-label">请输入文件名</label>
    	<div class="layui-input-block">
      		<input type="text" name="filename" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    	</div>
  	</div>
	<div class="layui-form-item layui-form-text">
    	<label class="layui-form-label">请输入文件内容</label>
    	<div class="layui-input-block">
      		<textarea name="filecontent" placeholder="请输入内容" class="layui-textarea"></textarea>
    	</div>
  	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">请选择文件所属类别</label>
			<div class="layui-input-inline">
				<select name="belong" lay-verify="required" lay-search="">
					<option value="31">系统与硬件</option>
					<option value="32">软件与数据</option>
				</select>
			</div>
	</div>
	<div class="layui-form-item">
		<input type="hidden" name="username" value="${user.username }" />
	</div>
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn" lay-submit="" lay-filter="demo2">立即上传</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>

<c:if test="${not empty noteMsg2 }"> 
	<div class="note-set" style="color:red">${noteMsg2 }</div>
</c:if>
		
<script>		
function edit(id) {
	var url = "${pageContext.request.contextPath }/edit.udo?id="+id;
	layer.open({
		 type:2,
		 area:['500px','400px'],
		  title: "修改用户信息",
		  content: url,
		  skin:'layui-layer-molv',
		  success: function(layero, index){
			  
			  },


		}); 
}
</script>		
</body>
</html>