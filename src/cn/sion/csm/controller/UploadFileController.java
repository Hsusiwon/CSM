package cn.sion.csm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sion.csm.model.UploadFile;
import cn.sion.csm.service.FactoryService;
import cn.sion.csm.service.UploadFileService;
import cn.sion.csm.utils.office2PDF;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet(urlPatterns = {"*.up"})
public class UploadFileController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UploadFileService ufs = FactoryService.getUploadFileService();

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
		mt = mt.substring(0, mt.length()-3);
		try {
			Method method = this.getClass().getDeclaredMethod(mt, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	protected void wxupload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接受网页发送来的文件信息，文件本身，描述信息
		//保存接收到的文件的工作，不在控制层实现，转发到service，实现保存文件
		try {
			ufs.wxsaveFile(req, resp);
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("data", "上传成功！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}catch (Exception e) {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("data", "上传失败！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
	}
	
	protected void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接受网页发送来的文件信息，文件本身，描述信息
		//保存接收到的文件的工作，不在控制层实现，转发到service，实现保存文件
		try {
			ufs.saveFile(req, resp);
			//这里没有抓到异常，证明上传文件成功
			req.setAttribute("noteMsg1", "文件上传成功");
			req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
		} catch (Exception e) {
			//让服务层去实现保存文件具体业务的逻辑功能代码，单个文件超标，总的文件超限，禁止上传的文件类型,在jsp页面显示出来
			//服务层这里，获取到异常信息，注入jsp页面并显示
			//System.out.println("controller's error:"+e.getMessage());
			req.setAttribute("noteMsg1", e.getMessage());
			req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
		}
	}
	
	protected void write(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接受网页发送来的文件信息，文件本身，描述信息
				//保存接收到的文件的工作，不在控制层实现，转发到service，实现保存文件
				try {
					ufs.writeFile(req, resp);
					//这里没有抓到异常，证明上传文件成功
					req.setAttribute("noteMsg2", "文件上传成功");
					req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
				} catch (Exception e) {
					//让服务层去实现保存文件具体业务的逻辑功能代码，单个文件超标，总的文件超限，禁止上传的文件类型,在jsp页面显示出来
					//服务层这里，获取到异常信息，注入jsp页面并显示
					//System.out.println("controller's error:"+e.getMessage());
					req.setAttribute("noteMsg2", e.getMessage());
					req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
				}
		
	}
	
	@SuppressWarnings("unused")
	private void display(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<UploadFile> list = ufs.getUploadFiles();
		req.setAttribute("uploadfiles", list);
		req.getRequestDispatcher("/queryallfiles.jsp").forward(req, resp);
	}
	
	@SuppressWarnings("unused")
	private void displaysearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = req.getParameter("page");
		String limit = req.getParameter("limit");
		String stitle = req.getParameter("stitle");

		if(stitle!="" && stitle!=null) {

			List<UploadFile> fileList = ufs.getUploadFilesByStitle(stitle);

			List<UploadFile> fileListPage = ufs.getUploadFilesBySP(page, limit, stitle);
			//主体JSON
			JSONObject jsonMain = new JSONObject();
			//item:[{}] 内容
			JSONArray array = JSONArray.fromObject(fileListPage);
			//为item 添加值
			jsonMain.put("code", 0);
			jsonMain.put("message","");
			jsonMain.put("count",fileList.size());
			jsonMain.put("data", array);
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {

			List<UploadFile> fileList = ufs.getUploadFiles();

			List<UploadFile> fileListPage = ufs.getUploadFilesByPage(page, limit);
			//主体JSON
			JSONObject jsonMain = new JSONObject();
			//item:[{}] 内容
			JSONArray array = JSONArray.fromObject(fileListPage);
			//为item 添加值
			jsonMain.put("code", 0);
			jsonMain.put("message","");
			jsonMain.put("count",fileList.size());
			jsonMain.put("data", array);
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
	}
	
	@SuppressWarnings("unused")
	private void wxdisplaysearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String stitle = req.getParameter("stitle");
		List<UploadFile> fileList = ufs.getUploadFilesByStitle(stitle);
		JSONArray array = JSONArray.fromObject(fileList);
		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(array);
        resp.getWriter().flush();
        resp.getWriter().close();
		
	}
	
	@SuppressWarnings("unused")
	private void wxdisplaysection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String belong = req.getParameter("belong");
		long fileNum = ufs.countUploadFilesByBelong(belong);
		List<UploadFile> fileList = ufs.getUploadFilesByBelongwx(belong);
		JSONArray array = JSONArray.fromObject(fileList);
		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(array);
        resp.getWriter().flush();
        resp.getWriter().close();
		
	}
	
	@SuppressWarnings("unused")
	private void displaysection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = req.getParameter("page");
		String limit = req.getParameter("limit");
		String belong = req.getParameter("belong");
		
		long fileNum = ufs.countUploadFilesByBelong(belong);

		List<UploadFile> fileList = ufs.getUploadFilesByBelong(page,limit,belong);
		//主体JSON
		JSONObject jsonMain = new JSONObject();
		//item:[{}] 内容
		JSONArray array = JSONArray.fromObject(fileList);
		//为item 添加值
		jsonMain.put("code", 0);
		jsonMain.put("message","");
		jsonMain.put("count",fileNum);
		jsonMain.put("data", array);
		resp.setContentType("text/text;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print(jsonMain);
        resp.getWriter().flush();
        resp.getWriter().close();
		
	}
	
	@SuppressWarnings("unused")
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String filePath = req.getParameter("filePath");
		
		ufs.deleteUploadFile(id);
		resp.sendRedirect(req.getContextPath()+"/display.up");
		
	}
	
	@SuppressWarnings("unused")
	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.获取要下载文件的绝对路径和文件名
		int id = Integer.parseInt(req.getParameter("id"));

		UploadFile uf = ufs.getUploadFileById(id);
		String filePath = uf.getSavePath()+"/"+uf.getSaveName();//注意：windows"\\"和linux"/"路径符不一样
		String fileName = uf.getFileName();

		//2.针对IE或者以IE为内核的浏览器处理，主要是解决下载的时候文件名是中文乱码
		String userAgent = req.getHeader("user-Agent");
		if(userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			fileName=java.net.URLEncoder.encode(fileName,"UTF-8");
		}else {
			//非IE浏览器的处理：
			fileName=new String(fileName.getBytes("utf-8"),"ISO-8859-1");
		}
		//3.设置content-disposition响应头控制浏览器以下载的方式打开文件
		resp.setHeader("content-disposition", "attachment;filename="+fileName);
		//4.获取要下载文件的输入流
		InputStream in = new FileInputStream(filePath);
		//5.创建写缓冲区
		int len = 0;
		byte[] buffer = new byte[1024];
		//6.通过response对象获取OutPutStream输出流对象
		OutputStream os = resp.getOutputStream();
		//7.将FileInputStream对象写到buffer缓冲区
		while((len=in.read(buffer))>0) {
			os.write(buffer,0,len);
		}
		//8.关闭流
		in.close();
		os.close();
	}
	
	@SuppressWarnings("unused")
	private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.获取要下载文件的绝对路径和文件名
		int id = Integer.parseInt(req.getParameter("id"));
		
		UploadFile uf = ufs.getUploadFileById(id);
		String filePath = uf.getSavePath()+"/"+uf.getSaveName();//注意：windows"\\"和linux"/"路径符不一样
		String fileName = uf.getFileName();
		//2.转换文件为pdf文件	
		String path=uf.getSavePath()+"/";//注意：windows"\\"和linux"/"路径符不一样
		
		String path1= path+uf.getSaveName();
		
		String path2= path+uf.getSaveName().substring(0,uf.getSaveName().lastIndexOf("."))+".pdf";
		
		String fileEx = path1.substring(path1.lastIndexOf(".")+1);
		
		
		File file1= new File(path1);
		File file2= new File(path2);

		if (!file2.exists()) {
			
			office2PDF.officeToPDF(path1, path2);
			
			try{
				
				InputStream fis= new FileInputStream(path2);
				byte[] buffer = new byte[1024];

				resp.reset();

				resp.addHeader("Content-Disposition", "inline;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));

				resp.addHeader("Content-Length", "" +file2.length());

				resp.setContentType("application/pdf");

				OutputStream toClient= resp.getOutputStream();
				
				int nbytes = 0;
				while ((nbytes = fis.read(buffer)) != -1) {

				toClient.write(buffer,0, nbytes);

				toClient.flush();

				}

				toClient.flush();

				toClient.close();

				fis.close();

				}catch(Exception ex) {

				ex.printStackTrace();

				}
		}else{
			
			try{
			
			InputStream fis= new FileInputStream(path2);
			byte[] buffer = new byte[1024];

			resp.reset();

			resp.addHeader("Content-Disposition", "inline;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));

			resp.addHeader("Content-Length", "" +file2.length());

			resp.setContentType("application/pdf");

			OutputStream toClient= resp.getOutputStream();
			
			int nbytes = 0;
			while ((nbytes = fis.read(buffer)) != -1) {

			toClient.write(buffer,0, nbytes);

			toClient.flush();

			}

			toClient.flush();

			toClient.close();

			fis.close();

			}catch(Exception ex) {

			ex.printStackTrace();

			}

			
		}

	}
}
