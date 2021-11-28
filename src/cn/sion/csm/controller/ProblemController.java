package cn.sion.csm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.sion.csm.model.Problem;
import cn.sion.csm.service.FactoryService;
import cn.sion.csm.service.ProblemService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet(urlPatterns = {"*.pdo"})
public class ProblemController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ProblemService problemService = FactoryService.getProblemService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//在这一个方法里能处理增删改查所有的功能
		String mt = req.getServletPath();
		mt = mt.substring(1);
		mt = mt.substring(0, mt.length()-4);
		try {
			Method method = this.getClass().getDeclaredMethod(mt, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unused")
	private void wxquery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String page = req.getParameter("page");
		String limit = req.getParameter("limit");
		String stitle = req.getParameter("stitle");
		String stime = req.getParameter("stime");
		String etime = req.getParameter("etime");
		String repaired = req.getParameter("repaired");
		String level = req.getParameter("level");
		List<Problem> problemList = problemService.getAllO(stitle,stime,etime,repaired,level);
		
		JSONArray array = JSONArray.fromObject(problemList);

		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(array);
        resp.getWriter().flush();
        resp.getWriter().close();				
	}
	
	@SuppressWarnings("unused")
	private void wxquerypage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String page = req.getParameter("page");
		String limit = req.getParameter("limit");
		String stitle = req.getParameter("stitle");
		String stime = req.getParameter("stime");
		String etime = req.getParameter("etime");
		String repaired = req.getParameter("repaired");
		String level = req.getParameter("level");
		List<Problem> problemListPage = problemService.getAll(stitle,stime,etime,repaired,level,page,limit);

		JSONArray array = JSONArray.fromObject(problemListPage);

		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(array);
        resp.getWriter().flush();
        resp.getWriter().close();				
	}
	
	@SuppressWarnings("unused")
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String page = req.getParameter("page");
		String limit = req.getParameter("limit");
		String stitle = req.getParameter("stitle");
		String stime = req.getParameter("stime");
		String etime = req.getParameter("etime");
		String repaired = req.getParameter("repaired");
		String level = req.getParameter("level");
		List<Problem> problemList = problemService.getAllO(stitle,stime,etime,repaired,level);
		List<Problem> problemListPage = problemService.getAll(stitle,stime,etime,repaired,level,page,limit);
//		主体JSON
		JSONObject jsonMain = new JSONObject();
		//item:[{}] 内容
		JSONArray array = JSONArray.fromObject(problemListPage);
		//为item 添加值
		jsonMain.put("code", 0);
		jsonMain.put("message","");
		jsonMain.put("count",problemList.size());
		jsonMain.put("data", array);
		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(jsonMain);
        resp.getWriter().flush();
        resp.getWriter().close();
