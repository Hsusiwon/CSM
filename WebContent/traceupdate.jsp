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
<form class="layui-form" id="traceupdate">
	<div class="layui-form-item">
		<input type="hidden" name="serial" />
	</div>
	<div class="layui-form-item">
	    <label class="layui-form-label">状态</label>
	    <div class="layui-input-block">
	      <input type="radio" name="repaired" value="2" title="执行中" checked>
	      <input type="radio" name="repaired" value="3" title="已关闭">
	    </div>
  	</div>
  	<div class="layui-form-item">
		<div class="layui-inline">
	      <label class="layui-form-label">处理时间</label>
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" id="uptime" name="updatetime" placeholder="yyyy-MM-dd HH:mm:ss" readonly="readonly">
	      </div>
    	</div>
	</div>
	<div class="layui-form-item">
		<input type="hidden" name="updater" value="${user.username }" />
	</div>
	<div class="layui-form-item layui-form-text">
    	<label class="layui-form-label">处理过程与结果</label>
    	<div class="layui-input-block">
      		<textarea name="updatecont" placeholder="请输入现象描述与处理方式结果" class="layui-textarea"></textarea>
    	</div>
  	</div>
  	<div class="layui-upload">
	  <button type="button" class="layui-btn" id="upp">选择图片</button> 
	  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
	    预览图：
	    <div class="layui-upload-list" id="viewp"></div>
	 </blockquote>
	</div>
	
	
	<div class="layui-form-item">
		<div class="layui-input-block">
			<button type="submit" class="layui-btn" lay-submit="" lay-filter="upsub">立即上传</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>

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
    elem: '#uptime'
    ,type: 'datetime'
   	,value: curDate
  });
  
//多图片上传
  upload.render({
	elem: '#upp'
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
// 		obj.preview(function(index, file, result){
// 		       $('#viewp').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 50px;height: 50px;" onclick="previewImg(this);">')
// 		     });
//         var that = this;
        //预读本地文件示例，不支持ie8
        obj.preview(function (index, file, result) {
//             console.log("选中的文件：", file);
//             var index = layer.load(1, {
//                 content: '图片上传中....',
//                 shade: 0.2,
//                 success: function (layer) {
//                     layer.find('.layui-layer-content').css({
//                         'paddingTop': '40px',
//                         'width': '60px',
//                         'textAlign': 'center',
//                         'backgroundPositionX': 'center'
//                     });
//                 }
//             });
            //这段开始才是压缩的重点，所以关注这段代码即可
            //若图片超过1M则启动压缩
            if (file.size > (1024 * 1024)) {
//                 console.log("大于1m");
                canvasDataURL(file, this, function (blob) {
                    console.log("压缩后的二进制Blob对象：",blob);
                    console.log("压缩前：" + (file.size / 1024 / 1024) + "M");
                    console.log("压缩后：" + (blob.size / 1024 / 1024) + "M");
                    var compresFile = new File([blob], file.name, {
                        type: file.type
                    })
//                     console.log("压缩后的文件：", compresFile);
                    var reader = new FileReader();      //读取文件的对象
                    reader.onload = function ( event ) { 
                    	var txt = event.target.result;
//                     	console.log("压缩后的文件码2：", txt);
                    	$('#viewp').append('<img src="'+ txt +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 50px;height: 50px;" onclick="previewImg(this);">')                   	
                    };
                    reader.readAsDataURL(compresFile);
//                     obj.upload(1, compresFile);
//                     $('#viewp').append('<img src="'+ compresFile.data +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 50px;height: 50px;" onclick="previewImg(this);">')
                })
            }
            //若图片不超过1M则无需压缩，直接传
            else {
//             	console.log("小于1m");
//                 var picDom = $(that.item).find("img");
//                 picDom.attr('src', result);
//                 obj.upload(1, file);
					$('#viewp').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img" style="width: 50px;height: 50px;" onclick="previewImg(this);">')
            }
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

  form.on('submit(upsub)', function (data) {
	 
		var myForm = new FormData(document.getElementById("traceupdate"));
	 
		$.ajax({
			type: 'POST',
			url: '${pageContext.request.contextPath }/edit.pdo', //请求路径
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

var $ = layui.jquery;
//下面是压缩部分的核心代码
/**
 * 通过canvas画布实现压缩，并转化为base64格式的图片
 * @param {File} file : 图片
 * @param {Object} item ：通过item找到当前对象的img标签
 * @param {Function} callback ：回调函数
 */
function canvasDataURL(file,item,callback) { //压缩转化为base64
    var reader = new FileReader();      //读取文件的对象
    reader.readAsDataURL(file);         //对文件读取，读取完成后会将内容以base64的形式赋值给result属性
    reader.onload = function (e) {      //读取完成的钩子
        const img = new Image();
        const quality = 0.5; // 图像质量
        //先创建canvas画布，再获取canvas画布上的2d绘图环境，通过这个2d绘图环境才可使用绘制API
        const canvas = document.createElement('canvas');        //创建canvas画布
        const drawer = canvas.getContext('2d');                 //返回一个在画布上绘制2d图的环境对象，该对象上包含有canvas绘制2d图形的API
        img.src = this.result;
        // console.log("FileReader对象：",this);
        //图片预览
//         var picDom = $(item.item).find("img");  
//         picDom.attr('src', this.result); //图片链接（base64）
		
        //图片压缩代码，需要注意的是，img图片渲染是异步的，所以必须在img的onlaod钩子中再进行相应操作
        img.onload = function () {
            canvas.width = img.width;
            canvas.height = img.height;
            drawer.drawImage(img, 0, 0, canvas.width, canvas.height);
            convertBase64UrlToBlob(canvas.toDataURL(file.type, quality), callback);
        }
    }
}
//下面是上传部分的核心代码
/**
 * 将base64格式转化为Blob格式
 * @param {string} urlData : urlData格式的数据，通过这个转化为Blob对象
 * @param {Function} callback : 回调函数
 */
function convertBase64UrlToBlob(urlData, callback) { //将base64转化为文件格式
    // console.log("压缩成base64的对象：",urlData);
    const arr = urlData.split(',')
    // console.log("arr",arr);
    const mime = arr[0].match(/:(.*?);/)[1]
    const bstr = atob(arr[1])   //atob方法用于解码base64
    // console.log("将base64进行解码:",bstr);
    let n = bstr.length
    const u8arr = new Uint8Array(n)
    while (n--) {
    u8arr[n] = bstr.charCodeAt(n)
    }
    // console.log("Uint8Array:",u8arr);
    callback(new Blob([u8arr], {
    type: mime
    }));
}
</script>

</body>
</html>