package cn.sion.csm.service;

import java.util.List;

import cn.sion.csm.model.User;

public interface UserService {

	/**
	 * 实现插入一条新用户信息数据
	 * @param user
	 * @return
	 */
	public int save(User user);
	
	/**
	 * 根据用户id修改对应用户信息数据
	 * @param id
	 * @return
	 */
	public int deleteUserById(int id);
	
	/**
	 * 根据用户id修改对应用户信息数据
	 * @param id
	 * @return
	 */
	public int updateUserById(User user);
	
	/**
	 * 根据用户名获取一条用户数据，封装成类User的一个对象,不支持事务
	 * @param username
	 * @return
	 */
	public User get(int id);
	
	/**
	 * 根据用户名获取一条用户数据，封装成类User的一个对象,支持事务
	 * @param conn
	 * @param username
	 * @return
	 */
	public User getTransaction(String username);
	
	/**
	 * 获取所有用户数据
	 * @return
	 */
	public List<User> getlistAll();
	
	/**
	 * 查询指定用户名的用户有多少条
	 * @param username
	 * @return
	 */
	public long getCountByName(String username);
	
	/**
	 * 这是用户模糊查询的方法
	 * @param username
	 * @param password
	 * @param phone
	 * @return
	 */
	public List<User> query(String username, String password, String phone);

	/**
	 * 这是登陆时判断用户名密码正确性的方法
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);

	public User getUserByName(String username);
}
