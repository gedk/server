package cn.gedk.db;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * 获取数据库连接
 * @className:DBPool
 * @author ：gedk
 * @创建时间：2013-9-23
 */
public class DBPool {
	private static Context CTT;
	private static Connection conn;
	private static DataSource ds;
	static {
		try {
			CTT = (Context) new InitialContext().lookup("java:comp/env");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("jndi 数据源加载失败！");
		}
	}
	/**
	 * 根据特定数据库连接（jni名称为jdbc/8color）
	 * @return 返回根据jni获取到的数据库连接
	 */
	 public static Connection getConnection() {
		try {
			synchronized(DBPool.class){
				ds = (DataSource) CTT.lookup("jdbc/8color");
				conn = ds.getConnection();
				while(true){
					if(conn != null) break;
					ds.getConnection();
				}
				return conn;
			}
		} catch (NamingException e) {
	 		e.printStackTrace();
			throw new RuntimeException("jndi 数据源创建失败！");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据jni名称获取不同的数据库连接
	 * @param name jni名称
	 * @return 返回根据jni获取到的数据库连接
	 */
	public static Connection getConnection(String name) {
			try {
				synchronized(DBPool.class){
					ds = (DataSource) CTT.lookup(name);
					conn = ds.getConnection();
					while(true){
						if(conn != null) break;
						ds.getConnection();
					}
					return conn;
				}
			} catch (NamingException e) {
		 		e.printStackTrace();
				throw new RuntimeException("jndi 数据源创建失败！");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
}
