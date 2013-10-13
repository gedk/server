package cn.gedk.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gedk.core.Action;
import cn.gedk.core.Context;
import cn.gedk.core.Result;
import cn.gedk.db.DBUtil;
import cn.gedk.utils.Tools;

import com.google.gson.Gson;
/**
 * @className:MenuAction
 * @author ：gedk
 * @descripthion:菜单管理
 * @创建时间：2013-3-4
 */
public class MenuAction extends Action {
	public MenuAction(Context context) {
		super(context);
	}
	public Result doShowTables(Context context){
		DBUtil.getAllTables();
		return null;
	}
	public void doLoadMenu(Context context){
		String sql = "select id ,text as 'text', 'false' as leaf from t_menu where parent_id = '' order by list_order ";
		List<Map<String,Object>> results = DBUtil.query(sql);
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		datas = this.loadMenu(results);
		Gson g = new Gson();
		Tools.outJsonStr(g.toJson(datas),context.getResponse());
	}
	
	public List<Map<String,Object>> loadMenu(List<Map<String,Object>> parent){
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		String permission = Tools.getValueInContext(context, "permission","");
		for(Map<String,Object> p:parent){
			if("".equals(permission)){
				results.add(this.getChildren(p));
			} else {
				Map<String,Object> datas = this.getChildrenByPermission(p);
				if(datas.get("children")!=null)results.add(datas);
			}
		}
		return results;
	}
	public String getAllParentIdByChildrendId(String id){
		String ids = "-1";
		String parentId = this.getParentId(id);
		if(ids.length()>0)ids+=",";
		ids +=parentId;
		String sql = "select * from t_menu where id = '"+parentId+"'";
		List<Map<String,Object>> mid = DBUtil.query(sql);
		for(Map<String,Object> m:mid){
			String pid = Tools.getValueFromMapByDefaultValue(m,"id","-1");
			if(this.hasParent(pid)){
				if(ids.length()>0)ids+=",";
				ids+=this.getAllParentIdByChildrendId(pid);
			}
		}
		return ids;
	}
	public boolean hasParent(String id){
		String sql = "select * from t_menu where id = '"+id+"'";
		List<Map<String,Object>> results = DBUtil.query(sql);
		if(results != null && results.size()>0){
			String parentId = Tools.getValueFromMapByDefaultValue(results.get(0), "parent_id","");
			if(parentId != null && !parentId.equals("")) return true;
		}
		return false;
	}
	public String getParentId(String id){
		String sql = "select * from t_menu where id = '"+id+"'";
		List<Map<String,Object>> results = DBUtil.query(sql);
		if(results != null && results.size()>0){
			String parentId = Tools.getValueFromMapByDefaultValue(results.get(0), "parent_id","");
			if(parentId != null && !parentId.equals("")) return parentId;
		}
		return "-1";
	}
	public Map<String,Object> getChildrenByPermission(Map<String,Object> m){
		String parentIds = "";
		int role_id = 16;
		String qsql = "select * from t_menu_permission where role_id in(select id from t_role where code = '"+role_id+"')";
		List<Map<String,Object>> r = DBUtil.query(qsql);
		if(r != null && r.size()>0){
			for(Map<String,Object> map:r){
				String id = Tools.getValueFromMapByDefaultValue(map, "menu_id","-1");
				if(parentIds.length()>0) parentIds +=",";
				if("".equals(id)) id = "-1";
				parentIds +=id;
				if(parentIds.length()>0) parentIds +=",";
				parentIds +=this.getAllParentIdByChildrendId(id);
			}
		}
		if(parentIds.equals(""))parentIds+="-1";
		parentIds = parentIds.replaceAll("undefined", "-1");
		List<Map<String,Object>> ds = new ArrayList<Map<String,Object>>();
		String id = Tools.getValueFromMapByDefaultValue(m, "id","");
		String sql = "select  app_class as appClass ,script,icon_cls as iconCls ,id,text as 'text','true' as leaf from t_menu where id in("+parentIds+") and parent_id = '"+id+"' order by list_order";
		/**管理员拥有所有菜单权限**/
		sql = "select  app_class as appClass ,script,icon_cls as iconCls ,id,text as 'text','true' as leaf from t_menu where  parent_id = '"+id+"' order by list_order";
		List<Map<String,Object>> children = DBUtil.query(sql);
		/**递归查询所有子菜单**/
		for(Map<String,Object> c:children){
			if(this.hasChildMenu(c)){
				if(true){
					ds.add(this.getChildren(c));
				}/* else {
					//ds.add(this.getPermissionChildren(c,parentIds));
				}*/
			}else {
				ds.add(c);
			}
		}
		if(ds !=null && ds.size()>0){
			m.put("children",children);
			m.put("title", m.get("text"));
			m.put("leaf", false);
			m.put("iconCls", "lanyo-tree-node-leaf");
		}
		return m;
	}
	
	public Map<String,Object> getPermissionChildren(Map<String,Object> m,String mid){
		List<Map<String,Object>> ds = new ArrayList<Map<String,Object>>();
		String id = Tools.getValueFromMapByDefaultValue(m, "id","");
		String sql = "select  app_class as appClass ,script,icon_cls as iconCls ,id,text as 'text','true' as leaf from t_menu where id in("+mid+") and parent_id = '"+id+"'";
		List<Map<String,Object>> children = DBUtil.query(sql);
		for(Map<String,Object> c:children){
			if(this.hasChildMenu(c)){
				ds.add(this.getChildren(c));
			}else {
				ds.add(c);
			}
		}
		if(ds !=null && ds.size()>0){
			m.put("children",children);
			m.put("title", m.get("text"));
			m.put("leaf", false);
			m.put("iconCls", "lanyo-tree-node-leaf");
		}
		return m;
	}
	
	public Map<String,Object> getChildren(Map<String,Object> m){
		List<Map<String,Object>> ds = new ArrayList<Map<String,Object>>();
		String id = Tools.getValueFromMapByDefaultValue(m, "id","");
		String sql = "select  app_class as appClass ,script,icon_cls as iconCls ,id,text as 'text','true' as leaf from t_menu where parent_id = '"+id+"' ";
		sql+=" order by list_order";
		List<Map<String,Object>> children = DBUtil.query(sql);
		for(Map<String,Object> c:children){
			if(this.hasChildMenu(c)){
				ds.add(this.getChildren(c));
			}else {
				ds.add(c);
			}
		}
		if(ds !=null && ds.size()>0){
			m.put("children",children);
			m.put("title", m.get("text"));
			m.put("leaf", false);
			m.put("iconCls", "lanyo-tree-node-leaf");
		}
		return m;
	}
	public boolean hasChildMenu(Map<String,Object> m){
		String id = Tools.getValueFromMapByDefaultValue(m, "id","");
		String sql = "select * from t_menu where parent_id = '"+id+"'";
		List<Map<String,Object>> datas = DBUtil.query(sql);
		if(datas != null && datas.size()>0) return true;
		return false;
	}
	public void loadMenu(){
		String sql = "select id ,text as 'text', 'false' as leaf from t_menu where parent_id = ''";
		List<Map<String,Object>> results = DBUtil.query(sql);
		for(Map<String,Object> m:results){
			String parentId = Tools.getValueFromMapByDefaultValue(m, "id","");
			sql = "select id ,text as 'text', 'false' as leaf from t_menu where parent_id = '"+parentId+"'";
			List<Map<String,Object>> children = DBUtil.query(sql);
			if(children !=null && children.size()>0)m.put("children", children);
		}
	}
}
