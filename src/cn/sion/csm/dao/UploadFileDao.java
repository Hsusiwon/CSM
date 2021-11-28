package cn.sion.csm.dao;

import java.util.List;

import cn.sion.csm.model.UploadFile;

public interface UploadFileDao {
	//新增
	public void addFileInfo(UploadFile uploadFile);
	
	//获取到所有上传到服务器上的文件的信息列表
	public List<UploadFile> getUploadFiles();
	
	public List<UploadFile> getUploadFilesByPage(String page,String limit);
	
	public List<UploadFile> getUploadFilesByStitle(String stitle);
	
	public List<UploadFile> getUploadFilesBySP(String page,String limit,String stitle);
	
	//删除
	public void deleteUploadFile(int id);

	//��ѯ�����ϴ����ļ�����Ϣ
	public UploadFile get(int id);

	public long countUploadFilesByBelong(String belong);

	public List<UploadFile> getUploadFilesByBelong(String page,String limit,String belong);
	
	public List<UploadFile> getUploadFilesByBelongwx(String belong);
	

}
