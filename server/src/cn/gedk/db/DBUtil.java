package cn.gedk.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gedk.core.Context;
import cn.gedk.utils.Constant;

/**
 * 数据库操作工具类
 * 
 * @className:DBUtil
 * @author ：gedk
 * @创建时间：2013-9-27
 */
public class DBUtil {
	/**
	 * 获取数据库连接
	 * 
	 * @return 返回获取到的数据库连接
	 */
	public static Connection getConnetion() {
		Connection con = DBPool.getConnection();
		return con;
	}

	public static List<Map<String, Object>> query(String sql, Connection conn) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (conn == null)
			return result;
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			Map<String, Object> r = null;
			while (rs.next()) {
				r = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					r.put(rsm.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				;
				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<Map<String, Object>> query(String sql) {
		Connection conn = getConnetion();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (conn == null)
			return result;
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			Map<String, Object> r = null;
			while (rs.next()) {
				r = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					r.put(rsm.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				;
				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Map<String, Object> queryOne(String sql) {
		Connection conn = getConnetion();
		Map<String, Object> result = new HashMap<String, Object>();
		if (conn == null)
			return result;
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			Map<String, Object> r = null;
			while (rs.next()) {
				r = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					r.put(rsm.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				;
				return r;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @return
	 */
	public static List<Map<String, Object>> query(String sql, String start,
			String limit) {
		long startx = System.currentTimeMillis();
		sql = sql + " limit " + start + "," + limit;
		Connection conn = getConnetion();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (conn == null)
			return result;
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			Map<String, Object> r = null;
			while (rs.next()) {
				r = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					r.put(rsm.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				;
				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.err.println("查询耗时间：" + (System.currentTimeMillis() - startx));
		return result;
	}

	public static List<Map<String, Object>> query(String sql, String start,
			String limit, Connection conn) {
		long startx = System.currentTimeMillis();
		sql = sql + " limit " + start + "," + limit;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (conn == null)
			return result;
		ResultSet rs = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			System.out.println("==>" + sql);
			Map<String, Object> r = null;
			while (rs.next()) {
				r = new HashMap<String, Object>();
				ResultSetMetaData rsm = rs.getMetaData();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					r.put(rsm.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				;
				result.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.err.println("查询耗时间：" + (System.currentTimeMillis() - startx));
		return result;
	}

	public static boolean executeSql(String sql, Connection con) {
		if (con == null)
			return false;
		Statement statement = null;
		boolean success = false;
		try {
			statement = con.createStatement();
			con.setAutoCommit(false);
			System.err.println("===>" + sql);
			statement.execute(sql);
			con.commit();
			success = true;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			success = false;
		} finally {
		}
		return success;
	}

	public static boolean executeSql(String sql) {
		Connection con = getConnetion();
		if (con == null)
			return false;
		Statement statement = null;
		boolean success = false;
		try {
			statement = con.createStatement();
			con.setAutoCommit(false);
			statement.execute(sql);
			con.commit();
			success = true;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			success = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	public static boolean executeSqls(List<String> sqls) {
		Connection con = getConnetion();
		if (con == null)
			return false;
		Statement statement = null;
		boolean success = false;
		try {
			statement = con.createStatement();
			for (String sql : sqls) {
				statement.execute(sql);
			}
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return success;
	}

	public static boolean executeSqls(List<String> sqls, Connection con) {
		Statement statement = null;
		boolean success = false;
		try {
			statement = con.createStatement();
			for (String sql : sqls) {
				statement.execute(sql);
			}
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {

		}

		return success;
	}

	public static String createInsertSql(Map<String, Object> params,
			String tableName) {
		String sql = "insert into " + tableName + "(";
		String temp = "";
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (temp.length() > 0)
				temp += ",";
			temp += m.getKey();
		}
		sql += temp;
		sql += ") ";
		sql += "values(";
		temp = "";
		for (Map.Entry<String, Object> m : params.entrySet()) {
			if (temp.length() > 0)
				temp += ",";
			if ("undefined".equals(m.getValue())) {
				temp += "''";
			} else {
				temp += "'" + m.getValue() + "'";
			}
		}
		sql += temp;
		sql += ")";
		return sql;
	}

	public static List<String> createInsertSql(
			List<Map<String, Object>> results, String tableName) {
		List<String> sqls = new ArrayList<String>();
		for (Map<String, Object> result : results) {
			String sql = "insert into " + tableName + "(";
			String temp = "";
			for (Map.Entry<String, Object> m : result.entrySet()) {
				if (temp.length() > 0)
					temp += ",";
				temp += m.getKey();
			}
			sql += temp;
			sql += ") ";
			sql += "values(";
			temp = "";
			for (Map.Entry<String, Object> m : result.entrySet()) {
				if (temp.length() > 0)
					temp += ",";

				if ("null".equals(m.getValue())) {
					temp += "''";
				} else {
					temp += "'" + m.getValue() + "'";
				}
			}
			sql += temp;
			sql += ")";
			sqls.add(sql);
		}
		return sqls;
	}

	public static String createUpdateSql(Map<String, Object> params,
			String tableName, String condition) {
		String sql = "update " + tableName + " set ";
		String temp = "";
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (!param.getValue().equals(Constant.NOT_UPDATE)) {
				if (temp.length() > 0)
					temp += ",";
				temp += param.getKey() + "='" + param.getValue() + "'";
			}
		}
		sql += temp;
		sql += condition;
		return sql;
	}

	public static String createUpdateSql2(Map<String, Object> params,
			String tableName, String condition) {
		String sql = "update " + tableName + " set ";
		String temp = "";
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (param.getValue() != null) {
				if (temp.length() > 0)
					temp += ",";
				temp += param.getKey() + "='" + param.getValue() + "'";
			}
		}
		sql += temp;
		sql += condition;
		return sql;
	}

	public static Context removeParamsInContext(Context context) {
		context.getTextFields().remove("id");
		context.getTextFields().remove("cmd");
		context.getTextFields().remove("projectPath");
		context.getTextFields().remove("searchType");
		context.getTextFields().remove("searchKey");
		return context;
	}

	/**
	 * 获取所有表名称
	 */
	public static void getAllTables() {
		Connection con = getConnetion();
		try {
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getTables(null, null, null,
					new String[] { "TABLE" });
			while (rs.next()) {
				System.out.println("表名：" + rs.getString(3));
			}
			con.close();
		} catch (Exception e) {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
