/****************************************
 * author:gedk
 * description:主控制器
 ***************************************/
Ext.define('App.controller.Gedk', {
	extend : 'Ext.app.Controller',
	views : ['AppHeader','BasePanel'],
	init : function() {
		this.control({
			'button[id=btn_add]':{
				'click':function(){
					alert('x');
				}
			}
		});
	}
});
