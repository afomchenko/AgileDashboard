Ext.define('AD.controller.EditIssueWindow', {
    extend: 'Ext.app.Controller',
    views: ['windows.EditIssue'],
    refs: [
        {ref: 'EditIssue', selector: 'editIssue'},
        {ref: 'EditButton', selector: '#editButton'},
        {ref: 'TaskName', selector: '#taskName'},
        {ref: 'ProjectName', selector: '#projectName'},
        {ref: 'TaskTestSteps', selector: '#taskTestSteps'},
        {ref: 'TaskDescription', selector: '#taskDescription'},
        {ref: 'UserName', selector: '#userName'},
        {ref: 'RadioTaskType', selector: '#radioTaskType'},
        {ref: 'RadioPriority', selector: '#radioPriority'},
        {ref: 'EstimateField', selector: '#estimateField'},
        {ref: 'EditWindowTagsField', selector: '#editWindowTagsField'}
    ],

    init: function () {
        this.control({
            'editIssue': {afterrender: this.afterRender},
            '#editButton': {click: this.editIssue}
        });
        this.callParent();
    },

    afterRender: function(){
        var me=this,
            currentIssue = _decodeSessionStorage('current_issue');

        me.getTaskTestSteps().setValue(currentIssue.teststeps);
        me.getTaskDescription().setValue(currentIssue.description);
        me.getTaskName().setValue(currentIssue.name);
        me.getRadioPriority().setValue({'priority':currentIssue.priority});
        me.getRadioTaskType().setValue({'tasktype':currentIssue.tasktype});

        var tagString='',
            currentIssueTagsStore = Ext.getStore('AD.store.IssueTags');
        currentIssueTagsStore.load({
            callback: function () {
                this.each(function (item) {
                    tagString += item.data.name;
                    if (currentIssueTagsStore.indexOf(item) < currentIssueTagsStore.getCount() - 1) {
                        tagString += ', ';
                    }
                });
                me.getEditWindowTagsField().setValue(tagString);
            }
        });
    },

    editIssue: function(){
        var me = this;
        Ext.Ajax.request({
            url: _path('tasks/'+ _decodeSessionStorage('current_issue').id),
            method: 'PUT',
            jsonData: {
                name: me.getTaskName().getValue(),
                description: me.getTaskDescription().getValue(),
                teststeps: me.getTaskTestSteps().getValue(),
                tasktype: me.getRadioTaskType().getValue().tasktype,
                priority: me.getRadioPriority().getValue().priority
            },
            success: function (response) {
                var respObj = Ext.JSON.decode(response.responseText);
                sessionStorage.setItem('current_issue', Ext.encode(respObj));
                Ext.getStore('AD.store.Issues').getProxy().url = issuesByProjectAndUser();
                Ext.getStore('AD.store.Issues').load();
                Ext.Ajax.request({
                    url: _path('tags/') + _decodeSessionStorage('current_issue').id ,
                    method: 'PUT',
                    jsonData: {tags: me.getEditWindowTagsField().getValue()},
                    success: function () {
                        me.getEditIssue().close();
                        _changeView('issueBrowser');
                    }
                });
            }
        });
    }
});