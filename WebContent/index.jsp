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
    .demo-carousel{height: 500px; line-height: 500px; text-align: center;}
  </style>
</head>
<body>
<div class="layui-header">
		<ul class="layui-nav" lay-filter="">
		  <li class="layui-nav-item layui-this"><a href="${pageContext.request.contextPath }/index.jsp">最新活动</a></li>
		  <li class="layui-nav-item">
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

<div class="layui-carousel" id="carousel">
  <div carousel-item>
    <div><p class="layui-bg-green demo-carousel">在这里，你将以从菜单选择相应的功能</p></div>
    <div><p class="layui-bg-cyan demo-carousel">你也可以点击左侧导航选择查看指导书相关文件</p></div>
  </div>
</div>

	
<script>
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(function(){
  var $ = layui.jquery
  ,element = layui.element //Tab的切换功能，切换事件监听等，需要依赖element模块
  ,carousel = layui.carousel;
  //建造实例
 carousel.render({
    elem: '#carousel'
    ,width: '100%' //设置容器宽度
    ,height: '500px'
    ,arrow: 'none' //始终显示箭头
    ,anim: 'fade' //切换动画方式
    ,interval: 3000
  });

});

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