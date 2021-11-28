package cn.sion.csm.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * jdbc������
 * @author Sion
 *
 */
public class JdbcUtils {
	//���ݿ����ӳ�C3P0
	private static DataSource dataSource = null;
	static {//��̬�����ֻ�ᱻִ��һ��
		dataSource = new ComboPooledDataSource("mysql");
	}
	
	
	/**
	 * ��ȡ�����ݿ�mysql���������Ӷ���conn
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
		
	}
	
	/**
	 * ��ͨ�õĹر����ݿ����Ӷ���ķ���
	 * @param conn
	 */
	public static void closeConn(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void rollbackTransaction(Connection conn) {
		if(conn!=null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
