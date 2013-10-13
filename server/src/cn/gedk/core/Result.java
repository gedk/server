package cn.gedk.core;
import com.google.gson.Gson;
/**
 * @className:Result
 * @author:gedk
 * @descripthion:结果集
 * @创建时间：2013-5-29 
 */
public class Result {
	private Object result = null;
	private String type = "";
	private Gson gson = new Gson();
	public String getType() {
		return type;
	}
	public Result(String type,Object result){
		this.result = result;
		this.type = type;
	}
	public String toJson(){		
		return gson.toJson(this.result);
	}
}
