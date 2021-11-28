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
 * 2、记录在线用户的时候，访问过来的时间，10分钟，如果用户10分钟没有操作，用户离线，删除其在数据表中在线记录；
 * ServletContextListenser，web应用启动的时候，要做每5秒钟检查，过期用户，并且执行删除
 * @author Sion
 *
 */
@WebListener
public class OnlineServletContextListener implements ServletContextListener {

	/**
	 * 是10分钟，访问者超过设置的时间毫秒没有任何操作就认为离线
	 */
	public  final int MAX_MILLTIS = 10;
	
	OnlineService onlineService = FactoryService.getOnlineService();
	
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
	public void contextInitialized(ServletContextEvent sce) {
		//存放过时的访问者信息
		List<Online> expiresUserList = new ArrayList<>();
		Calendar dateOne = Calendar.getInstance();
		Calendar dateTwo = Calendar.getInstance();
		
		//服务器启动的时候就被执行,启动一个定时器
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
					//从数据库中删除掉过时的访问者信息
					onlineService.deleteExpiresOnline(expiresUserList);
					//数据库中删除果实的访问者信息
					expiresUserList.clear();
				}
			}
		}).start();
		
		
	}

}
