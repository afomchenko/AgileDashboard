Ext.define('AD.controller.WorkflowWindow', {
    extend: 'Ext.app.Controller',
    views: ['windows.WorkflowWindow'],
    refs: [
        {ref: 'WorkflowWindow', selector: 'workflowWindow'},
        {ref: 'WorkflowWindowCommentField', selector: '#workflowWindowCommentField'},
        {ref: 'WorkflowWindowOkButton', selector: '#workflowWindowOkButton'},
        {ref: 'WorkflowWindowStatus', selector: '#workflowWindowStatus'}
    ],

    init: function () {
        this.control({
            'workflowWindow': {render: this.workflowRender},
            '#workflowWindowOkButton': {click: this.comment}
        });
        this.callParent();
    },

    workflowRender: function(){
        Ext.getStore('AD.store.Statuses').load();
    },

    comment: function(){
        var me= this,
            selectedStatus=me.getWorkflowWindowStatus().getValue();
        Ext.Ajax.request({
            url: _path('comments'),
            method: 'POST',
            jsonData: {
                task: _decodeSessionStorage('current_issue').id,
                comment: me.getWorkflowWindowCommentField().getValue()
            }
        });

        Ext.Ajax.request({
            url: _path('tasks/status/') + _decodeSessionStorage('current_issue').id,
            method: 'PUT',
            jsonData: {id: selectedStatus},
            success: function (response) {
                var respObj = Ext.JSON.decode(response.responseText);
                sessionStorage.setItem('current_issue', Ext.encode(respObj));
                me.getWorkflowWindow().close();
                _changeView('issueBrowser');
            }
        });
    }
});