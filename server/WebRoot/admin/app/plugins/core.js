Ext.namespace("EasyJF.Ext");
EasyJF.Ext.MainTabPanel = Ext.extend(Ext.TabPanel, {
	singleTabMode : false,
	iframe : false,
	enableAnimate : false,
	resizeTabs : true,
	minTabWidth : 65,
	tabWidth : 120,
	enableTabScroll : true,
	activeTab : 0,
	maxTabs : 20,// 默认的最大Tab数
	initComponent : function() {
		EasyJF.Ext.MainTabPanel.superclass.initComponent.call(this);
	}
});

