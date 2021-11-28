package cn.sion.csm.listener;

import java.util.Date;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.sion.csm.model.Online;
import cn.sion.csm.service.FactoryService;
import cn.sion.csm.service.OnlineService;

/**
 * ����˼·��
 * 1�������������ʱ�򣬼�¼�����ߵ���Ϣ���жϷ����ߣ����������ݿ��д��ڷ����߾͸��·���ʱ��ͷ�����ҳ�棬�����������ߵ����ݿ⣻
 * 2����¼�����û���ʱ�򣬷��ʹ�����ʱ�䣬10���ӣ�����û�10����û�в������û����ߣ�ɾ���������ݱ������߼�¼��
 * 3�������ߵ�¼�ɹ��󣬽�online���ݱ������username�����ο͸�Ϊ�������û���
 * ServletContextListenser��webӦ��������ʱ��Ҫ��ÿ5���Ӽ�飬�����û�������ִ��ɾ��
 * @author Sion
 *
 */
@WebListener
public class OnlineRequestListener implements ServletRequestListener, ServletRequestAttributeListener {

	//Service��������������ݿ�
	OnlineService onlineService = FactoryService.getOnlineService();
	
	@Override
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		//1.ͨ�������õ������ߵ���Ϣ
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session = request.getSession();	
		String ssid = session.getId();
		String ip = request.getRemoteAddr();
		String page = request.getRequestURI();
		String username = (String) session.getAttribute("username");
		username = username==null?"�ο�":username;
		//�������ݿ�֮ǰ������Щ��Ϣ��װ��һ��online����
		Online ol = new Online();
		ol.setSsid(ssid);
		ol.setIp(ip);
		ol.setPage(page);
		ol.setUsername(username);
		ol.setTime(new Date());
		//�������ݿ⣬����Ϣ�ŵ����ݿ⣬�������time��û�������
		//1.����ssid����ѯ���ݿ���û�м�¼
		Online online = onlineService.getOnlineBySsid(ssid);
		if(online!=null) {
			//2.����������¼��time
			online.setTime(new Date());
			online.setPage(page);
			onlineService.updateOnline(online);
		}else {
			onlineService.insertOnline(ol);
		}
	}

 

}
