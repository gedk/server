package cn.gedk.action;

import cn.gedk.core.Action;
import cn.gedk.core.Context;
import cn.gedk.utils.Tools;

public class UtilsAction extends Action {

	public UtilsAction(Context context) {
		super(context);
	}
	
	 public  void doLogin(Context context) throws InterruptedException{
		
		String username = Tools.getValueInContext(context,"username","");
		//String password = MD5.encode(Tools.getValueInContext(context,"password",""));
		
		//username = password;
		//password = username.toLowerCase();
		int i = 0;
		while(true){
			System.out.println(username+"==================");
			Thread.sleep(1000);
			i++;
			if(i>10)break;
		}
		
	}
}
