/*******************************************************************************
 * author:gedk description:菜单
 ******************************************************************************/
Ext.define('Color8.view.SystemMenuTreePanel', {
	extend : 'Ext.tree.TreePanel',
	xtype : 'treeMenu',
	border : false,
	lines : false,
	children : [],
	rootVisible : false,
	buildTreeStore : function() {
		Ext.regModel('menuModel', {
		    fields: [{name:'text',type:'string'},{name: 'leaf', type: 'boolean'},{name: 'text', type: 'string'},{name : 'script', type:'string'},{name:'appclass',type:'string'}]
		});
		var store = Ext.create('Ext.data.TreeStore', {
			autoLoad:true,
			model:'menuModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : 'menu.do?cmd=loadMenu',
				reader : {type:'json'}
			},
			root : {}
		});
		return store;
	},
	initComponent : function() {
		this.store = this.buildTreeStore();
		this.on("itemclick",this.menuClick,this);
		this.callParent(this);
	}
});