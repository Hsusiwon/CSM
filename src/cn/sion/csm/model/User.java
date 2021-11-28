package cn.sion.csm.model;

import java.util.Date;

//”√ªß¿‡
	
public class User {
	private int id;
	private String username;
	private String password;
	private String phone;
	private String userlevel;
	private Date regdate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserlevel() {
		return userlevel;
	}
	public void setUserlevel(String userlevel) {
		this.userlevel = userlevel;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String username, String password, String phone, String userlevel, Date regdate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.userlevel = userlevel;
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", userlevel=" + userlevel + ", regdate=" + regdate + "]";
	}
	
	
	
	
}
