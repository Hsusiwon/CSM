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
 * 基本思路：
 * 1、当请求进来的时候，记录访问者的信息，判断访问者，假如在数据库中存在访问者就更新访问时间和访问者页面，否则插入访问者到数据库；
 * 2、记录在线用户的时候，访问过来的时间，10分钟，如果用户10分钟没有操作，用户离线，删除其在数据表中在线记录；
 * 3、访问者登录成功后，将online数据表里面的username，从游客改为真正的用户名
 * ServletContextListenser，web应用启动的时候，要做每5秒钟检查，过期用户，并且执行删除
 * @author Sion
 *
 */
@WebListener
public class OnlineRequestListener implements ServletRequestListener, ServletRequestAttributeListener {

	//Service对象，请求操作数据库
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
		//1.通过请求拿到访问者的信息
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session = request.getSession();	
		String ssid = session.getId();
		String ip = request.getRemoteAddr();
		String page = request.getRequestURI();
		String username = (String) session.getAttribute("username");
		username = username==null?"游客":username;
		//操作数据库之前，把这些信息封装到一个online对象
		Online ol = new Online();
		ol.setSsid(ssid);
		ol.setIp(ip);
		ol.setPage(page);
		ol.setUsername(username);
		ol.setTime(new Date());
		//连接数据库，把信息放到数据库，有则更新time，没有则插入
		//1.更具ssid，查询数据库有没有记录
		Online online = onlineService.getOnlineBySsid(ssid);
		if(online!=null) {
			//2.更新这条记录的time
			online.setTime(new Date());
			online.setPage(page);
			onlineService.updateOnline(online);
		}else {
			onlineService.insertOnline(ol);
		}
	}

 

}
