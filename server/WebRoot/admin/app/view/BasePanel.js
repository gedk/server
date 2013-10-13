/************************************************
 * author:gedk
 * description:应用头部
************************************************/
Ext.define('Color8.view.BasePanel',{
    extend: 'Ext.panel.Panel',
    xtype: 'basePanel',
    layout:'fit',
    border:false,
    tbar:[{text:'add',id:'btn_add'}],
    createGrid:function(){
    	var store = new Ext.data.Store({
    	    fields:['name', 'email', 'phone'],
    	    data:{'items':[
    	        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224"  },
    	        { 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" },
    	        { 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244"  },
    	        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224"  },
    	        { 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" },
    	        { 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244"  },
    	        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224"  },
    	        { 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" },
    	        { 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244"  },
    	        { 'name': 'Lisa',  "email":"lisa@simpsons.com",  "phone":"555-111-1224"  },
    	        { 'name': 'Bart',  "email":"bart@simpsons.com",  "phone":"555-222-1234" },
    	        { 'name': 'Homer', "email":"home@simpsons.com",  "phone":"555-222-1244"  },
    	        { 'name': 'Marge', "email":"marge@simpsons.com", "phone":"555-222-1254"  }
    	    ]},
    	    proxy: {
    	        type: 'memory',
    	        reader: {
    	            type: 'json',
    	            root: 'items'
    	        }
    	    }
    	});
    	return new Ext.grid.Panel({
    	    store:store,
    	    border:false,
        	columnLines:true,
    	    columns: [
    	        { text: 'Name',  dataIndex: 'name' },
    	        { text: 'Email', dataIndex: 'email', flex: 1 },
    	        { text: 'Phone', dataIndex: 'phone' }
    	    ],
    	    layout:'fit',
    	    bbar: Ext.create('Ext.PagingToolbar', {
                store: store,
                displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
            })
    	});
    },
    initComponent:function(){
    	
    	var grid = this.createGrid();
    	this.items = [grid];
    	
    	this.callParent(arguments);
    	var app = Color8.getApplication();
    	if(!app.hasController('Gedk')){
    	   app.addController('Gedk');
    	}
    }
});