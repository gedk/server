/************************************************
 * author:gedk
 * description:应用头部
************************************************/
Ext.define('Color8.view.AppHeader',{
    extend: 'Ext.panel.Panel',
    xtype: 'appHeader',
    border:false,
    items:[ {
		xtype : "box",
		border : true,
		el : "header",
		autoShow : true,
		anchor : '100% -23'
	}],
    initComponent:function(){
    	this.callParent(arguments);
    }
});