package cn.gedk.core;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * @className:Context
 * @author ：gedk
 * @descripthion:解析请求里面的参数信息
 * @创建时间：2013-7-25
 */
public class Context {
	
	private Map<String,Object> textFields = new HashMap<String, Object>();
	private Map<String,FileItem> fileFields = new HashMap<String, FileItem>();
	private HttpServletRequest request = null;
	private ServletContext servletContext = null;
	private HttpServletResponse response = null;
	public Context(HttpServletRequest request){
		try {
			this.paraseRequestParameter(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Context(){}
	/**
	 * 解析request参数并封装到自己的模型中。
	 * @param request
	 * @throws Exception
	 */
	private void paraseRequestParameter(HttpServletRequest request) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		ServletFileUpload upload = new ServletFileUpload();
		upload.setSizeMax(-1);
		upload.setFileSizeMax(1024 * 1024 * 10);
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(1024 * 5);
		upload.setFileItemFactory(diskFileItemFactory);
		if(ServletFileUpload.isMultipartContent(request)){
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem f:items){
				if(!f.isFormField()){
					this.fileFields.put(f.getFieldName(),f);
				} else{
					if(!f.getFieldName().matches("ext-.*"))
					this.textFields.put(f.getFieldName(), new String(f.getString().getBytes("ISO-8859-1"),"utf-8"));
				}
			}
		} else{
			Enumeration<?>  names = request.getParameterNames();
			while(names.hasMoreElements()){
				String name = names.nextElement().toString();
				String value = request.getParameter(name);
				if(!name.matches("ext-.*"))
				this.textFields.put(name, value);
			}
		}
	}
	public Map<String, Object> getTextFields() {
		return textFields;
	}
	public void setTextFields(Map<String, Object> textFields) {
		this.textFields = textFields;
	}
	public Map<String, FileItem> getFileFields() {
		return fileFields;
	}
	public void setFileFields(Map<String, FileItem> fileFields) {
		this.fileFields = fileFields;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