//		String page = req.getParameter("page");
//		String limit = req.getParameter("limit");
//		String stitle = req.getParameter("stitle");
//		if(stitle!="" && stitle!=null) {
//			List<Problem> problemList = problemService.getAllSerialByStitle(stitle);
//			List<Problem> problemListPage = problemService.getAllSerialBySP(page, limit, stitle);
//			//主体JSON
//			JSONObject jsonMain = new JSONObject();
//			//item:[{}] 内容
//			JSONArray array = JSONArray.fromObject(problemListPage);
//			//为item 添加值
//			jsonMain.put("code", 0);
//			jsonMain.put("message","");
//			jsonMain.put("count",problemList.size());
//			jsonMain.put("data", array);
//			resp.setContentType("text/text;charset=utf-8");
//	        resp.setCharacterEncoding("UTF-8");
//	        resp.getWriter().print(jsonMain);
//	        resp.getWriter().flush();
//	        resp.getWriter().close();
//		}else {
//			List<Problem> problemList = problemService.getAllSerial();
//			List<Problem> problemListPage = problemService.getAllSerialByPage(page,limit);
//			//主体JSON
//			JSONObject jsonMain = new JSONObject();
//			//item:[{}] 内容
//			JSONArray array = JSONArray.fromObject(problemListPage);
//			//为item 添加值
//			jsonMain.put("code", 0);
//			jsonMain.put("message","");
//			jsonMain.put("count",problemList.size());
//			jsonMain.put("data", array);
//			resp.setContentType("text/text;charset=utf-8");
//	        resp.setCharacterEncoding("UTF-8");
//	        resp.getWriter().print(jsonMain);
//	        resp.getWriter().flush();
//	        resp.getWriter().close();
//		}
					
	}
	
	@SuppressWarnings("unused")
	private void wxquerydetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String serial = req.getParameter("serial");	
		List<Problem> problemList = problemService.getAllSerialNum(serial);
		
		JSONArray array = JSONArray.fromObject(problemList);

		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(array);
        resp.getWriter().flush();
        resp.getWriter().close();
	}
	
	@SuppressWarnings("unused")
	private void querydetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String serial = req.getParameter("serial");	
		List<Problem> problemList = problemService.getAllSerialNum(serial);
		//主体JSON
		JSONObject jsonMain = new JSONObject();
		//item:[{}] 内容
		JSONArray array = JSONArray.fromObject(problemList);
		//为item 添加值
		jsonMain.put("code", 0);
		jsonMain.put("message","");
		jsonMain.put("count",problemList.size());
		jsonMain.put("data", array);
		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(jsonMain);
        resp.getWriter().flush();
        resp.getWriter().close();
	}

	@SuppressWarnings("unused")
	private void getimg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getParameter("path");
			resp.setContentType("image/jpeg");
//			File f=new File(req.getSession().getServletContext().getRealPath("/WEB-INF/trace/")+path);
			File f=new File(path);
			byte[] buf=new byte[1024];
			int len=0;
			FileInputStream fis=new FileInputStream(f);
			OutputStream os=resp.getOutputStream();
			while((len=fis.read(buf))!=-1){
				os.write(buf, 0, len);
			}
			fis.close();
	}
	
	@SuppressWarnings("unused")
	private void gettxt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getParameter("path");
			resp.setContentType("text/plain");
//			File f=new File(req.getSession().getServletContext().getRealPath("/WEB-INF/trace/")+path);
			File f=new File(path);
			byte[] buf=new byte[1024];
			int len=0;
			FileInputStream fis=new FileInputStream(f);
			OutputStream os=resp.getOutputStream();
			while((len=fis.read(buf))!=-1){
				os.write(buf, 0, len);
			}
			fis.close();
	}
	
	@SuppressWarnings("unused")
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String serial = req.getParameter("serial");
		List<Problem> problemList = problemService.getAll(serial);
//		String filepath=req.getSession().getServletContext().getRealPath("/WEB-INF/trace/");
		for(Problem pro:problemList) {
			if(pro.getRecordcont()!=null && pro.getRecordcont()!="") {
				String[] path = pro.getRecordcont().split("trace_");
				String filepath = path[0];
				String[] txtPath = pro.getRecordcont().split("/");
				File txtfile = new File(filepath+txtPath[txtPath.length-1]);

				if(txtfile.isFile()) {
					txtfile.delete();
				}
				
				if(pro.getRecordpic()!=null && pro.getRecordpic()!="") {
				
					String[] picPath = pro.getRecordpic().split("/");
					String[] picList = picPath[picPath.length-1].split("add");
					for(String lst:picList) {
						File picfile = new File(filepath+lst);
	
						if(picfile.isFile()) {
							picfile.delete();
						}
					}
				}

			}else {
				String[] path = pro.getUpdatecont().split("trace_");
				String filepath = path[0];
				String[] txtPath = pro.getUpdatecont().split("/");
				File txtfile = new File(filepath+txtPath[txtPath.length-1]);

				if(txtfile.isFile()) {
					txtfile.delete();
				}

				
				if(pro.getUpdatepic()!=null && pro.getUpdatepic()!="") {
					String[] picPath = pro.getUpdatepic().split("/");
					String[] picList = picPath[picPath.length-1].split("add");
					for(String lst:picList) {
						File picfile = new File(filepath+lst);
	
						if(picfile.isFile()) {
							picfile.delete();
						}
					}
				}

			}
			
		}

		int rows = problemService.delete(serial);
		if(rows>0) {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","删除成功！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","删除失败！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
	}
	
	@SuppressWarnings("unused")
	private void deletedetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String serial = req.getParameter("serial");
		String serialnum = req.getParameter("serialnum");
		Problem problem = problemService.getOne(serial, serialnum);
