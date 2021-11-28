package cn.sion.csm.utils;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static final String KEY = ":cookie@cn.sion4637!65*95;";
	
	/**
	 * 指令浏览器创建cookie文件用的方法
	 * @param username :放到cookie信息里的用户名
	 * @param req
	 * @param resp	:调用addcookie方法的response对象
	 * @param sec	:设置cookie失效的时间，单位秒
	 */
	public static void createCookie(String username,HttpServletRequest req,HttpServletResponse resp,int sec) {
		Cookie userCookie = new Cookie("userKey", username);
		Cookie ssidCookie = new Cookie("ssid", md5Encrypt(username));
		userCookie.setMaxAge(sec);
		ssidCookie.setMaxAge(sec);
		resp.addCookie(userCookie);
		resp.addCookie(ssidCookie);
		
	}

	/**
	 * 将username加密
	 * @param string
	 * @return
	 */
	public static String md5Encrypt(String ss) {
		ss = ss==null?"":ss+KEY;
		char[] md5Digist = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f',};
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");//md5 sha1 sha2
			byte[] ssarr = ss.getBytes();
			md.update(ssarr);//把明文放到加密类
			byte[] mssarr = md.digest();//这里就是真正的加密
			
			int len = mssarr.length;
			char[] str = new char[len*2];
			int k = 0;
			
			for(int i=0;i<len;i++) {
				byte b = mssarr[i];
				str[k++] = md5Digist[b >>> 4 & 0xf];
				str[k++] = md5Digist[b & 0xf];
				
			}
			
			//System.out.println(str);
			return new String(str);
					
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
