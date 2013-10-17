package cn.gedk.action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.gedk.core.Action;
import cn.gedk.core.Context;
import cn.gedk.core.Result;
import cn.gedk.db.DBUtil;
import cn.gedk.utils.Constant;
import cn.gedk.utils.Tools;
/**
 * 菜单管理
 * @className:MenuAction
 * @author ：gedk
 * @创建时间：2013-3-4
 */
public class MenuAction extends Action {
	public MenuAction(Context context) {
		super(context);
		this.returnValue.clear();
	}
	public Result doShowTables(Context context){
		DBUtil.getAllTables();
		return null;
	}
	/**
	 * 获取根级菜单
	 * @param context
	 * @return 返回所获取到的根菜单数据
	 */
	public Result doLoadParentMenu(Context context){
		this.returnValue.clear();
		String sql = "SELECT * FROM T_MENU WHERE PARENT_ID IS NULL";
		List<Map<String,Object>> results = DBUtil.query(sql);
		this.returnValue.put("results",results);
		return new Result("json",this.returnValue);
	}
	/**
	 * 根据id获取子菜单数据
	 * @param context
	 * @return 以json格式返回获取到的菜单数据
	 */
	public Result doGetMenuByParentId(Context context){
		this.returnValue.clear();
		String id = Tools.getValueInContext(context, "id","-1");
		this.returnValue.putAll(this.getChildren(id));
		return new Result("json",this.returnValue);
	}
	/**
	 * 根据菜单编号获取子菜单
	 * @param id 菜单编号
	 * @return 以json格式返回子菜单数据
	 */
	private Map<String,Object> getChildren(String id){
		String sql = "SELECT id, APP_CLASS as appClass ,script ,id,text as 'text',leaf from t_menu where parent_id = '"+id+"' ";
		List<Map<String,Object>> children = DBUtil.query(sql);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("children",children);
		return m;
	}
	
	/**
	 * 分页加载系统菜单数据
	 * @param context
	 * @return 以json格式返回系统菜单数据
	 */
	public Result doLoadSystemMenu(Context context){
		String limit = Tools.getValueInContext(context, "limit",Constant.DEFAULT_LIMIT);
		String start = Tools.getValueInContext(context, "start",Constant.DEFAULT_START);
		
		String sql = "SELECT * FROM T_MENU";
		List<Map<String,Object>> results = DBUtil.query(sql, start, limit);
		this.returnValue.put("results",results);
		this.returnValue.put("rowCount",10);
		return new Result("json",this.returnValue);
	}
}