//		String filepath=req.getSession().getServletContext().getRealPath("/WEB-INF/trace/");
		String[] path = problem.getUpdatecont().split("trace_");
		String filepath = path[0];	
		
		String[] txtPath = problem.getUpdatecont().split("/");
		File txtfile = new File(filepath+txtPath[txtPath.length-1]);

		if(txtfile.isFile()) {
			txtfile.delete();
		}

		if(problem.getUpdatepic()!=null && problem.getUpdatepic()!="") {
			String[] picPath = problem.getUpdatepic().split("/");
			String[] picList = picPath[picPath.length-1].split("add");
			for(String lst:picList) {
				File picfile = new File(filepath+lst);
	
				if(picfile.isFile()) {
					picfile.delete();
				}
			}
		}

				
		int rows = problemService.deleted(serial,serialnum);
		if(rows>0) {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","删除成功！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","删除失败！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
	}
	
	@SuppressWarnings("unused")
	private void wxadd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int serial = 0 ;
		serial = problemService.getMaxSerial();
		serial +=1;

		int serialnum = 1 ;
		String title = req.getParameter("title");
		String type = req.getParameter("type");
		String level = req.getParameter("level");
		String repaired = req.getParameter("repaired");
		String recordtime = req.getParameter("recordtime");
		String recorder = req.getParameter("recorder");
		String recordcont = req.getParameter("recordcont");
		String recordpic = req.getParameter("recordpic");
		
		//指定上传位置
