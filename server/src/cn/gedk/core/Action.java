package cn.gedk.core;

import java.util.HashMap;
import java.util.Map;

public abstract class Action {
	public Context context;
	public Map<String,Object> returnValue = new HashMap<String,Object>();
	
	public Action(Context context){
		this.context = context;
	}
}
