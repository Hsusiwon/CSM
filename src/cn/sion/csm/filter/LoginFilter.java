package cn.sion.csm.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.sion.csm.model.Online;
import cn.sion.csm.model.User;
import cn.sion.csm.service.FactoryService;
import cn.sion.csm.service.OnlineService;
import cn.sion.csm.service.UserService;
import cn.sion.csm.utils.CookieUtils;

public class LoginFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		String path = req.getServletPath().substring(1);

		UserService userService = FactoryService.getUserService();
		OnlineService onlineService = FactoryService.getOnlineService();
		
		if(path.equals("login.jsp")&&!path.contains(".css")&&!path.contains("layui.js")&&!path.contains(".png")&&!path.contains(".jpg")&&!path.contains(".jpeg")&&!path.contains(".iso")) {	

			Cookie[] cookies = req.getCookies();
			if(cookies != null && cookies.length>0) {
				String username = null;
				String ssid = null;
				for(Cookie cookie:cookies) {
					if(cookie.getName().equals("userKey")) {
						username = cookie.getValue();
					}
					if(cookie.getName().equals("ssid")) {
						ssid = cookie.getValue();
					}
				}
				
				if(username != null && ssid != null && ssid.equals(CookieUtils.md5Encrypt(username))) {//true,֤���û���½��������ѡ���˼�ס�ң������Զ���¼
					HttpSession session = req.getSession();
					session.setAttribute("username", username);
					//cookie��¼ʱ��ȡ���û���Ϣ�������û������û������
					User user = userService.getUserByName(username);
					//String userlevel = user.getUserlevel();
					session.setAttribute("user", user);
					//3�������ߵ�¼�ɹ��󣬽�online���ݱ������username�����ο͸�Ϊ�������û���
					//������״̬online���username���ο�״̬��Ϊ������username
					Online ol = onlineService.getOnlineBySsid(session.getId());
					if(ol!=null) {
						ol.setUsername(username);
						onlineService.updateOnline(ol);
					}
					resp.sendRedirect(req.getContextPath()+"/index.jsp");//������ʵ�����Զ���¼
				}else {
					chain.doFilter(req, resp);//����
				}
			}else {
				chain.doFilter(req, resp);//����
			}
			
		}else if(!path.equals("login.jsp")&&!path.contains(".css")&&!path.contains("layui.js")&&!path.contains(".png")&&!path.contains(".jpg")&&!path.contains(".jpeg")&&!path.contains(".iso")) {

			String autho = getFilterConfig().getInitParameter("authority");
			String noautho = getFilterConfig().getInitParameter("noautho");
			String[] strArr = autho.split(",");
			String[] noauthoArr = noautho.split(",");
			for(String str:noauthoArr) {
				if(str.equals(path)) {
					chain.doFilter(req, resp);
				}
			}
				
			HttpSession session = req.getSession();
			for(String str:strArr) {
				if(str.equals(path)) {
					String username = (String)session.getAttribute("username");
					if(username != null && username != "") {
						
						chain.doFilter(req, resp);
			            
					}else {
						resp.sendRedirect(req.getContextPath()+"/login.jsp");
					}
				}
			}
		}else if(path.contains(".css") || path.contains("layui.js") || path.contains(".png")|| path.contains(".jpg")|| path.contains(".jpeg")|| path.contains(".ico")) {

			chain.doFilter(req, resp);
		}
	}
}
