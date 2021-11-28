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

<div>
<table class="layui-hide" id="proque" lay-filter="test"></table>
</div>

<script type="text/html" id="barDemo">
<a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="detail">详细</a>
  {{#  if(d.repaired === '3'){ }}
    <a class="layui-btn layui-btn-xs layui-btn-disabled">新建</a>
  {{#  } else { }}
    <a class="layui-btn layui-btn-xs" lay-event="update">新建</a>
  {{#  } }}

<c:if test="${user.username=='admin'}">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</c:if>
</script>

<script type="text/html" id="viewimg">
{{# if (d.recordpic!=null&&d.recordpic!=""){
var  path=d.recordpic.split("trace_").filter(item => item != '');
var path=path[0];
var  con=d.recordpic.split("/").filter(item => item != '');
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
	<textarea id="{{d.serial}}" disabled="true" style="width:380px;height:100px;" />
{{# layui.jquery.ajax({
    			type: 'post',
				data: {
        			path : d.recordcont
    	     		},
    			url: '${pageContext.request.contextPath }/gettxt.pdo', 
    			success: function (data) {
					document.getElementById(d.serial).value=data



    			}
    		}); }}
	
</script>

<script type="text/html" id="Search">
<div class="demoTable" >
	<div class="layui-inline">
      <label>日期范围:</label>
      <div class="layui-inline">
        <div class="layui-input-inline">
          <input type="text" class="layui-input" id="stime" readonly="readonly" placeholder="开始时间" autocomplete="off">
        </div>
        <label>-</label>
        <div class="layui-input-inline">
          <input type="text" class="layui-input" id="etime" readonly="readonly" placeholder="结束时间" autocomplete="off">
        </div>
      </div>
    </div>

<label>类别:</label>
			<div class="layui-input-inline">
				<select id="level">
					<option value="">请选择...</option>
					<option value="1">故障</option>
					<option value="2">警告</option>
					<option value="3">提示</option>
					<option value="4">施工</option>
					<option value="5">升级</option>
				</select>
			</div>

<label>状态:</label>
			<div class="layui-input-inline">
				<select id="repaired">
					<option value="">请选择...</option>
					<option value="1">未处理</option>
					<option value="2">执行中</option>
					<option value="3">已关闭</option>
				</select>
			</div>

       <label>标题:</label>
        <div class="layui-inline">
            <input class="layui-input" id="stitle"placeholder="请输入标题" autocomplete="off">
        </div>
        <button id="sbutton" class="layui-btn" data-type="reload" >搜索</button>
</div>
</script>

<script type="text/html" id="levelTpl">
  {{#  if(d.level === '1'){ }}
    <span>故障</span>
  {{#  } else if(d.level === '2') { }}
    <span>警告</span>
  {{#  } else if(d.level === '3') { }}
    <span>提示</span>
  {{#  } else if(d.level === '4') { }}
    <span>施工</span>
  {{#  } else { }}
    <span>升级</span>
  {{#  } }}
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
//    var frameId = self.frameElement.getAttribute('data-frameid')//获取当前tab的id并传入servlet

//执行一个 table 实例
  table.render({
    elem: '#proque'
    ,height: 'full-110'
    ,url: 'query.pdo' //数据接口
    ,method:'post'
    ,title: '工作查看'
//     ,where: {belong: frameId}
    ,page: true //开启分页
    ,unresize: true
    ,toolbar: '#Search' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
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
      ,{field: 'title', title: '标题', width:150, sort: true}
      ,{field: 'type', title: '组别', width:78, sort: true}
      ,{field: 'level', title: '类型', width:78, sort: true,templet: '#levelTpl'}
      ,{field: 'repaired', title: '状态', width:78, sort: true,templet: '#repairedTpl'}
      ,{field: 'recordtime', title: '记录时间', width:164, sort: true,templet: function(d){return dateToStr(d.recordtime);}} 
      ,{field: 'recorder', title: '记录者', width: 92, sort: true}
      ,{field: 'recordcont', title: '内容描述', width: 400, sort: true,templet:"#viewtxt"}
      ,{field: 'recordpic', title: '相关图片', width: 100, sort: true,templet:"#viewimg"}
      ,{fixed: 'right', width: 166, align:'center', toolbar: '#barDemo'}
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
  
//日期时间选择器
  laydate.render({
    elem: '#stime'
    ,type: 'datetime'
  });
  
  laydate.render({
	    elem: '#etime'
	    ,type: 'datetime'
	  });
  
  var active = {
	      reload: function(){
	          var demoReload = $('#stitle');
	          var demostime = $('#stime');
	          var demoetime = $('#etime');
	          var demorepaired = $('#repaired');
	          var demolevel = $('#level');
	          //执行重载
	          table.reload('proque', {
	              url:"query.pdo"
	            ,method:'post'
	              ,page: {
	                  curr: 1 //重新从第 1 页开始
	              }
	              ,where: {
	                      stitle: demoReload.val(),
	                      stime: demostime.val(),
	                      etime: demoetime.val(),
	                      repaired: demorepaired.val(),//发送搜索条件的值
	                      level: demolevel.val()
	              }
	          });
	          
	          $("#repaired").val(demorepaired.val());
	          $("#stitle").val(demoReload.val());
	          $("#level").val(demolevel.val());
	          
	          laydate.render({
	        	    elem: '#stime'
	        	    ,type: 'datetime'
	        	    ,value: demostime.val()
	        	  });
	        	  
	        	  laydate.render({
	        		    elem: '#etime'
	        		    ,type: 'datetime'
	        		    ,value:demoetime.val()
	        		  });
	        	  
	      }
	  };
	//这里绑定的是搜索按钮
	  $('body').on('click','#sbutton', function(){
	      var type = $(this).data('type');
	      active[type] ? active[type].call(this) : '';
	  });

  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'update'){
    	if(data.repaired!='已关闭'){
    	layer.open({
    		 type:2,
    		 area:['1000px','600px'],
    		  title: data.title,
    		  content: "traceupdate.jsp",
    		  skin:'layui-layer-molv',
    		  success: function(layero, index){
    			    var body = layer.getChildFrame('body', index);
    			    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
    			    body.find("[name='serial']").val(data.serial);
    			  },
 

    		}); 
    	}else{
    		layer.msg("已关闭的项目无法新建条目！");
    		};
    } else if(layEvent === 'delete'){
    	layer.confirm('真的删除这项工作吗？', function(){
    		$.ajax({
    			type: 'POST',
    			url: '${pageContext.request.contextPath }/delete.pdo', //请求路径
    			data: {
        			serial : data.serial,
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
    } else if(layEvent === 'detail'){
    	layer.open({
   		 type:2,
   		 area:['932px','600px'],
   		  title: data.title,
   		  content: "tracedetail.jsp"+"?serial="+data.serial,
   		  skin:'layui-layer-molv',
   			success: function(layero, index){
		    var body = layer.getChildFrame('body', index);
		    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    body.find("[name='serial']").val(data.serial);
		  },


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
//     xhr.open('GET', url, true);
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