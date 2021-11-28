package cn.sion.csm.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.sion.csm.model.Online;
import cn.sion.csm.model.User;
import cn.sion.csm.service.FactoryService;
import cn.sion.csm.service.OnlineService;
import cn.sion.csm.service.UserService;
import cn.sion.csm.utils.CookieUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet(urlPatterns = {"*.udo"})
public class UserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UserService userService = FactoryService.getUserService();
	
	OnlineService onlineService = FactoryService.getOnlineService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//����һ���������ܴ�����ɾ�Ĳ����еĹ���
		String mt = req.getServletPath();
		mt = mt.substring(1);
		mt = mt.substring(0, mt.length()-4);
		try {
			Method method = this.getClass().getDeclaredMethod(mt, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unused")
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		username = username.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		password = password.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		phone = phone.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		
		user.setUserlevel("2");
		user.setRegdate(new Date());
		
		long count = userService.getCountByName(user.getUsername());
		if(count>0) {//�ܴ����ݿ����ҵ�ͬ����¼��֤���������г�ͻ�����������
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","�����г�ͻ�����������룡");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
			return;
		}
		int rows = userService.save(user);
		if(rows>0) {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","ע��ɹ���");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","ע��ʧ�ܣ�");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
		
	}
	
	@SuppressWarnings("unused")
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		int rows = userService.deleteUserById(id);
		if(rows>0) {
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
		} else {
			//throw new RuntimeException("ע��ʧ�ܣ�");
			resp.sendRedirect(req.getContextPath()+"/error.jsp");
		}
	}
	
	@SuppressWarnings("unused")
	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		User user = userService.get(id);
		req.setAttribute("user", user);
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
		
	}
	
	@SuppressWarnings("unused")
	private void editsub(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		//ͨ��id����ȡ��Ҫ���޸�id���û���Ϣ
		User oriUser = userService.get(id);
		
		String oriUsername = oriUser.getUsername();
		String newUsername = req.getParameter("username");
		newUsername = newUsername.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		long count = userService.getCountByName(newUsername);
		if(!newUsername.equals(oriUsername) && count>0) {//�����ֺ�ԭ�������ֲ�һ�������ܴ����ݿ����ҵ�ͬ����¼��֤���������г�ͻ���������޸�
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","�����г�ͻ�����������룡");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
			return;
		}
		
		oriUser.setUsername(newUsername);
		String newPassword = req.getParameter("password");
		newPassword = newPassword.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		oriUser.setPassword(newPassword);
		String phone = req.getParameter("phone");
		phone = phone.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		oriUser.setPhone(phone);
		int rows = userService.updateUserById(oriUser);
		if(rows>0) {
			req.getSession().setAttribute("user", oriUser);
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","�����û���Ϣ�ɹ���");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","�����û���Ϣʧ�ܣ�");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}
	}
	
	@SuppressWarnings("unused")
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String username = req.getParameter("username");
//		String password = req.getParameter("password");
//		String phone = req.getParameter("phone");
//		username = username.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
//		password = password.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
//		phone = phone.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");

		List<User> list = userService.getlistAll();
		req.setAttribute("userList", list);//�ѽ�����ŵ�req�����Կռ�
		req.getRequestDispatcher("/queryallusers.jsp").forward(req, resp);
		
	}
	
	@SuppressWarnings("unused")
	private void wxlogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userService.login(username,password);
		JSONArray array = JSONArray.fromObject(user);
		resp.getWriter().print(array);
		resp.getWriter().flush();
		resp.getWriter().close();
		
	}
	
	@SuppressWarnings("unused")
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��һ���E���õ���������
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String expiredays = req.getParameter("expiredays");
		username = username.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		password = password.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		
		Cookie[] cookies = req.getCookies();
		
		boolean login = false;//�Ƿ��䛵Ę�ӛ��true�ѽ���ꑣ�false��δ���
		String account = null;//����~̖
		String ssid = null;//����һ����ǣ�ͨ��cookie�õ���һ���ж��û��ò��óɹ���¼�ı��
		
		if(cookies != null && cookies.length>0) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("userKey")) {
					account = cookie.getValue();
				}
				if(cookie.getName().equals("ssid")) {
					ssid = cookie.getValue();
				}
			}
		}
		
		if(account != null && ssid != null) {
			login = ssid.equals(CookieUtils.md5Encrypt(username));
		}
		
		if(!login) {//login:falseȡ��true�������û���δ��¼
			//�û��״η���
			User user = userService.login(username,password);//ͨ���������ݿ����ж��û�����������ȷ��
			if(user != null) {//��ȷ����¼
				expiredays = expiredays==null?"":expiredays;
				switch (expiredays) {
				case "7":
					//����һ��cookie��������cookie�����ʧЧʱ����7��
					CookieUtils.createCookie(username, req, resp, 7*24*60*60);
					break;
				case "30":
					//����һ��cookie��������cookie�����ʧЧʱ����30��
					CookieUtils.createCookie(username, req, resp, 30*24*60*60);
					break;
				case "100":
					//����һ��cookie��������cookie�����ʧЧʱ��������
					CookieUtils.createCookie(username, req, resp, Integer.MAX_VALUE);
					break;
				default:
					//����һ��cookie��������cookie�����ʧЧʱ��-1,�ر������cookieʧЧ
					CookieUtils.createCookie(username, req, resp, -1);
					break;
				}
				//�״ε�¼�ɹ���׼���ȥ�����棬���û������û������
				req.getSession().setAttribute("useruame", username);
				req.getSession().setAttribute("user", user);
				//3�������ߵ�¼�ɹ��󣬽�online���ݱ������username�����ο͸�Ϊ�������û���
				//������״̬online���username���ο�״̬��Ϊ������username
				HttpSession session = req.getSession();
				Online ol = onlineService.getOnlineBySsid(session.getId());
				if(ol!=null) {
					ol.setUsername(username);
					onlineService.updateOnline(ol);
				}
				//��¼�ɹ���׼�������ҳ
				resp.sendRedirect(req.getContextPath()+"/index.jsp");
			} else {
				req.setAttribute("note", "�û������������");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}  
		//login:true������cookie��¼��LoginFilter��ִ���ض�������ҳ

	}
	
	@SuppressWarnings("unused")
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��¼��½״̬��cookieɾ��
		Cookie[] cookies = req.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("userKey")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);//��cookieʧЧ��ʶд���ͻ���cookie
				}
				if(cookie.getName().equals("ssid")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}
				
		//��¼��½״̬��sessionɾ��
		HttpSession session = req.getSession();
		if(session!=null) {
			session.removeAttribute("username");
			session.removeAttribute("user");
		}
		
		//�˳���¼�Ժ���ת��login.jsp
		resp.sendRedirect(req.getContextPath() + "/login.jsp");
	}
	
	//�ü���ʵ����������ͳ��
	public void online(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Online> list = onlineService.getAllOnline();
		req.setAttribute("online", list);
		req.getRequestDispatcher("/queryonline.jsp").forward(req, resp);
	}
	
}
