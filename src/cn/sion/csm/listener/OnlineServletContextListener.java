package cn.sion.csm.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.swing.Timer;

import cn.sion.csm.model.Online;
import cn.sion.csm.service.FactoryService;
import cn.sion.csm.service.OnlineService;

/**
 * 2����¼�����û���ʱ�򣬷��ʹ�����ʱ�䣬10���ӣ�����û�10����û�в������û����ߣ�ɾ���������ݱ������߼�¼��
 * ServletContextListenser��webӦ��������ʱ��Ҫ��ÿ5���Ӽ�飬�����û�������ִ��ɾ��
 * @author Sion
 *
 */
@WebListener
public class OnlineServletContextListener implements ServletContextListener {

	/**
	 * ��10���ӣ������߳������õ�ʱ�����û���κβ�������Ϊ����
	 */
	public  final int MAX_MILLTIS = 10;
	
	OnlineService onlineService = FactoryService.getOnlineService();
	
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
	public void contextInitialized(ServletContextEvent sce) {
		//��Ź�ʱ�ķ�������Ϣ
		List<Online> expiresUserList = new ArrayList<>();
		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();
		
		//������������ʱ��ͱ�ִ��,����һ����ʱ��
		new Timer(5*1000,new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent event) {
				List<Online> list = onlineService.getAllOnline();
				if(list!=null && list.size()>0) {
					for(Online ol:list) {
						dateOne.setTime(new Date());
						dateTwo.setTime(ol.getTime());
						long difTime = (dateOne.getTimeInMillis()-dateTwo.getTimeInMillis())/(1000*60);
//						if((System.currentTimeMillis()-Long.parseLong(ol.getTime().toString()))>MAX_MILLTIS) {
//							expiresUserList.add(ol);
//						}
						if(difTime>MAX_MILLTIS) {
							expiresUserList.add(ol);
						}					
					}
					//�����ݿ���ɾ������ʱ�ķ�������Ϣ
					onlineService.deleteExpiresOnline(expiresUserList);
					//���ݿ���ɾ����ʵ�ķ�������Ϣ
					expiresUserList.clear();
				}
			}
		}).start();
		
		
	}

}
