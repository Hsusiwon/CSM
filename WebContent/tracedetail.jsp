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
<style type="text/css">
.layui-table-cell {
	height:auto;
    overflow: visible;
 	text-overflow:inherit;
    white-space:normal;
}
.layui-table img {
                height: 25px;
            width: 25px;
}
textarea {
resize: none;
}
</style>
</head>
<body>
<input type="hidden"  name="serial" id="serial" />
<table class="layui-hide" id="proque" lay-filter="test"></table>

<script type="text/html" id="barDemo">
<c:if test="${user.username == 'admin' }">
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</c:if>
</script>

<script type="text/html" id="viewimg">
{{#  if (d.updatepic!=null&&d.updatepic!=""){
var  path=d.updatepic.split("trace_").filter(item => item != '');
var path=path[0];
var  con=d.updatepic.split("/").filter(item => item != '');
var str=con[con.length-1];
var str=str.split("add").filter(item => item != '');
for(var j in str) {  
	var  name1=str[j].split(".").filter(item => item != '');
	var  name2=name1[0].replace(/-/g,'');
	var name= name2.replace(/_/g,''); }}
	<img id="{{name}}" src="img/ctd.jpg" style="width:25px;height:25px;" onclick="loadImg('{{path}}{{str[j]}}');" />
<!-- <img src="{{path}}{{str[j]}}" style="width:25px;height:25px;" onclick="previewImg(this);" /> -->
{{# }
} }}
</script>

<script type="text/html" id="viewtxt">
	<textarea id="{{d.serialnum}}" disabled="true" style="width:380px;height:100px;" />
{{# layui.jquery.ajax({
    			type: 'post',
				data: {
        			path : d.updatecont
    	     		},
    			url: '${pageContext.request.contextPath }/gettxt.pdo', 
    			success: function (data) {
					document.getElementById(d.serialnum).value=data
    			}
    		}); }}
	
</script>

<script type="text/html" id="repairedTpl">
  {{#  if(d.repaired === '1'){ }}
    <span style="color: #00BB00;">未处理</span>
  {{#  } else if(d.repaired === '2') { }}
    <span style="color: #0072E3;">执行中</span>
  {{#  } else { }}
    <span>已关闭</span>
  {{#  } }}
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
  var serialinput = ${param.serial};

//执行一个 table 实例
  table.render({
	elem: '#proque'
    ,height: 'full-110'
    ,url: 'querydetail.pdo' //数据接口
    ,method:'post'
    ,title: '工作查看'
    ,where: {serial: serialinput}
    ,page: false //开启分页
    ,unresize: true
    ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
    ,defaultToolbar: ['filter', 'print', 'exports']
    ,parseData:function(res) {
    	return{
    		"code":0
    		,"msg":""
    		,"count":res.count
    		,"data":res.data
    		
    	}
    }
    ,cols: [[ 
      {field: 'xuhao', title: '序号', width:30, fixed: 'left',type: 'numbers'}
      ,{field: 'repaired', title: '状态', width:78, sort: true,templet: '#repairedTpl'}
      ,{field: 'updatetime', title: '更新时间', width:164, sort: true,templet: function(d){return dateToStr(d.updatetime);}} 
      ,{field: 'updater', title: '更新者', width: 92, sort: true}
      ,{field: 'updatecont', title: '内容描述', width: 400, sort: true,templet:"#viewtxt"}
      ,{field: 'updatepic', title: '相关图片', width: 100, sort: true,templet:"#viewimg"}
      ,{fixed: 'right', width: 60, align:'center', toolbar: '#barDemo'}
    ]]
    ,done: function (res, curr, count) {
    	//初始化高度，使得冻结行表头高度一致
          $(".layui-table-header  tr").each(function (index, val) {
            $($(".layui-table-fixed .layui-table-header table tr")[index]).height($(val).height());
          });
    	  // 初始化高度，使得冻结行表体高度一致
          $(".layui-table-main tr").each(function (index ,val) {
              $($(".layui-table-fixed .layui-table-body tbody tr")[index]).height($(val).height());
          });
      }
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    
    if(layEvent === 'delete'){
    	layer.confirm('真的删除这项吗？', function(){
    		$.ajax({
    			type: 'POST',
    			url: '${pageContext.request.contextPath }/deletedetail.pdo', //请求路径
    			data: {
        			serial : data.serial,
        			serialnum : data.serialnum
    	     		},
    			dataType: "json",
    			success: function (data) {
    				if (data.code == 0) {	
    					layer.msg(data.message);	
    				} else {
    					layer.msg(data.message);	
    				}
    	 
    			}
    		});
          });
    }
  });
  
  function dateToStr(date) {
	  if(date){
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
	  	}else{
	  		return "";
	  	}
	  }
  
});

function loadImg(path) {
	var $=layui.jquery;

//ajax方法还停留在几年前的xmlhttprequest1不支持流文件,xmlhttprequest 2 标准中支持流文件的，并且应该使用 xhr.response作为返回 而不是responseText
// 	var url = "${pageContext.request.contextPath }/getimg.pdo?path="+path;
//     var xhr = new XMLHttpRequest();
//     xhr.open('POST', url, true);
//     xhr.responseType = "blob";
//     xhr.setRequestHeader("client_type", "DESKTOP_WEB");
//     xhr.setRequestHeader("desktop_web_access_key", "_desktop_web_access_key");
//     xhr.onload = function() {
//         if (this.status == 200) {
//             var blob = this.response;
//             var dataURL = window.URL.createObjectURL(blob);
            
			var  con=path.split("/").filter(item => item != '');
			var name3=con[con.length-1];
			var  name1=name3.split(".").filter(item => item != '');
			var  name2=name1[0].replace(/-/g,'');
			var name= name2.replace(/_/g,'');
			
			$("#"+name).attr('src',path); 
			$("#"+name).attr('onclick',"previewImg(this)");
//         }
//     }
//     xhr.send();

};

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
</script>

</body>
</html>