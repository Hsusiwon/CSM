package cn.sion.csm.listener;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import cn.sion.csm.utils.UploadFilePropertiesUtil;

@WebListener
public class FileUploadPropertiesInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//Web服务器启动的时候执行
		InputStream in = getClass().getClassLoader().getResourceAsStream("uploadfile.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			for(Object o:properties.keySet()) {
				String key=(String)o;
				String value=properties.getProperty(key);
				UploadFilePropertiesUtil.getInstance().addProperty(key, value);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
