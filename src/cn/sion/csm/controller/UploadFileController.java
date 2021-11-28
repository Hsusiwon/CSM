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
		//����һ���������ܴ�����ɾ�Ĳ����еĹ���
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
		//������ҳ���������ļ���Ϣ���ļ�����������Ϣ
		//������յ����ļ��Ĺ��������ڿ��Ʋ�ʵ�֣�ת����service��ʵ�ֱ����ļ�
		try {
			ufs.wxsaveFile(req, resp);
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("data", "�ϴ��ɹ���");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}catch (Exception e) {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("data", "�ϴ�ʧ�ܣ�");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
	}
	
	protected void upload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������ҳ���������ļ���Ϣ���ļ�����������Ϣ
		//������յ����ļ��Ĺ��������ڿ��Ʋ�ʵ�֣�ת����service��ʵ�ֱ����ļ�
		try {
			ufs.saveFile(req, resp);
			//����û��ץ���쳣��֤���ϴ��ļ��ɹ�
			req.setAttribute("noteMsg1", "�ļ��ϴ��ɹ�");
			req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
		} catch (Exception e) {
			//�÷����ȥʵ�ֱ����ļ�����ҵ����߼����ܴ��룬�����ļ����꣬�ܵ��ļ����ޣ���ֹ�ϴ����ļ�����,��jspҳ����ʾ����
			//����������ȡ���쳣��Ϣ��ע��jspҳ�沢��ʾ
			//System.out.println("controller's error:"+e.getMessage());
			req.setAttribute("noteMsg1", e.getMessage());
			req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
		}
	}
	
	protected void write(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������ҳ���������ļ���Ϣ���ļ�����������Ϣ
				//������յ����ļ��Ĺ��������ڿ��Ʋ�ʵ�֣�ת����service��ʵ�ֱ����ļ�
				try {
					ufs.writeFile(req, resp);
					//����û��ץ���쳣��֤���ϴ��ļ��ɹ�
					req.setAttribute("noteMsg2", "�ļ��ϴ��ɹ�");
					req.getRequestDispatcher("/fileupload.jsp").forward(req, resp);
				} catch (Exception e) {
					//�÷����ȥʵ�ֱ����ļ�����ҵ����߼����ܴ��룬�����ļ����꣬�ܵ��ļ����ޣ���ֹ�ϴ����ļ�����,��jspҳ����ʾ����
					//����������ȡ���쳣��Ϣ��ע��jspҳ�沢��ʾ
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
			//����JSON
			JSONObject jsonMain = new JSONObject();
			//item:[{}] ����
			JSONArray array = JSONArray.fromObject(fileListPage);
			//Ϊitem ���ֵ
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
			//����JSON
			JSONObject jsonMain = new JSONObject();
			//item:[{}] ����
			JSONArray array = JSONArray.fromObject(fileListPage);
			//Ϊitem ���ֵ
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
		//����JSON
		JSONObject jsonMain = new JSONObject();
		//item:[{}] ����
		JSONArray array = JSONArray.fromObject(fileList);
		//Ϊitem ���ֵ
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
		//1.��ȡҪ�����ļ��ľ���·�����ļ���
		int id = Integer.parseInt(req.getParameter("id"));

		UploadFile uf = ufs.getUploadFileById(id);
		String filePath = uf.getSavePath()+"/"+uf.getSaveName();//ע�⣺windows"\\"��linux"/"·������һ��
		String fileName = uf.getFileName();

		//2.���IE������IEΪ�ں˵������������Ҫ�ǽ�����ص�ʱ���ļ�������������
		String userAgent = req.getHeader("user-Agent");
		if(userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			fileName=java.net.URLEncoder.encode(fileName,"UTF-8");
		}else {
			//��IE������Ĵ���
			fileName=new String(fileName.getBytes("utf-8"),"ISO-8859-1");
		}
		//3.����content-disposition��Ӧͷ��������������صķ�ʽ���ļ�
		resp.setHeader("content-disposition", "attachment;filename="+fileName);
		//4.��ȡҪ�����ļ���������
		InputStream in = new FileInputStream(filePath);
		//5.����д������
		int len = 0;
		byte[] buffer = new byte[1024];
		//6.ͨ��response�����ȡOutPutStream���������
		OutputStream os = resp.getOutputStream();
		//7.��FileInputStream����д��buffer������
		while((len=in.read(buffer))>0) {
			os.write(buffer,0,len);
		}
		//8.�ر���
		in.close();
		os.close();
	}
	
	@SuppressWarnings("unused")
	private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.��ȡҪ�����ļ��ľ���·�����ļ���
		int id = Integer.parseInt(req.getParameter("id"));
		
		UploadFile uf = ufs.getUploadFileById(id);
		String filePath = uf.getSavePath()+"/"+uf.getSaveName();//ע�⣺windows"\\"��linux"/"·������һ��
		String fileName = uf.getFileName();
		//2.ת���ļ�Ϊpdf�ļ�	
		String path=uf.getSavePath()+"/";//ע�⣺windows"\\"��linux"/"·������һ��
		
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
