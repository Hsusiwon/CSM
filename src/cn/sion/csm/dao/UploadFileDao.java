package cn.sion.csm.dao;

import java.util.List;

import cn.sion.csm.model.UploadFile;

public interface UploadFileDao {
	//鏂板
	public void addFileInfo(UploadFile uploadFile);
	
	//鑾峰彇鍒版墍鏈変笂浼犲埌鏈嶅姟鍣ㄤ笂鐨勬枃浠剁殑淇℃伅鍒楄〃
	public List<UploadFile> getUploadFiles();
	
	public List<UploadFile> getUploadFilesByPage(String page,String limit);
	
	public List<UploadFile> getUploadFilesByStitle(String stitle);
	
	public List<UploadFile> getUploadFilesBySP(String page,String limit,String stitle);
	
	//鍒犻櫎
	public void deleteUploadFile(int id);

	//查询单个上传的文件的信息
	public UploadFile get(int id);

	public long countUploadFilesByBelong(String belong);

	public List<UploadFile> getUploadFilesByBelong(String page,String limit,String belong);
	
	public List<UploadFile> getUploadFilesByBelongwx(String belong);
	

}
