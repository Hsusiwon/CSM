package cn.sion.csm.model;

import java.util.Date;

public class Problem {

	private int serial;
	private int serialnum;
	private String title;
	private String type;
	private String level;
	private String repaired;
	private Date recordtime;
	private String recorder;
	private String recordcont;
	private String recordpic;
	private Date updatetime;
	private String updater;
	private String updatecont;
	private String updatepic;
	

	public int getSerial() {
		return serial;
	}


	public void setSerial(int serial) {
		this.serial = serial;
	}


	public int getSerialnum() {
		return serialnum;
	}


	public void setSerialnum(int serialnum) {
		this.serialnum = serialnum;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getRepaired() {
		return repaired;
	}


	public void setRepaired(String repaired) {
		this.repaired = repaired;
	}


	public Date getRecordtime() {
		return recordtime;
	}


	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}


	public String getRecorder() {
		return recorder;
	}


	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}


	public String getRecordcont() {
		return recordcont;
	}


	public void setRecordcont(String recordcont) {
		this.recordcont = recordcont;
	}


	public String getRecordpic() {
		return recordpic;
	}


	public void setRecordpic(String recordpic) {
		this.recordpic = recordpic;
	}


	public Date getUpdatetime() {
		return updatetime;
	}


	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}


	public String getUpdater() {
		return updater;
	}


	public void setUpdater(String updater) {
		this.updater = updater;
	}


	public String getUpdatecont() {
		return updatecont;
	}


	public void setUpdatecont(String updatecont) {
		this.updatecont = updatecont;
	}


	public String getUpdatepic() {
		return updatepic;
	}


	public void setUpdatepic(String updatepic) {
		this.updatepic = updatepic;
	}


	public Problem() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Problem [serial=" + serial + ", serialnum=" + serialnum + ", title=" + title + ", type=" + type
				+ ", level=" + level + ", repaired=" + repaired + ", recordtime=" + recordtime + ", recorder="
				+ recorder + ", recordcont=" + recordcont + ", recordpic=" + recordpic + ", updatetime=" + updatetime
				+ ", updater=" + updater + ", updatecont=" + updatecont + ", updatepic=" + updatepic + "]";
	}
	

	
	

}
