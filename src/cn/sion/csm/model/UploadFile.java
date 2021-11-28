package cn.sion.csm.model;

import java.util.Date;

public class UploadFile {
	
	private int id;
	private String fileName;
	private String fileType;
	private String fileSize;
	private String savePath;
	private Date saveTime;
	private String saveName;
	private String writer;
	private String belong;
	
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public Date getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}
	
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public UploadFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UploadFile [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", fileSize=" + fileSize
				+ ", savePath=" + savePath + ", saveTime=" + saveTime + ", saveName=" + saveName + ", writer=" + writer
				+ ", belong=" + belong + "]";
	}
	
	

	
}
