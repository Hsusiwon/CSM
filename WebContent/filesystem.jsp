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
.rightmenu{font-size:12px; padding:5px 10px; border-radius: 2px;} 
.rightmenu li{line-height:20px; cursor: pointer;} 
.rightmenu li:hover { background-color: #666; color: #fff; }
/* ul.layui-tab-title li:first-child i{display:none;}  */
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

<div class="layui-container layui-row">

<div class="layui-col-md3">
  <div class="layui-panel site-menu">
    <ul class="layui-menu layui-menu-lg">
    
    <li class="layui-menu-item-group" lay-options="{type: 'group', isAllowSpread: true}">
        <div class="layui-menu-body-title">
          文件检索
        </div>
        <hr>
        <ul>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplayall.jsp" data-id="10" data-title="所有文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>所有文档 </span> 
                <span class="layui-font-12 layui-font-gray"></span>
              </a>    
            </div>
          </li>
        </ul>
      </li>
    
      <li class="layui-menu-item-group" lay-options="{type: 'group', isAllowSpread: true}">
        <div class="layui-menu-body-title">
          操作指导书
        </div>
        <hr>
        <ul>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="11" data-title="LC相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>LC </span> 
                <span class="layui-font-12 layui-font-gray">LC服务器与网络设备</span>
              </a>           
            </div>
          </li>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="12" data-title="SC相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>SC </span>
                <span class="layui-font-12 layui-font-gray">SC服务器与网络设备</span>
              </a>
            </div>
          </li>
           <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="13" data-title="POST相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>POST </span>
                <span class="layui-font-12 layui-font-gray">POST系统相关软硬件</span>
              </a>
            </div>
          </li>
           <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="14" data-title="GATE相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>GATE </span>
                <span class="layui-font-12 layui-font-gray">GAT系E统相关软硬件</span>
              </a>
            </div>
          </li>
           <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="15" data-title="TVM相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>TVM </span>
                <span class="layui-font-12 layui-font-gray">TVM系统相关软硬件</span>
              </a>
            </div>
          </li>
        </ul>
      </li>
      
      <li class="layui-menu-item-group" lay-options="{type: 'group', isAllowSpread: true}">
        <div class="layui-menu-body-title">
          应急预案
        </div>
        <hr>
        <ul>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="21" data-title="各类预案" href="#" class="site-demo-active" data-type="tabAdd">
                <span>各类应急预案 </span> 
                <span class="layui-font-12 layui-font-gray"></span>
              </a>    
            </div>
          </li>
        </ul>
      </li>
      
      <li class="layui-menu-item-group" lay-options="{type: 'group', isAllowSpread: true}">
        <div class="layui-menu-body-title">
          经验札记交流
        </div>
        <hr>
        <ul>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="31" data-title="系统相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>系统与硬件 </span> 
                <span class="layui-font-12 layui-font-gray">系统相关</span>
              </a>    
            </div>
          </li>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="32" data-title="数据相关文档" href="#" class="site-demo-active" data-type="tabAdd">
                <span>软件与数据 </span>
                <span class="layui-font-12 layui-font-gray">数据相关</span>
              </a>
            </div>
          </li>
        </ul>
      </li>
      
      <li class="layui-menu-item-group" lay-options="{type: 'group', isAllowSpread: true}">
        <div class="layui-menu-body-title">
          公司重要下发文件
        </div>
        <hr>
        <ul>
          <li class="">
            <div class="layui-menu-body-title">
              <a data-url="${pageContext.request.contextPath }/filedisplay.jsp" data-id="41" data-title="各类下发文件" href="#" class="site-demo-active" data-type="tabAdd">
                <span>各类信息文件汇总 </span> 
                <span class="layui-font-12 layui-font-gray"></span>
              </a>    
            </div>
          </li>
          
        </ul>
      </li>
      
    </ul>
  </div>
</div>

<div class="layui-col-md9 site-content" id="content">


 <!-- 选项卡和内容区 -->
    <div class="layui-tab layui-tab-brief" lay-filter="demo" lay-allowclose="true" style="margin-left:10px;margin-top:0;">
        <ul class="layui-tab-title">
        </ul>
        <ul class="layui-bg-green rightmenu" style="display: none;position: absolute;">
            <li data-type="refresh">刷新</li>
            <li data-type="closeOthers">关闭其他</li>
            <li data-type="closeRight">关闭右侧所有</li>
            <li data-type="closeAll">关闭所有</li>
        </ul>
        <div class="layui-tab-content">
        </div>
    </div>

</div>

</div>
	
<script>
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function(){
  var $ = layui.jquery
  ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
  
//触发事件
  var active = {
	tabAdd: function(url,id,name){
        //新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
        //关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
        element.tabAdd('demo', {
              title: name,
             content: '<iframe onload="FrameWH();" data-frameid="'+id+'" scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height=100%"></iframe>',
             id: id //规定好的id
         })
          //通过鼠标mouseover事件  动态将新增的tab下的li标签绑定鼠标右键功能的菜单   
                //下面的json.id是动态新增Tab的id，一定要传入这个id,避免重复加载mouseover数据
                $(".layui-tab-title li[lay-id=" + id + "]").mouseover(function () {
                    var tabId = $(this).attr("lay-id");
                    CustomRightClick(tabId); //给tab绑定右击事件
                });
	},tabDelete: function(id){
      //删除指定Tab项
      element.tabDelete('demo', id); 
    }
    ,tabChange: function(id){
      //切换到指定Tab项
      element.tabChange('demo', id); 
    }
    ,tabRefresh: function (id) { //刷新页面
        $("iframe[data-frameid='" + id + "']").attr("src", $("iframe[data-frameid='" + id + "']").attr("src")) //刷新框架
    }

  };
  
  //当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
  $('.site-demo-active').on('click', function () {
      var dataid = $(this);

      //这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
      if ($(".layui-tab-title li[lay-id]").length <= 0) {
          //如果比零小，则直接打开新的tab项
          active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
      } else {
          //否则判断该tab项是否以及存在

          var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
          $.each($(".layui-tab-title li[lay-id]"), function () {
              //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
              if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                  isData = true;
              }
          })
          if (isData == false) {
              //标志为false 新增一个tab项
              active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
          }
      }
      //最后不管是否新增tab，最后都转到要打开的选项页面上
      active.tabChange(dataid.attr("data-id"));
  });

  function CustomRightClick(id) {
      //取消右键  rightmenu属性开始是隐藏的 ，当右击的时候显示，左击的时候隐藏
      $('.layui-tab-title li').on('contextmenu', function () {
          return false;
      })
      $('.layui-tab-title,.layui-tab-title li').click(function () {
          $('.rightmenu').hide();
      });
      //单击取消右键菜单
      $('body,#aaa').click(function () {
          $('.rightmenu').hide();
      });
      //tab点击右键 
      $('.layui-tab-title li').on('contextmenu', function (e) {
          var popupmenu = $(".rightmenu");
          popupmenu.find("li").attr("data-id", id); //在右键菜单中的标签绑定id属性

          //判断右侧菜单的位置 
          l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
          t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
          popupmenu.css({
              left: l,
              top: t
          }).show(); //进行绝对定位
          return false;
      });
  }


  $(".rightmenu li").click(function () {
      //当前的tabId
      var currentTabId = $(this).attr("data-id");

      if ($(this).attr("data-type") == "closeOthers") { //关闭其他
          var tabtitle = $(".layui-tab-title li");
          $.each(tabtitle, function (i) {
              if ($(this).attr("lay-id") != currentTabId) {
                  active.tabDelete($(this).attr("lay-id"))
              }
          })
      } else if ($(this).attr("data-type") == "closeAll") { //关闭全部
          var tabtitle = $(".layui-tab-title li");
          $.each(tabtitle, function (i) {
              active.tabDelete($(this).attr("lay-id"))
          })

      } else if ($(this).attr("data-type") == "refresh") { //刷新页面
          active.tabRefresh($(this).attr("data-id"));

      } else if ($(this).attr("data-type") == "closeRight") { //关闭右边所有
          //找到当前聚焦的li之后的所有li标签 然后遍历
          var tabtitle = $(".layui-tab-title li[lay-id=" + currentTabId + "]~li");
          $.each(tabtitle, function (i) {
              active.tabDelete($(this).attr("lay-id"))
          })
      }

      $('.rightmenu').hide();
  });

  //导航栏点击选中时关闭其他选项卡
  $('.layui-nav-item').click(function () {
      $(this).siblings('li').attr('class', 'layui-nav-item');
  });

//打开默认页面
  active.tabAdd("${pageContext.request.contextPath }/filedisplayall.jsp", 10, "所有文档");
  active.tabChange(10);
  
});

var $ = layui.jquery;

function FrameWH() {
    var h = $(window).height();
    $("iframe").css("height", h + "px");
};

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