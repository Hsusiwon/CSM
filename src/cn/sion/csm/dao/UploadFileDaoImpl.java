package cn.sion.csm.dao;

import java.util.List;

import cn.sion.csm.model.UploadFile;

public class UploadFileDaoImpl extends BaseDao<UploadFile> implements UploadFileDao {

	@Override
	public void addFileInfo(UploadFile uploadFile) {
		String sql="insert into `uploadfiles`(`file_name`,`file_type`,`file_size`,`save_path`,`save_time`,`save_name`,`writer`,`belong`) values(?,?,?,?,?,?,?,?)";
		super.update(sql, uploadFile.getFileName(),uploadFile.getFileType(),uploadFile.getFileSize(),uploadFile.getSavePath(),uploadFile.getSaveTime(),uploadFile.getSaveName(),uploadFile.getWriter(),uploadFile.getBelong());
	}

	@Override
	public List<UploadFile> getUploadFiles() {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles`";
		return super.getlist(sql);
	}
	
	@Override
	public List<UploadFile> getUploadFilesByPage(String page,String limit) {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles` limit "+((Integer.valueOf(page) - 1)*Integer.valueOf(limit))+","+limit+" ;";
		return super.getlist(sql);
	}
	
	@Override
	public List<UploadFile> getUploadFilesByStitle(String stitle) {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles` where `file_name` regexp ?";
		return super.getlist(sql,stitle);
	}
	
	@Override
	public List<UploadFile> getUploadFilesBySP(String page,String limit,String stitle) {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles` where `file_name` regexp ? limit "+((Integer.valueOf(page) - 1)*Integer.valueOf(limit))+","+limit+" ;";
		return super.getlist(sql,stitle);
	}

	@Override
	public void deleteUploadFile(int id) {
		String sql="delete from `uploadfiles` where id=?";
		super.update(sql,id);
		
	}

	@Override
	public UploadFile get(int id) {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles` where id=?";
		return super.get(sql,id);
	}

	@Override
	public long countUploadFilesByBelong(String belong) {
		String sql="select count(*) from `uploadfiles` where `belong`=?";
		return  (long) super.getValue(sql,belong);
	}

	@Override
	public List<UploadFile> getUploadFilesByBelong(String page,String limit,String belong) {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles` where `belong`=? limit "+((Integer.valueOf(page) - 1)*Integer.valueOf(limit))+","+limit+" ;";
		return super.getlist(sql,belong);
	}

	@Override
	public List<UploadFile> getUploadFilesByBelongwx(String belong) {
		String sql="select `id`,`file_name` fileName,`file_type` fileType,`file_size` fileSize,`save_path` savePath,`save_time` saveTime,`save_name` saveName,`writer`,`belong` from `uploadfiles` where `belong`=?";
		return super.getlist(sql,belong);
	}
	
}
