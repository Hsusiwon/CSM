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
		//在这一个方法里能处理增删改查所有的功能
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
		if(count>0) {//能从数据库中找到同名记录，证明新名字有冲突，不允许添加
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","名字有冲突，请重新输入！");
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
			jsonMain.put("message","注册成功！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","注册失败！");
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
			//throw new RuntimeException("注册失败！");
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
		//通过id，获取到要的修改id的用户信息
		User oriUser = userService.get(id);
		
		String oriUsername = oriUser.getUsername();
		String newUsername = req.getParameter("username");
		newUsername = newUsername.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		long count = userService.getCountByName(newUsername);
		if(!newUsername.equals(oriUsername) && count>0) {//新名字和原来的名字不一样，还能从数据库中找到同名记录，证明新名字有冲突，不允许修改
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","名字有冲突，请重新输入！");
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
			jsonMain.put("message","更改用户信息成功！");
			resp.setContentType("text/text;charset=utf-8");
	        resp.setCharacterEncoding("UTF-8");
	        resp.getWriter().print(jsonMain);
	        resp.getWriter().flush();
	        resp.getWriter().close();
		}else {
			JSONObject jsonMain = new JSONObject();
			jsonMain.put("code", 0);
			jsonMain.put("message","更改用户信息失败！");
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
		req.setAttribute("userList", list);//把结果集放到req的属性空间
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
		//第一步驟，拿到三個參數
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String expiredays = req.getParameter("expiredays");
		username = username.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		password = password.replaceAll("[`~!@#$%^&*()|;:',.<>/?{}]", "");
		
		Cookie[] cookies = req.getCookies();
		
		boolean login = false;//是否登錄的標記：true已經登陸，false尚未登錄
		String account = null;//登錄賬號
		String ssid = null;//这是一个标记，通过cookie拿到的一个判断用户该不该成功登录的标记
		
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
		
		if(!login) {//login:false取反true，表明用户尚未登录
			//用户首次访问
			User user = userService.login(username,password);//通过访问数据库来判断用户名和密码正确性
			if(user != null) {//正确，登录
				expiredays = expiredays==null?"":expiredays;
				switch (expiredays) {
				case "7":
					//创建一个cookie对象，设置cookie对象的失效时间是7天
					CookieUtils.createCookie(username, req, resp, 7*24*60*60);
					break;
				case "30":
					//创建一个cookie对象，设置cookie对象的失效时间是30天
					CookieUtils.createCookie(username, req, resp, 30*24*60*60);
					break;
				case "100":
					//创建一个cookie对象，设置cookie对象的失效时间是永久
					CookieUtils.createCookie(username, req, resp, Integer.MAX_VALUE);
					break;
				default:
					//创建一个cookie对象，设置cookie对象的失效时间-1,关闭浏览器cookie失效
					CookieUtils.createCookie(username, req, resp, -1);
					break;
				}
				//首次登录成功，准许进去主界面，将用户名和用户类放入
				req.getSession().setAttribute("useruame", username);
				req.getSession().setAttribute("user", user);
				//3、访问者登录成功后，将online数据表里面的username，从游客改为真正的用户名
				//把在线状态online表的username从游客状态改为真正的username
				HttpSession session = req.getSession();
				Online ol = onlineService.getOnlineBySsid(session.getId());
				if(ol!=null) {
					ol.setUsername(username);
					onlineService.updateOnline(ol);
				}
				//登录成功，准许进入主页
				resp.sendRedirect(req.getContextPath()+"/index.jsp");
			} else {
				req.setAttribute("note", "用户名或密码错误！");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}  
		//login:true，则用cookie登录在LoginFilter中执行重定向至主页

	}
	
	@SuppressWarnings("unused")
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//记录登陆状态的cookie删除
		Cookie[] cookies = req.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("userKey")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);//把cookie失效标识写到客户端cookie
				}
				if(cookie.getName().equals("ssid")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}
				
		//记录登陆状态的session删除
		HttpSession session = req.getSession();
		if(session!=null) {
			session.removeAttribute("username");
			session.removeAttribute("user");
		}
		
		//退出登录以后，跳转到login.jsp
		resp.sendRedirect(req.getContextPath() + "/login.jsp");
	}
	
	//用监听实现在线人数统计
	public void online(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Online> list = onlineService.getAllOnline();
		req.setAttribute("online", list);
		req.getRequestDispatcher("/queryonline.jsp").forward(req, resp);
	}
	
}
