/*******************************************************************************
 * author:gedk description:app 主界面
 ******************************************************************************/
var main = new EasyJF.Ext.MainTabPanel({
	region : 'center',
	xtype : 'tabpanel',
	activeTab : 0,
	border : false,
	minTabWidth : 110,
	plugins : new Ext.ux.TabCloseMenu(),
	items : [ {
		xtype : 'panel',
		layout : 'fit',
		html : '首页',
		border : false,
		title : '首页'
	} ]
});
Ext.define('Color8.view.Viewport', {
	extend : 'Ext.container.Viewport',
	requires : [ 'Color8.view.AppHeader', 'Color8.view.Menu' ],
	layout : 'border',
	items : [ {
		region : 'north',
		xtype : 'appHeader',
		collapseMode : 'mini',
		height : 100
	}, {
		xtype : 'menu',
		region : 'west',
		title : '功能选项',
		itemClick:function(t,record,n,index,e,eOpts){
			var leaf = record.get("leaf");
			var text = record.get("text");
			var script = record.get("script");
			if(leaf&&script){
				Ext.require("Color8.view.BasePanel",function(){
					var userPanel = main.child('basePanel');
                    if(!userPanel){
                        var userPanel =  Ext.widget('basePanel',{title:text});
                        main.add(userPanel);
                        main.setActiveTab(userPanel);
                    }else{
                    	main.setActiveTab(userPanel);
                    }
	            });
			} else {
				
			}
		}
	},main,{
		xtype : 'toolbar',
		items : [ "当前用户：" ],
		height : 20,
		region : 'south',
		border : false
	} ]
});
