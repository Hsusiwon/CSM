package cn.sion.csm.dao;

import java.sql.Connection;
import java.util.List;

import cn.sion.csm.model.User;

/**
 * �ӿ�ָ������ֻ���巽������ȥʵ�֣�UserDao;������users���ݱ��й�ϵ�Ĳ�������
 * @author Sion
 *
 */
public interface UserDao {
	
	/**
	 * ʵ�ֲ���һ�����û���Ϣ����
	 * @param user
	 * @return
	 */
	public int save(User user);
	
	/**
	 * �����û�id�޸Ķ�Ӧ�û���Ϣ����
	 * @param id
	 * @return
	 */
	public int deleteUserById(int id);
	
	/**
	 * �����û�id�޸Ķ�Ӧ�û���Ϣ����
	 * @param id
	 * @return
	 */
	public int updateUserById(User user);
	
	/**
	 * �����û�����ȡһ���û����ݣ���װ����User��һ������,��֧������
	 * @param username
	 * @return
	 */
	public User get(int id);
	
	/**
	 * �����û�����ȡһ���û����ݣ���װ����User��һ������,֧������
	 * @param conn
	 * @param username
	 * @return
	 */
	public User get(Connection conn,String username);
	
	/**
	 * ��ȡ�����û�����
	 * @return
	 */
	public List<User> getlistAll();
	
	/**
	 * ��ѯָ���û������û��ж�����
	 * @param username
	 * @return
	 */
	public long getCountByName(String username);

	/**
	 * Dao��ʵ��ģ����ѯ�ķ���
	 * @param username
	 * @param password
	 * @param phone
	 * @return
	 */
	public List<User> query(String username, String password, String phone);

	/**
	 * Daoʵ�ָ����û����������ѯ�û���¼�ķ���
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserByUp(String username, String password);

	public User getUserByName(String username);
}
