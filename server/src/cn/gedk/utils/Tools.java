package cn.gedk.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.gedk.core.Context;
import cn.gedk.db.DBUtil;

/**
 * @className:Tools
 * @author ：gedk
 * @descripthion:工具类方法
 * @创建时间：2013-9-23
 */
public class Tools {
	/**
	 * @author gedk
	 * @param path 文件路径
	 * @return 返回指定文件名的后缀
	 */
	public static String getFileSuffix(String path){
		if(path.lastIndexOf(".")>0)
		return path.substring(path.lastIndexOf(".")+1, path.length());
		return "";
	} 
	
	public static String getValueFromMapByDefaultValue(Map<String,Object> map,String field ,String defaultValue){
		String value = "";
		Object ov = map.get(field);
		if(ov == null)value = defaultValue;
		else value = ov.toString();
		return value;
	}
	public static String getDateStr() {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		return sm.format(new Date());
	}

	public static String getDateStr(String pattern) {
		SimpleDateFormat sm = new SimpleDateFormat(pattern);
		return sm.format(new Date());
	}

	public static String getTimeStr() {
		SimpleDateFormat sm = new SimpleDateFormat("HH-mm-ss");
		return sm.format(new Date());
	}
	public static String getFileName() {
		SimpleDateFormat sm = new SimpleDateFormat("HHmmss");
		return sm.format(new Date());
	}
	public static String getTimeStr2() {
		SimpleDateFormat sm = new SimpleDateFormat("HH:mm:ss");
		return sm.format(new Date());
	}
	public static int getTotals(String sql) {
		int totals = 0;
		List<Map<String, Object>> o = DBUtil.query(sql);
		if (o != null)
			totals = o.size();
		return totals;
	}
	public static String getValueInContext(Context context, String fieldName,String defaultValue) {
		Object value = context.getTextFields().get(fieldName);
		if (value != null) {
			return value.toString().trim();
		}
		return defaultValue;
	}
	public static void outJsonStr(String json, HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.write(json);
		pw.close();
	}
}
