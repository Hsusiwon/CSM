package cn.sion.csm.service;

import java.util.List;

import cn.sion.csm.model.Online;

public interface OnlineService {

	/**
	 * ȡ���������߷�������Ϣ
	 * @return
	 */
	public List<Online> getAllOnline();
	
	/**
	 * ����һ��online��Ϣ
	 * @param online
	 */
	public void insertOnline(Online online);
	
	/**
	 * ���±����online��Ϣ
	 * @param online
	 */
	public void updateOnline(Online online);
	
	/**
	 * ����ssidɾ�����ߵ��û���online��Ϣ
	 * @param ssid
	 */
	public void deleteExpiresOnline(List<Online> list);
	
	/**
	 * ����ssid��ȡһ��online��Ϣ
	 * @param ssid
	 * @return
	 */
	public Online getOnlineBySsid(String ssid);
}
