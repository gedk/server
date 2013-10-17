/****************************************
 * author:gedk
 * description:主控制器
 ***************************************/
Ext.define('App.controller.BaseGridController', {
	extend : 'Ext.app.Controller',
	fun_refresh:function(){alert("refresh");},
	fun_search:function(){alert("refresh");},
	fun_add:function(){alert("refresh");},
	fun_edit:function(){alert("refresh");},
	fun_remove:function(){alert("refresh");},
	init : function(){
		this.control({
			'button[id=btn_refresh]':{'click':this.fun_refresh},
			'button[id=btn_search]':{'click':this.fun_search},
			'button[id=btn_add]':{'click':this.fun_add},
			'button[id=btn_edit]':{'click':this.fun_edit},
			'button[id=btn_remove]':{'click':this.fun_remove}
		});
	}
});
