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
<div class="layui-header">
		<ul class="layui-nav" lay-filter="">
		  <li class="layui-nav-item"><a href="${pageContext.request.contextPath }/index.jsp">最新活动</a></li>
		  <li class="layui-nav-item">
		    <a href="javascript:;">文件系统</a>
		    <dl class="layui-nav-child"> <!-- 二级菜单 -->
		      <dd><a href="${pageContext.request.contextPath }/filesystem.jsp">文件查看</a></dd>
		      <dd><a href="${pageContext.request.contextPath }/fileupload.jsp">文件录入</a></dd>
		    </dl>
		  </li>
		  <li class="layui-nav-item layui-this">
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

<div class="layui-container layui-row" style="margin-top: 10px;">

<form class="layui-form" id="traceadd">
	<div class="layui-form-item">
    	<label class="layui-form-label">请输入标题</label>
    	<div class="layui-input-block">
      		<input type="text" name="title" required  lay-verify="required" placeholder="请输入标题" autocomplete="off" class="layui-input">
    	</div>
  	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">工作类别</label>
			<div class="layui-input-inline">
				<select name="type" lay-verify="required" lay-search="">
					<option value="LC">LC</option>
					<option value="SC">SC</option>
					<option value="POST">POST</option>
					<option value="GATE">GATE</option>
					<option value="TVM">TVM</option>
					<option value="网络">网络</option>
				</select>
			</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">类型</label>
			<div class="layui-input-inline">
				<select name="level" lay-verify="required" lay-search="">
					<option value="1">故障</option>
					<option value="2">警告</option>
					<option value="3">提示</option>
					<option value="4">施工</option>
					<option value="5">升级</option>
				</select>
			</div>
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">状态</label>
	    <div class="layui-input-block">
	      <input type="radio" name="repaired" value="1" title="未处理" checked>
	      <input type="radio" name="repaired" value="2" title="执行中">
	      <input type="radio" name="repaired" value="3" title="已关闭">
	    </div>
  	</div>
  	<div class="layui-form-item">
		<div class="layui-inline">
	      <label class="layui-form-label">记录时间</label>
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" id="test5" name="recordtime" placeholder="yyyy-MM-dd HH:mm:ss" readonly="readonly">
	      </div>
    	</div>
	</div>
	<div class="layui-form-item">
		<input type="hidden" name="recorder" value="${user.username }" />
	</div>
	<div class="layui-form-item layui-form-text">
    	<label class="layui-form-label">现象描述</label>
    	<div class="layui-input-block">
      		<textarea name="recordcont" placeholder="请输入现象描述与处理方式结果" class="layui-textarea"></textarea>
    	</div>
  	</div>
  	<div class="layui-upload">
	  <button type="button" class="layui-btn" id="test2">选择图片</button> 
	  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
	    预览图：
	    <div class="layui-upload-list" id="demo2"></div>
	 </blockquote>
	</div>
	
	
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn" lay-submit="" lay-filter="sub">立即上传</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>

</div>
	
<script>
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['upload', 'element', 'layer','laydate'], function(){
  var $ = layui.jquery
  ,form = layui.form
  ,upload = layui.upload
  ,element = layui.element
  ,layer = layui.layer
  ,laydate = layui.laydate;
  var curDate = new Date();
  
//日期时间选择器
  laydate.render({
    elem: '#test5'
    ,type: 'datetime'
   	,value: curDate
  });
  
//多图片上传
  upload.render({
	elem: '#test2'
	, auto: false
	, multiple: true
	, accept: 'image' //普通文件
	, exts: 'jpg|jpeg|png|bmp|png' //只允许上传图片
	, size: 2 * 1024 //限制文件大小，单位 KB
	, field:'image' //设定文件域的字段名，默认为file,此处即为关键部分
	,before: function(obj){
     //预读本地文件示例，不支持ie8
     
   	}
	, choose: function (obj) {
		//预读本地文件示例，不支持ie8
		obj.preview(function(index, file, result){
		       $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 50px;height: 50px;" onclick="previewImg(this);">')
		     });
		   	
	}
	, done: function (res) {
		//如果上传失败
		if (res.code > 0) {
			return layer.msg('上传失败');
		}
		//上传成功
	}
	, error: function () {
		//演示失败状态，并实现重传
		
	}
});

  form.on('submit(sub)', function (data) {
	 
		var myForm = new FormData(document.getElementById("traceadd"));
	 
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath }/add.pdo', //请求路径
			data: myForm,
			dataType: "json",
			contentType: false,//必填 必须false 才会加上正确的Content-Type
			processData: false, // 必填 必须false 才会避开jq对formdata的默认处理 XMLHttpRequest才会对formdata进行正确处理.当设置为true的时候,jquery ajax提交的时候不会序列化data，而是直接使用data
			success: function (data) {
				if (data.code == 0) {
					layer.msg(data.message);	
				} else {
					layer.msg(data.message);	
				}
	 
			}
		});
		return false;
	});  

  

});

function previewImg(obj) {
    var img = new Image();  
    img.src = obj.src;
    
    var scaleWH = img.width / img.height;
    var bigH = 500;
    if (img.height < 500) {
        bigH = img.height;
    }
    var bigW = scaleWH * bigH;
    if (bigW > 900) {
        bigW = 900;
        bigH = bigW / scaleWH;
    } // 放大预览图片
    var imgHtml = "<img width='" + bigW + "' height='" + bigH + "' src='" + obj.src + "' />";

	var laybigW= bigW+0;
	var laybigH= bigH+51;
    //弹出层
    layer.open({  
        type: 1,  
        shade: 0.8,
        offset: 'auto',
		area: [laybigW + 'px', laybigH + 'px'],
        shadeClose:true,//点击外围关闭弹窗
        scrollbar: false,//不现实滚动条
        title: "图片预览", //不显示标题  
        content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响  
        cancel: function () {  
            //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });  
        }  
    }); 
}

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