//		String savePicPath = req.getSession().getServletContext().getRealPath("/WEB-INF/trace/");
		String savePicPath = "/home/csmfile/trace";
		File saveDir = new File(savePicPath);  
		//如果目录不存在，就创建目录  
		if(!saveDir.exists()){  
		    saveDir.mkdir();  
		}
		
		String saveTxtName = "trace_"+serial +"-"+serialnum+".txt"; 
		savePicPath = makePath(saveTxtName,savePicPath);

		int rows = 0;
		Problem problem = new Problem();
		
			try{

				
				PrintWriter fos = new PrintWriter(savePicPath + "/" + saveTxtName,"UTF-8");//注意：windows"\\"和linux"/"路径符不一样
				fos.println(recordcont);		
				fos.close();
				recordcont= savePicPath + "/" + saveTxtName;//注意：windows"\\"和linux"/"路径符不一样
				//将结果写入数据库
				
				problem.setSerial(serial);
				problem.setSerialnum(serialnum);
				problem.setTitle(title);
				problem.setType(type);
				problem.setLevel(level);
				problem.setRepaired(repaired);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				problem.setRecordtime(sdf.parse(recordtime));
				problem.setRecorder(recorder);
				problem.setRecordcont(recordcont);
				problem.setRecordpic(recordpic);
				rows = problemService.add(problem);			
			}catch(Exception e){
				e.printStackTrace();
			}

			if(rows>0) {
				JSONObject jsonMain = JSONObject.fromObject(problem);
				String str = jsonMain.toString();
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(str);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}else {
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print("待办录入失败！");
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}
 
		
	}
	
	@SuppressWarnings("unused")
	private void wxaddpic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		String serial = "";
		String title = "";
		String type = "";
		String level = "";
		String repaired = "";
		String recordtime = "";
		String recorder = "";
		String recordcont = "";
		String recordpic = "";
		

		String savePicPath = "";


		String pathTemp="";
		String name ="";
		String picEx ="";	
		String picName = "";
		String picNum = "" ;
		String savePicName ="";
		String saveTxtName ="";
		int rows = 0;
		
			//创建文件上传核心类 
			FileItemFactory factory = new DiskFileItemFactory(); // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
			ServletFileUpload upload = new ServletFileUpload(factory); // 用以上工厂实例化上传组件
			OutputStream out = null;//输入输出流不要在循环体定义
			InputStream in = null;
			int code = 0;
			String message="项目录入成功！";
			try{
				//处理表单请求
				//Unnecessary@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(req);
				if(items!=null && items.size()>0) {
					for(FileItem fileItem:items) {
//					fileItem就是封装一个一个form提交过来的表单项：普通表单项/文件域表单项
//					遍历FileItem对象的集合，第一步，判断这个表单项是不是普通表单项
						if(fileItem.isFormField()) {
							if(fileItem.getFieldName().equals("serial")) {
								serial = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("recordpic")) {
								recordpic = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("picNum")) {
								picNum = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("path")) {
								pathTemp = fileItem.getString("UTF-8");
								String[] path =pathTemp.split("trace_");
								savePicPath=path[0];
							}
						}else {
							if(fileItem!=null && fileItem.getSize()>0) {
								name= fileItem.getName();
								picEx = name.substring(name.lastIndexOf(".")+1);
								picName = serial +"-"+1+"-"+ picNum;
								savePicName = "trace_"+picName+"." + picEx;
								
								//先创建一个输出流
								 out = new FileOutputStream( savePicPath + savePicName);//注意：windows"\\"和linux"/"路径符不一样
								 in = fileItem.getInputStream();
								//建立缓冲过区，做一个搬运文件数据流的勺子
								 byte[] buffer = new byte[1024];
								 int len = 0;
								 while((len=in.read(buffer))>0) {
									 out.write(buffer, 0, len);
								 }
								 in.close();
								 out.close();
								 if(recordpic.equals("")) {
									 recordpic=savePicPath  + savePicName+"add";//注意：windows"\\"和linux"/"路径符不一样
								 }else {
									 recordpic=recordpic +savePicName+"add";
								 }
							
							}
						}
					}
				}
				rows = problemService.wxaddpic(serial,"1",recordpic);			
			}catch(Exception e){
				e.printStackTrace();
			}

			Problem problem = problemService.getOne(serial, "1");
			if(rows>0) {
				JSONObject jsonMain = JSONObject.fromObject(problem);
				String str = jsonMain.toString();
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(str);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}else {
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print("图片录入失败！");
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}
				
	}
	
	@SuppressWarnings("unused")
	private void wxedit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String serial = req.getParameter("serial");
		int serialnum = problemService.getMaxSerialNum(serial)+1;
		String repaired = req.getParameter("repaired");
		String updatetime = req.getParameter("updatetime");
		String updater = req.getParameter("updater");
		String updatecont = req.getParameter("updatecont");
		String updatepic = "";
		
		//指定上传位置
