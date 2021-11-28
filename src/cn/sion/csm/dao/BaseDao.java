package cn.sion.csm.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.sion.csm.utils.JdbcUtils;

/**
 * ����һ��dao����Ļ����࣬���ڱ������dao�࣬UserDaoȥ�̳������õģ�����new BaseDao������ֱ����
 * @author Sion
 *
 * @param <T>�����Ҫ�����������ݱ�ӳ�䵽java������java�࣬User
 */
public class BaseDao<T> {
	
	QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDao( ) {
		//��baseDao�Ĺ��췽����ʼ��clazz���ԣ�User  User.clazz
		Type superType = this.getClass().getGenericSuperclass();
		if(superType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) superType;
			Type[] tarry = pt.getActualTypeArguments();
			if(tarry[0] instanceof Class) {
				clazz = (Class<T>) tarry[0];			
			}
		}
	}
	
	/**
	 * ��ѯ���ݱ�ȡ��sql���Ľ�����ĵ�һ�����ݣ���װ��һ����Ķ��󷵻أ���֧������
	 * �õ�dbutils������
	 * null��λ�ã�Ӧ�ô���BaseDao<T>��ߵ�T�������õ�ʱ������͵�Class
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql,Object... args) {
		Connection conn = null;
		T entity = null;
		try {
			conn = JdbcUtils.getConnection();
			entity = queryRunner.query(conn, sql, new BeanHandler<T>(clazz), args);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConn(conn);
		}
		return entity;
	}
	
	/**
	 * ��ѯ���ݱ�ȡ��sql���Ľ�����ĵ�һ�����ݣ���װ��һ����Ķ��󷵻أ�֧������
	 * �õ�dbutils������
	 * null��λ�ã�Ӧ�ô���BaseDao<T>��ߵ�T�������õ�ʱ������͵�Class
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(Connection conn,String sql,Object... args) {
		T entity = null;
		try {
			entity = queryRunner.query(conn, sql, new BeanHandler<T>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getlist(String sql,Object... args) {
		Connection conn = null;
		List<T> list = null;
		try {
			conn = JdbcUtils.getConnection();
			list = queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConn(conn);
		}
		return list;
	}
	
	/**
	 * ʵ��insert��update��deleteͨ�õĸ��·���
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(String sql,Object... args) {
		Connection conn = null;
		int rows = 0;
		try {
			conn = JdbcUtils.getConnection();
			rows = queryRunner.update(conn,sql,args);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConn(conn);
		}
		return rows;
	}

	/**
	 * ͨ�õķ���sql���ֻ��һ����ֵ�����͵Ĳ�ѯ�������û�����
	 * @param sql
	 * @param args
	 * @return
	 */
	public Object getValue(String sql,Object... args) {
		Connection conn = null;
		Object obj = null;
		try {
			conn = JdbcUtils.getConnection();
			obj = queryRunner.query(conn, sql, new ScalarHandler<>(), args);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConn(conn);
		}
		return obj; 
	}
}
