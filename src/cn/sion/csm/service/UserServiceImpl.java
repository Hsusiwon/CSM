package cn.sion.csm.service;

import java.sql.Connection;
import java.util.List;

import cn.sion.csm.dao.FactoryDao;
import cn.sion.csm.dao.UserDao;
import cn.sion.csm.model.User;
import cn.sion.csm.utils.JdbcUtils;

public class UserServiceImpl implements UserService {

	UserDao userDao = FactoryDao.getUserDao();
	
	public int save(User user) {
		return userDao.save(user);
	}

	@Override
	public int deleteUserById(int id) {
		return userDao.deleteUserById(id);
	}

	@Override
	public int updateUserById(User user) {
		return userDao.updateUserById(user);
	}

	@Override
	public User get(int id) {
		return userDao.get(id);
	}

	@Override
	public User getTransaction(String username) {
		Connection conn = null;
		User user = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);//开始事务
			user = userDao.get(conn, username);
			conn.commit();
		} catch (Exception e) {
			JdbcUtils.rollbackTransaction(conn);//回滚事务
		}
		return user;
	}

	@Override
	public List<User> getlistAll() {
		return userDao.getlistAll();
	}

	@Override
	public long getCountByName(String username) {
		return userDao.getCountByName(username);
	}

	@Override
	public List<User> query(String username, String password, String phone) {
		return userDao.query(username,password,phone);
	}

	@Override
	public User login(String username, String password) {
		return userDao.getUserByUp(username,password);
	}

	@Override
	public User getUserByName(String username) {
		return userDao.getUserByName(username);
	}
	


}
