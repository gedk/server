Ext.require('Ext.app.Application', function() {
	Ext.app.Application.addMembers({
		newControllerAdded : function(idx, cntr) {
			cntr.init(this);
		},
		hasController : function(name) {
			return !!this.controllers.get(name);
		},
		addController : function(name) {
			return this.getController(name);
		}
	});
});
Ext.application({
	name : 'Color8',
	appFolder : "admin/app",
	autoCreateViewport : true,
	controllers : [ 'Main' ]
});