//		String savePicPath = req.getSession().getServletContext().getRealPath("/WEB-INF/trace/");
		String savePicPath = "/home/csmfile/trace";
		File saveDir = new File(savePicPath);  
		//如果目录不存在，就创建目录  
		if(!saveDir.exists()){  
		    saveDir.mkdir();  
		}
		
		String saveTxtName = "trace_"+serial +"-"+serialnum+".txt"; 
		savePicPath = makePath(saveTxtName,savePicPath);

		int rows = 0;
		Problem problem = new Problem();
		
			try{

				
				PrintWriter fos = new PrintWriter(savePicPath + "/" + saveTxtName,"UTF-8");//注意：windows"\\"和linux"/"路径符不一样
				fos.println(updatecont);		
				fos.close();
				updatecont= savePicPath + "/" + saveTxtName;//注意：windows"\\"和linux"/"路径符不一样
				//将结果写入数据库
				problem.setSerial(Integer.parseInt(serial));
				problem.setSerialnum(serialnum);
				problem.setRepaired(repaired);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				problem.setUpdatetime(sdf.parse(updatetime));
				problem.setUpdater(updater);
				problem.setUpdatecont(updatecont);
				problem.setUpdatepic(updatepic);
				rows = problemService.addd(problem);	
			}catch(Exception e){
				e.printStackTrace();
			}

			if(rows>0) {
				JSONObject jsonMain = JSONObject.fromObject(problem);
				String str = jsonMain.toString();
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(str);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}else {
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print("更新录入失败！");
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}	
				
	}
	
	@SuppressWarnings("unused")
	private void wxeditpic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		String serial = "";
		String serialnum = "";
		String repaired = "";
		String updatepic = "";
		

		String savePicPath = "";


		String pathTemp="";
		String name ="";
		String picEx ="";	
		String picName = "";
		String picNum = "" ;
		String savePicName ="";
		String saveTxtName ="";
		int rows = 0;
		
			//创建文件上传核心类 
			FileItemFactory factory = new DiskFileItemFactory(); // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
			ServletFileUpload upload = new ServletFileUpload(factory); // 用以上工厂实例化上传组件
			OutputStream out = null;//输入输出流不要在循环体定义
			InputStream in = null;
			int code = 0;
			String message="项目录入成功！";
			try{
				//处理表单请求
				//Unnecessary@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(req);
				if(items!=null && items.size()>0) {
					for(FileItem fileItem:items) {
//					fileItem就是封装一个一个form提交过来的表单项：普通表单项/文件域表单项
//					遍历FileItem对象的集合，第一步，判断这个表单项是不是普通表单项
						if(fileItem.isFormField()) {
							if(fileItem.getFieldName().equals("serial")) {
								serial = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("serialnum")) {
								serialnum = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("updatepic")) {
								updatepic = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("picNum")) {
								picNum = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("path")) {
								pathTemp = fileItem.getString("UTF-8");
								String[] path =pathTemp.split("trace_");
								savePicPath=path[0];
							}
						}else {
							if(fileItem!=null && fileItem.getSize()>0) {
								name= fileItem.getName();
								picEx = name.substring(name.lastIndexOf(".")+1);
								picName = serial +"-"+serialnum+"-"+ picNum;
								savePicName = "trace_"+picName+"." + picEx;
								
								//先创建一个输出流
								 out = new FileOutputStream( savePicPath + savePicName);//注意：windows"\\"和linux"/"路径符不一样
								 in = fileItem.getInputStream();
								//建立缓冲过区，做一个搬运文件数据流的勺子
								 byte[] buffer = new byte[1024];
								 int len = 0;
								 while((len=in.read(buffer))>0) {
									 out.write(buffer, 0, len);
								 }
								 in.close();
								 out.close();
								 if(updatepic.equals("")) {
									 updatepic=savePicPath  + savePicName+"add";//注意：windows"\\"和linux"/"路径符不一样
								 }else {
									 updatepic=updatepic +savePicName+"add";
								 }
							
							}
						}
					}
				}
				rows = problemService.wxeditpic(serial,serialnum,updatepic);			
			}catch(Exception e){
				e.printStackTrace();
			}

			Problem problem = problemService.getOne(serial, serialnum);
			if(rows>0) {
				JSONObject jsonMain = JSONObject.fromObject(problem);
				String str = jsonMain.toString();
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(str);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}else {
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print("图片录入失败！");
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}
				
	}
	
	@SuppressWarnings("unused")
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int serial = 0 ;
		serial = problemService.getMaxSerial();
		serial +=1;

		int serialnum = 1 ;
		String title = "";
		String type = "";
		String level = "";
		String repaired = "";
		String recordtime = "";
		String recorder = "";
		String recordcont = "";
		String recordpic = "";

		//指定上传位置
