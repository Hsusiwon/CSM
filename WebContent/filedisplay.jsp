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


<table class="layui-hide" id="demo" lay-filter="test"></table>




 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail">预览</a>
  <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="download">下载</a>
</script>

<script>
//加载模块  
layui.use(function(){ //亦可加载特定模块：layui.use(['layer', 'laydate', function(){
  //得到各种内置组件
  var $=layui.jquery
  ,layer = layui.layer //弹层
  ,laypage = layui.laypage //分页
  ,laydate = layui.laydate //日期
  ,table = layui.table //表格
  ,carousel = layui.carousel //轮播
  ,upload = layui.upload //上传
  ,element = layui.element //元素操作
  ,slider = layui.slider //滑块
  ,dropdown = layui.dropdown //下拉菜单
   var frameId = self.frameElement.getAttribute('data-frameid')//获取当前tab的id并传入servlet

//执行一个 table 实例
  table.render({
    elem: '#demo'
    ,height: 550
    ,url: 'displaysection.up' //数据接口
    ,method:'post'
    ,title: 'LC相关文档'
    ,where: {belong: frameId}
    ,page: true //开启分页
    ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
//     ,totalRow: true //开启合计行
//     ,parseData:function(res) {
// //     	console.log(res);
//     	return{
//     		"code":0
//     		,"msg":""
//     		,"count":res.length
//     		,"data":res
    		
//     	}
//     }
    ,parseData:function(res) {
//     	console.log(res);
    	return{
    		"code":0
    		,"msg":""
    		,"count":res.count
    		,"data":res.data
    		
    	}
    }
    ,cols: [[ //表头
//       {type: 'checkbox', fixed: 'left', totalRowText: '合计：'}
      {field: 'zizeng', title: '序号', width:60, fixed: 'left',type: 'numbers'}
//       ,{field: 'id', title: 'ID', width:60, sort: true, fixed: 'left', totalRow: true}
      ,{field: 'fileName', title: '文件名', width:205, sort: true}
//       ,{field: 'fileType', title: '文件类型', width: 90}
      ,{field: 'fileSize', title: '文件大小', width:120, sort: true, totalRow: true}
//       ,{field: 'savePath', title: '上传文件的路径', width: 80}
      ,{field: 'saveTime', title: '上传文件的日期', width:200, sort: true,templet: function(d){return dateToStr(d.saveTime);}} 
//       ,{field: 'saveName', title: '上传文件的新名字', width: 200}
      ,{field: 'writer', title: '上传者', width: 100, sort: true}
//       ,{field: 'belong', title: '所属分类', width: 135}
      ,{fixed: 'right', width: 150, align:'center', toolbar: '#barDemo'}
    ]]
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){
//     	var url = "${pageContext.request.contextPath }/view.up";
		var url="http://ow365.cn/";
        var id = data.id;
        var form = $("<form></form>").attr("action", url).attr("method", "get").attr("target", "_blank");
//         form.append($("<input></input>").attr("type", "hidden").attr("name", "id").attr("value", id));
		form.append($("<input></input>").attr("type", "hidden").attr("name", "i").attr("value", 25337));
		form.append($("<input></input>").attr("type", "hidden").attr("name", "furl").attr("value", "http://110.40.133.35"+data.savePath+"/"+data.saveName));
        form.appendTo('body').submit().remove();
    } else if(layEvent === 'download'){
//     	$.ajax({
//     		//请求类型
//     		type : 'POST',
//     		//请求的地址
//     		url : '${pageContext.request.contextPath }/download.up',
//     		//请求的数据
//     		data : {
//     			id : data.id
//     		},
// //     		dataType:"json",
// //     		headers : {'Content-Type' : 'application/json;charset=utf-8'},    		
//     		//dataType : "json",//返回的数据格式预处理
//     	    beforeSend:function(){
//     	        //请求前的处理
// //     	    	layer.msg('请求前的处理');
//     	    },
//     		//成功的回调函数，第一个形参代表返回的数据
//     		success: function (data, status, xhr) {
//                 console.log("Download file DONE!");
//                 console.log(data); // ajax方式请求的数据只能存放在javascipt内存空间，可以通过javascript访问，但是无法保存到硬盘
//                 console.log(status);
//                 console.log(xhr);
//                 console.log("=====================");
//             },
// 			complete:function(){
//     	        //请求完成的处理
// //     	    	layer.msg('请求完成的处理');
//     	    },
//     		error : function(err){}
//     	});
        //ajax方式下载文件时无法触发浏览器打开保存文件对话框，也就无法将下载的文件保存到硬盘上！ajax方式请求的数据只能存放在javascipt内存空间，可以通过javascript访问，但是无法保存到硬盘，因为javascript不能直接和硬盘交互，否则将是一个安全问题。
        //想实现post方式提交参数下载文件可以通过模拟表单提交的方式实现post传递数据。
        var url = "${pageContext.request.contextPath }/download.up";
        var id = data.id;
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "id").attr("value", id));
        form.appendTo('body').submit().remove();
    }
  });
  
  function dateToStr(date) {
	  var time = new Date(date.time);
	  var y = time.getFullYear();
	  var M = time.getMonth() + 1;
	  M = M < 10 ? ("0" + M) : M;
	  var d = time.getDate();
	  d = d < 10 ? ("0" + d) : d;
	  var h = time.getHours();
	  h = h < 10 ? ("0" + h) : h;
	  var m = time.getMinutes();
	  m = m < 10 ? ("0" + m) : m;
	  var s = time.getSeconds();
	  s = s < 10 ? ("0" + s) : s;
	  var str = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;

	  return str;
	  }
  
});
</script>
</body>
</html>