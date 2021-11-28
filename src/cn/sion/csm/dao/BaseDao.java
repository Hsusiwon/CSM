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
 * 这是一个dao称里的基本类，在于被具体的dao类，UserDao去继承它来用的，不能new BaseDao（）来直接用
 * @author Sion
 *
 * @param <T>：针对要操作各种数据表映射到java工程里java类，User
 */
public class BaseDao<T> {
	
	QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDao( ) {
		//用baseDao的构造方法初始化clazz属性，User  User.clazz
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
	 * 查询数据表，取出sql语句的结果集的第一条数据，封装成一个类的对象返回，不支持事务
	 * 用到dbutils工具类
	 * null的位置，应该传入BaseDao<T>里边的T的真正用的时候的类型的Class
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
	 * 查询数据表，取出sql语句的结果集的第一条数据，封装成一个类的对象返回，支持事务
	 * 用到dbutils工具类
	 * null的位置，应该传入BaseDao<T>里边的T的真正用的时候的类型的Class
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
	 * 实现insert，update，delete通用的更新方法
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
	 * 通用的返回sql结果只有一个数值的类型的查询，比如用户个数
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
