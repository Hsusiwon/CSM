<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
body{
background:url(img/back.jpg)fixed center center no-repeat;
  background-size: cover;
  width: 100%;
}

.footer{
    position:absolute;
    color:#fff;
    bottom:0;
    width:100%;
    height:50px;
    line-height:50px;
    text-align:center;
/*     background-color: #000; */
}
.container{
      			width: 400px;
      			height: 250px;
		 		 min-height: 250px;  
		 		 max-height: 300px;  
		 		 position: absolute;   
		 		 top: 0;  
		 		 left: 0;  
		 		 bottom: 0;  
		 		 right: 0;  
		 		 margin: auto;  
		 		 padding: 20px;  
		 		 z-index: 130;  
		 		 border-radius: 8px;  
		 		 background-color:  #fff;  
		 		 box-shadow: 0 3px 18px rgba(100, 0, 0, .5); 
		 		 font-size: 16px;
      		}
.font-set{
				font-size: 12px;
				text-decoration: none; 
				margin-left: 160px;
			}
.note-set{
				font-size: 12px;
				text-decoration: none; 
				margin-left: 135px;
			}
</style>
</head>
<body>
<!-- <div class="layui-container layui-row"> -->
<div class="container">
	<form class="layui-form" action="${pageContext.request.contextPath }/login.udo" method="post">
		<div class="layui-form-item">
    		<label class="layui-form-label">用户名</label>
    			<div class="layui-input-inline">
      				<input type="text" name="username" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
    			</div>
  		</div>
  		<div class="layui-form-item">
    		<label class="layui-form-label">密码</label>
    			<div class="layui-input-inline">
      				<input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
    			</div>
  		</div>
  		<div class="layui-form-item">
    		<label class="layui-form-label">记住我</label>
    		<div class="layui-input-block">
      		<input type="radio" name="expiredays" value="7" title="一周">
      		<input type="radio" name="expiredays" value="30" title="一个月">
      		<input type="radio" name="expiredays" value="100" title="永久">
    		</div>
  		</div>
		<div class="layui-form-item">
    		<div class="layui-input-block">
      		<button class="layui-btn" lay-submit lay-filter="formDemo">登录</button>
     		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
   			</div>
  		</div>
  		
	</form>
<%-- 	<a href="${pageContext.request.contextPath }/add.jsp" class="font-set">立即注册</a> --%>
<!-- 	<a  class="font-set" onclick="reg();">立即注册</a> -->
  	<c:if test="${not empty requestScope.note }"> 
		<div class="note-set" style="color:red">${note }</div>
	</c:if>	
<!-- </div> -->
</div>

<div class="footer" align="center">
	<p>
		
		 	<a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=32010402001194" rel="nofollow" style="color:#939393;">
		 	<img src="img/batb.png" />
		 		苏公网安备 32010402001194号
		 	</a>
		 	<a href="https://beian.miit.gov.cn" target="_blank" rel="nofollow" style="color:#939393;margin-left:10px;">
		 	苏ICP备2021036268号
		 	</a>
		 
	</p>
</div>

<script>
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function(){
  var $ = layui.jquery
  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
  

});

function reg() {
	layer.open({
		 type:2,
		 area:['500px','400px'],
		  title: "新增用户",
		  content: "add.jsp",
		  skin:'layui-layer-molv',
		  success: function(layero, index){
			  
			  },


		}); 
}
</script>

</body>
</html>