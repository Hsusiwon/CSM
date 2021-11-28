package cn.sion.csm.utils;

import java.security.MessageDigest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static final String KEY = ":cookie@cn.sion4637!65*95;";
	
	/**
	 * ָ�����������cookie�ļ��õķ���
	 * @param username :�ŵ�cookie��Ϣ����û���
	 * @param req
	 * @param resp	:����addcookie������response����
	 * @param sec	:����cookieʧЧ��ʱ�䣬��λ��
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
	 * ��username����
	 * @param string
	 * @return
	 */
	public static String md5Encrypt(String ss) {
		ss = ss==null?"":ss+KEY;
		char[] md5Digist = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f',};
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");//md5 sha1 sha2
			byte[] ssarr = ss.getBytes();
			md.update(ssarr);//�����ķŵ�������
			byte[] mssarr = md.digest();//������������ļ���
			
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
