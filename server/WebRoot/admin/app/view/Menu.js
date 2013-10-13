/*******************************************************************************
 * author:gedk description:应用头部
 ******************************************************************************/
Ext.define('Color8.view.Menu', {
	extend : 'Ext.panel.Panel',
	xtype : 'menu',
	border : true,
	width : 150,
	requires : [ 'Color8.view.SystemMenuTreePanel' ],
	layout : 'accordion',
	initComponent : function() {
		this.callParent(arguments);
		this.loadMenu();
	},
	loadMenu : function() {
		this.add({
			xtype : 'treeMenu',
			title : '客户数据',
			menuClick :this.itemClick
		});
		this.add({
			xtype : 'treeMenu',
			title : '风格数据',
			menuClick :this.itemClick
		});
	}
});