/************************************************
 * author:gedk
 * description:应用头部
************************************************/
Ext.define('App.view.system.SystemMenuPanel',{
    extend: 'Ext.panel.Panel',
    xtype: 'systemMenuPanel',
    layout:'fit',
    border:false,
    tbar:[
       {text:'刷新',id:'btn_refresh',cls:'refresh'},
       {text:'查询',id:'btn_search',cls:'search'},
       {text:'添加',id:'btn_add',cls:'add'},
       {text:'编辑',id:'btn_edit',cls:'edit'},
       {text:'删除',id:'btn_remove',cls:'remove'}
    ],
    createGrid:function(){
    	var store = new Ext.data.JsonStore({
    	    fields:['parent_name','app_class','text','script','list_order'],
    	    autoLoad:true,
    	    pageSize:5,
    	    proxy: {
    	    	type : 'ajax',
				actionMethods : 'post',
				url:'menu.do?cmd=loadSystemMenu',
    	        reader: {
    	            type: 'json',
    	            root: 'results',
    	            totalProperty : "rowCount"
    	        }
    	    }
    	});
    	return new Ext.grid.Panel({
    	    store:store,
    	    border:false,
        	columnLines:true,
        	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SINGLE",showHeaderCheckbox:false}),
    	    columns: [
    	        new Ext.grid.RowNumberer({text:'编号',width:30}),
    	        { text: '父级菜单', dataIndex: 'parent_name',flex:1 },
    	        { text: '菜单名称',  dataIndex: 'text',flex:1 },
    	        { text: '别名', dataIndex: 'app_class',flex:1},
    	        { text: '包名', dataIndex: 'script' ,flex:1},
    	        { text: '排序', dataIndex: 'list_order' ,flex:1}
    	        
    	    ],
    	    layout:'fit',
    	    bbar: Ext.create('Ext.PagingToolbar', {
                store: store,
                displayInfo: true,
                displayMsg: '第{0}-{1}条/总共{2}条',
                emptyMsg: "没有数据"
            })
    	});
    },
    initComponent:function(){
    	this.grid = this.createGrid();
    	this.items = [this.grid];
    	this.callParent(arguments);
    	var app = App.getApplication();
    	if(!app.hasController('system.CSystemMenu')){
    	   app.addController('system.CSystemMenu');
    	}
    }
});