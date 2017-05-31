Ext.define('AD.controller.AssignWindow', {
    extend: 'Ext.app.Controller',
    views: ['windows.AssignWindow'],
    refs: [
        {ref: 'AssignWindow', selector: 'assignWindow'},
        {ref: 'AssignedUser', selector: '#assignedUser'},
        {ref: 'AssignWindowOkButton', selector: '#assignWindowOkButton'}
    ],

    init: function () {
        this.control({
            '#assignWindowOkButton': {click: this.assign}
        });
        this.callParent();
    },
    assign: function(){
        var me= this;
        Ext.Ajax.request({
            url: _path('tasks/assign/') + _decodeSessionStorage('current_issue').id,
            method: 'PUT',
            jsonData: {id: me.getAssignedUser().getValue()},
            success: function (response) {
                var respObj = Ext.JSON.decode(response.responseText);
                sessionStorage.setItem('current_issue', Ext.encode(respObj));
                me.getAssignWindow().close();
                _changeView('issueBrowser');
            }
        });
    }
});