//		String savePicPath = req.getSession().getServletContext().getRealPath("/WEB-INF/trace/");
		String savePicPath = "/home/csmfile/trace";
		File saveDir = new File(savePicPath);  
		//如果目录不存在，就创建目录  
		if(!saveDir.exists()){  
		    saveDir.mkdir();  
		}

		
		String name ="";
		String picEx ="";	
		String picName = "";
		int picNum = 1 ;
		String savePicName ="";
		String saveTxtName ="";
		int rows = 0;
		
			//创建文件上传核心类 
			FileItemFactory factory = new DiskFileItemFactory(); // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
			ServletFileUpload upload = new ServletFileUpload(factory); // 用以上工厂实例化上传组件
			OutputStream out = null;//输入输出流不要在循环体定义
			InputStream in = null;
			int code = 0;
			String message="项目录入成功！";
			try{
				//处理表单请求
				//Unnecessary@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(req);
				if(items!=null && items.size()>0) {
					for(FileItem fileItem:items) {
//					fileItem就是封装一个一个form提交过来的表单项：普通表单项/文件域表单项
//					遍历FileItem对象的集合，第一步，判断这个表单项是不是普通表单项
						if(fileItem.isFormField()) {
							if(fileItem.getFieldName().equals("title")) {
								title = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("type")) {
								type = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("level")) {
								level = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("repaired")) {
								repaired = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("recordtime")) {
								recordtime = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("recorder")) {
								recorder = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("recordcont")) {
								recordcont = fileItem.getString("UTF-8");
								saveTxtName = "trace_"+serial +"-"+serialnum+".txt"; 
								savePicPath = makePath(saveTxtName,savePicPath);
							}
							
						}else {
							if(fileItem!=null && fileItem.getSize()>0) {
								name= fileItem.getName();
								picEx = name.substring(name.lastIndexOf(".")+1);
								picName = serial +"-"+serialnum+"-"+ picNum;
								savePicName = "trace_"+picName+"." + picEx;
								
								//先创建一个输出流
								 out = new FileOutputStream( savePicPath + "/" + savePicName);//注意：windows"\\"和linux"/"路径符不一样
								 in = fileItem.getInputStream();
								//建立缓冲过区，做一个搬运文件数据流的勺子
								 byte[] buffer = new byte[1024];
								 int len = 0;
								 while((len=in.read(buffer))>0) {
									 out.write(buffer, 0, len);
								 }
								 in.close();
								 out.close();
								 if(recordpic.equals("")) {
									 recordpic=savePicPath + "/" + savePicName+"add";//注意：windows"\\"和linux"/"路径符不一样
								 }else {
									 recordpic=recordpic +savePicName+"add";
								 }
								 picNum +=1;
							}
						}
					}
				}
				//将文本域写入txt文件
				
				PrintWriter fos = new PrintWriter(savePicPath + "/" + saveTxtName,"UTF-8");//注意：windows"\\"和linux"/"路径符不一样
				fos.println(recordcont);		
				fos.close();
				recordcont= savePicPath + "/" + saveTxtName;//注意：windows"\\"和linux"/"路径符不一样
				//将结果写入数据库
				Problem problem = new Problem();
				problem.setSerial(serial);
				problem.setSerialnum(serialnum);
				problem.setTitle(title);
				problem.setType(type);
				problem.setLevel(level);
				problem.setRepaired(repaired);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				problem.setRecordtime(sdf.parse(recordtime));
				problem.setRecorder(recorder);
				problem.setRecordcont(recordcont);
				problem.setRecordpic(recordpic);
				rows = problemService.add(problem);			
			}catch(Exception e){
				e.printStackTrace();
			}
			if(rows>0) {
				JSONObject jsonMain = new JSONObject();
				jsonMain.put("code", code);
				jsonMain.put("message","工作新建成功！");
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(jsonMain);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}else {
				JSONObject jsonMain = new JSONObject();
				jsonMain.put("code", code);
				jsonMain.put("message","工作新建失败！");
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(jsonMain);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}
	}
	
	@SuppressWarnings("unused")
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String serial ="";
		int serialnum = 1 ;
		String repaired = "";
		String updatetime = "";
		String updater = "";
		String updatecont = "";
		String updatepic = "";

		//指定上传位置
