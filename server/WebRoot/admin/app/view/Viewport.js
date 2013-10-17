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
Ext.define('App.view.Viewport', {
	extend : 'Ext.container.Viewport',
	requires : [ 'App.view.AppHeader', 'App.view.Menu' ],
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
			var app_class = record.get("appclass");
			if(leaf&&script){
				Ext.require(script,function(){
					var userPanel = main.child(app_class);
                    if(!userPanel){
                        var userPanel =  Ext.widget(app_class,{title:text});
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
		items : [ "当前用户：administrator" ],
		height : 20,
		region : 'south',
		border : false
	} ]
});
