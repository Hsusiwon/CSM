package cn.sion.csm.dao;

import java.sql.Connection;
import java.util.List;

import cn.sion.csm.model.User;

/**
 * 接口指定规则，只定义方法，不去实现，UserDao;定义与users数据表有关系的操作方法
 * @author Sion
 *
 */
public interface UserDao {
	
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
	public User get(Connection conn,String username);
	
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
	 * Dao里实现模糊查询的方法
	 * @param username
	 * @param password
	 * @param phone
	 * @return
	 */
	public List<User> query(String username, String password, String phone);

	/**
	 * Dao实现根据用户名和密码查询用户记录的方法
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserByUp(String username, String password);

	public User getUserByName(String username);
}