//		String savePicPath = req.getSession().getServletContext().getRealPath("/WEB-INF/trace/");
		String savePicPath = "/home/csmfile/trace";
		File saveDir = new File(savePicPath);  
		//如果目录不存在，就创建目录  
		if(!saveDir.exists()){  
		    saveDir.mkdir();  
		}
		
		String name ="";
		String picEx ="";	
		String picName = "";
		int picNum = 1 ;
		String savePicName ="";
		String saveTxtName ="";
		int rows = 0;
		
			//创建文件上传核心类 
			FileItemFactory factory = new DiskFileItemFactory(); // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
			ServletFileUpload upload = new ServletFileUpload(factory); // 用以上工厂实例化上传组件
			OutputStream out = null;//输入输出流不要在循环体定义
			InputStream in = null;
			int code = 0;
			String message="项目更新成功！";
			try{
				//处理表单请求
				//Unnecessary@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(req);
				if(items!=null && items.size()>0) {
					for(FileItem fileItem:items) {
//					fileItem就是封装一个一个form提交过来的表单项：普通表单项/文件域表单项
//					遍历FileItem对象的集合，第一步，判断这个表单项是不是普通表单项
						if(fileItem.isFormField()) {
							if(fileItem.getFieldName().equals("serial")) {
								serial = fileItem.getString("UTF-8");
								serialnum = problemService.getMaxSerialNum(serial)+1;
							}
							if(fileItem.getFieldName().equals("repaired")) {
								repaired = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("updatetime")) {
								updatetime = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("updater")) {
								updater = fileItem.getString("UTF-8");
							}
							if(fileItem.getFieldName().equals("updatecont")) {
								updatecont = fileItem.getString("UTF-8");
								saveTxtName = "trace_"+serial +"-"+serialnum+".txt"; 
								savePicPath = makePath(saveTxtName,savePicPath);
							}
							
						}else {
							if(fileItem!=null && fileItem.getSize()>0) {
								name= fileItem.getName();
								picEx = name.substring(name.lastIndexOf(".")+1);
								picName = serial +"-"+serialnum+"-"+ picNum;
								savePicName = "trace_"+picName+"." + picEx;
								//先创建一个输出流
								 out = new FileOutputStream(savePicPath + "/" + savePicName);//注意：windows"\\"和linux"/"路径符不一样
								 in = fileItem.getInputStream();
								//建立缓冲过区，做一个搬运文件数据流的勺子
								 byte[] buffer = new byte[1024];
								 int len = 0;
								 while((len=in.read(buffer))>0) {
									 out.write(buffer, 0, len);
								 }
								 in.close();
								 out.close();
								 if(updatepic.equals("")) {
									 updatepic=savePicPath + "/" +savePicName+"add";//注意：windows"\\"和linux"/"路径符不一样
								 }else {
									 updatepic=updatepic+savePicName+"add";
								 }
								 picNum +=1;
							}
						}
					}
				}
				//将文本域写入txt文件
				
				PrintWriter fos = new PrintWriter(savePicPath + "/" + saveTxtName,"UTF-8");//注意：windows"\\"和linux"/"路径符不一样
				fos.println(updatecont);
				fos.close();
				updatecont=savePicPath + "/" +saveTxtName;//注意：windows"\\"和linux"/"路径符不一样
				//将结果写入数据库
				Problem problem = new Problem();
				problem.setSerial(Integer.parseInt(serial));
				problem.setSerialnum(serialnum);
				problem.setRepaired(repaired);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				problem.setUpdatetime(sdf.parse(updatetime));
				problem.setUpdater(updater);
				problem.setUpdatecont(updatecont);
				problem.setUpdatepic(updatepic);
				rows = problemService.addd(problem);			
			}catch(Exception e){
				e.printStackTrace();
			}
			if(rows>0) {
				JSONObject jsonMain = new JSONObject();
				jsonMain.put("code", code);
				jsonMain.put("message","工作更新成功！");
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(jsonMain);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}else {
				JSONObject jsonMain = new JSONObject();
				jsonMain.put("code", code);
				jsonMain.put("message","工作更新失败！");
				resp.setContentType("text/text;charset=utf-8");
		        resp.setCharacterEncoding("UTF-8");
		        resp.getWriter().print(jsonMain);
		        resp.getWriter().flush();
		        resp.getWriter().close();
			}
	}
	
	private String makePath(String saveFileName, String savePath) {
		//拿到文件名，在内存中的地址，hashCode值
		int hashCode = saveFileName.hashCode();
		int dir1 = hashCode&0xf;//dir1的值，这个与运算的范围在0-15
		int dir2 = (hashCode >> 4)&0xf;
		//用dir1和dir2构造我的新的存储文件目录
		String dir = savePath+"/"+dir1+"/"+dir2;//注意：windows和linux路径符不一样
		File file = new File(dir);
		if(!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}

}
