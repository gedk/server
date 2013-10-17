/*******************************************************************************
 * author:gedk description:应用头部
 ******************************************************************************/
Ext.define('App.view.Menu', {
	extend : 'Ext.panel.Panel',
	xtype : 'menu',
	border : true,
	width : 150,
	requires : [ 'App.view.SystemMenuTreePanel' ],
	layout : 'accordion',
	initComponent : function() {
		this.callParent(arguments);
		this.loadMenu();
	},
	loadMenu : function() {
		Ext.Ajax.request({
			url:'menu.do?cmd=loadParentMenu',
			method:"post",
			success:function(response){
				var res = Ext.decode(response.responseText);
				Ext.each(res.results,function(item){
					this.add({
						xtype : 'treeMenu',
						title : item.text,
						menuId:item.id,
						menuClick :this.itemClick
					});
				},this);
			},
			failure: function(response, opts) {},
			scope:this
		});
	}
});