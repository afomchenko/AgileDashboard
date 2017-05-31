Ext.define('AD.controller.CreateIssueWindow', {
    extend: 'Ext.app.Controller',
    views: ['windows.CreateIssue'],
    refs: [
        {ref: 'CreateIssue', selector: 'createIssue'},
        {ref: 'CreateButton', selector: '#createButton'},
        {ref: 'Priority', selector: '#priority'},
        {ref: 'TaskName', selector: '#taskName'},
        {ref: 'CreateWindowProjectName', selector: '#createWindowProjectName'},
        {ref: 'TaskTestSteps', selector: '#taskTestSteps'},
        {ref: 'TaskDescription', selector: '#taskDescription'},
        {ref: 'CreateWindowIssueAssignUser', selector: '#createWindowIssueAssignUser'},
        {ref: 'EstimateField', selector: '#estimateField'},
        {ref: 'InputTagsField', selector: '#inputTagsField'},
        {ref: 'TaskType', selector: '#taskType'}
    ],

    init: function () {
        this.control({
            'createIssue': {afterrender: this.renderProjectsBox},
            '#createButton': {click: this.createIssue}
        });
        this.callParent();
    },

    renderProjectsBox: function(){
        Ext.getStore('AD.store.AvailableProjects').getProxy().url = _path('projects/user/')
        + _decodeSessionStorage('current_user').id;
        Ext.getStore('AD.store.AvailableProjects').load();
        Ext.getStore('AD.store.TotalUsers').load();
    },

    createIssue: function(){
        var me = this;
        Ext.Ajax.request({
            url: _path('tasks'),
            method: 'POST',
            jsonData: {
                project: me.getCreateWindowProjectName().getValue(),
                name: me.getTaskName().getValue(),
                description: me.getTaskDescription().getValue(),
                teststeps: me.getTaskTestSteps().getValue(),
                tasktype: me.getTaskType().getValue().tasktype,
                priority: me.getPriority().getValue().priority,
                assignee: me.getCreateWindowIssueAssignUser().getValue(),
                estimated: me.getEstimateField().getValue()
            },
            success: function (response) {
                var respObj = Ext.JSON.decode(response.responseText);
                sessionStorage.setItem('current_issue', Ext.encode(respObj));
                Ext.getStore('AD.store.Issues').getProxy().url = issuesByProjectAndUser();
                Ext.getStore('AD.store.Issues').load();
                Ext.Ajax.request({
                    url: _path('tags'),
                    method: 'POST',
                    jsonData: {
                        task: respObj.id,
                        tags: me.getInputTagsField().getValue()
                    },
                    success: function () {
                        me.getCreateIssue().close();
                        _changeView('issueBrowser');
                    }
                });
            }
        });
    }
});