package cn.gedk.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author:gedk
 * @description:请求转发器
 */
public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String [] packageNames = {
			"cn.gedk.action"
	};
	synchronized protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("cmd");
		method = "do"+method.substring(0,1).toUpperCase()+method.substring(1, method.length());
		String className = this.getClassName(request);
		className = className.substring(0,1).toUpperCase() + className.substring(1, className.length()) + "Action";
		try {
			Class<?> cls = null;
			/**遍历所有的包名开始**/
			for(String packageName:packageNames){
				cls = this.getClassByName(packageName+"."+className);
				if(cls != null) break;
			}
			/**遍历所有的包名结束**/
			Context context = new Context(request);
			context.setRequest(request);
			context.setResponse(response);
			context.setServletContext(this.getServletContext());
			context.getTextFields().put("projectPath", this.getServletConfig().getServletContext().getRealPath("/"));
			
			Constructor<?> constructor = cls.getDeclaredConstructor(new Class[] { Context.class });
			Method m = cls.getMethod(method,new Class[]{ Context.class });
			Result result = (Result) m.invoke(constructor.newInstance(context),new Object[] {context});
			if(result != null)this.writeResponse(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Class<?> getClassByName(String className){
		Class<?> cls = null;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return cls;
	}	
	synchronized protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	private void writeResponse(HttpServletResponse response, Result result) {
		if ("json".equals(result.getType())) {
			response.setContentType("application/json;charset=UTF-8");
		} else {
			response.setContentType("text/html;charset=UTF-8");
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.write(result.toJson());
		pw.close();
	}
	public String getClassName(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String[] className = url.toString().split("/");
		return className[className.length - 1].split("\\.")[0];
	}
}
