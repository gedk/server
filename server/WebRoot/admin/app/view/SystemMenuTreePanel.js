/*******************************************************************************
 * author:gedk description:菜单
 ******************************************************************************/
Ext.define('App.view.SystemMenuTreePanel', {
	extend : 'Ext.tree.TreePanel',
	xtype : 'treeMenu',
	border : false,
	lines : true,
	rootVisible:false,
	buildTreeStore : function() {
		Ext.regModel('menuModel', {
		    fields: [{name:'text',type:'string'},{name: 'leaf', type: 'boolean'},{name: 'id', type: 'string'},{name : 'script', type:'string'},{name:'appclass',type:'string'}]
		});
		var store = Ext.create('Ext.data.TreeStore', {
			model:'menuModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : 'menu.do?cmd=getMenuByParentId&id='+this.menuId,
				reader:{type:'json'}
			}
		});
		return store;
	},
	initComponent : function() {
		this.store = this.buildTreeStore();
		this.on("itemclick",this.menuClick,this);
		this.on("beforeitemclick",function(t,r,i,e,o){
			this.store.proxy.url = 'menu.do?cmd=getMenuByParentId&id='+r.get("id");
		},this);
		this.callParent(this);
	}
});