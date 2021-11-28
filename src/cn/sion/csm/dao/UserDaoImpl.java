package cn.sion.csm.dao;

import java.sql.Connection;
import java.util.List;

import cn.sion.csm.model.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public int save(User user) {
		String sql = "insert into `users`(`username`,`password`,`phone`,`userlevel`,`regdate`) values(?,?,?,?,?);";
		return super.update(sql, user.getUsername(),user.getPassword(),user.getPhone(),user.getUserlevel(),user.getRegdate());
	}

	@Override
	public int deleteUserById(int id) {
		String sql = "delete from `users` where `id`=?;";
		return super.update(sql, id);
	}

	@Override
	public int updateUserById(User user) {
		String sql = "update `users` set `username`=?,`password`=?,`phone`=? where `id`=?;";
		return super.update(sql, user.getUsername(),user.getPassword(),user.getPhone(),user.getId());
	}

	@Override
	public User get(int id) {
		String sql = "select `id`,`username`,`password`,`phone`,`userlevel`,`regdate` from `users` where `id`=?;";
		return super.get(sql, id);	
	}
	
	@Override
	public User get(Connection conn, String username) {
		String sql = "select `id`,`username`,`password`,`phone`,`userlevel`,`regdate` from `users` where `username`=?;";
		return super.get(conn,sql,username);
	}
	

	@Override
	public List<User> getlistAll() {
		String sql = "select `id`,`username`,`password`,`phone`,`userlevel`,`regdate` from users;";
		return super.getlist(sql);
	}

	@Override
	public long getCountByName(String username) {
		String sql = "select count(`id`) from `users` where `username`=?;";
		return (long) super.getValue(sql, username);
	}

	@Override
	public List<User> query(String username, String password, String phone) {
		String sql = "select `id`,`username`,`password`,`phone`,`userlevel`,`regdate` from users where 1=1";
		if(username!=null && !"".equals(username)) {
			sql = sql+ " and username like '%"+username+"%'";//sql×¢Èë¹¥»÷µÄ·çÏÕ
		}
		if(password!=null && !"".equals(password)) {
			sql = sql+ " and password like '%"+password+"%'";
		}
		if(phone!=null && !"".equals(phone)) {
			sql = sql+ " and phone like '%"+phone+"%'";
		}
		return super.getlist(sql);
	}

	@Override
	public User getUserByUp(String username, String password) {
		String sql = "select `id`,`username`,`password`,`phone`,`userlevel`,`regdate` from users where `username`=? and `password`=?";
		return super.get(sql, username,password);
	}

	@Override
	public User getUserByName(String username) {
		String sql = "select `id`,`username`,`password`,`phone`,`userlevel`,`regdate` from users where `username`=?";
		return super.get(sql, username);
	}

}
