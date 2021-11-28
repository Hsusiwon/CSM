package cn.sion.csm.service;

import java.util.List;

import cn.sion.csm.model.User;

public interface UserService {

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
	public User getTransaction(String username);
	
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
	 * �����û�ģ����ѯ�ķ���
	 * @param username
	 * @param password
	 * @param phone
	 * @return
	 */
	public List<User> query(String username, String password, String phone);

	/**
	 * ���ǵ�½ʱ�ж��û���������ȷ�Եķ���
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);

	public User getUserByName(String username);
}
