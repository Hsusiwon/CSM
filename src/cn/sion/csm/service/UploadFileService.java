package cn.sion.csm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sion.csm.model.UploadFile;

public interface UploadFileService {

	public void addFileInfo(UploadFile uploadFile);
	
	public List<UploadFile> getUploadFiles();
	
	public List<UploadFile> getUploadFilesByPage(String page,String limit);
	
	public List<UploadFile> getUploadFilesByStitle(String stitle);
	
	public List<UploadFile> getUploadFilesBySP(String page,String limit,String stitle);

	public void deleteUploadFile(int id);
	
	//保存文件的方法
	public void saveFile(HttpServletRequest req,HttpServletResponse resp);
	
	public void writeFile(HttpServletRequest req,HttpServletResponse resp);
	
	public UploadFile getUploadFileById(int id);

	public long countUploadFilesByBelong(String belong);

	public List<UploadFile> getUploadFilesByBelong(String page,String limit,String belong);

	public List<UploadFile> getUploadFilesByBelongwx(String belong);
	

	public void wxsaveFile(HttpServletRequest req,HttpServletResponse resp);

}
