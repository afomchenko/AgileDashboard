Ext.define('AD.controller.IssueBrowser', {
    extend: 'Ext.app.Controller',
    views: [
        'IssueBrowser',
        'elements.Separator',
        'IssuesGrid',
        'MainIssueContent',
        'elements.TimeBar'
    ],

    refs: [
        {ref: 'IssueBrowser', selector: 'issueBrowser'},
        {ref: 'IssuesGrid', selector: 'issuesGrid'},
        {ref: 'IssueGridMainContent', selector: '#issueGridMainContent'},
        {ref: 'TestStepsText', selector: '#testStepsText'},
        {ref: 'PeopleAssignee', selector: '#peopleAssignee'},
        {ref: 'TagsField', selector: '#tagsField'},
        {ref: 'PeopleReporter', selector: '#peopleReporter'},
        {ref: 'TimeCreated', selector: '#timeCreated'},
        {ref: 'TimeUpdated', selector: '#timeUpdated'},
        {ref: 'IssueType', selector: '#issueType'},
        {ref: 'IssuePriority', selector: '#issuePriority'},
        {ref: 'IssueState', selector: '#issueState'},
        {ref: 'Comments', selector: '#comments'},
        {ref: 'IssuesWestGrid', selector: '#issuesWestGrid'},
        {ref: 'DescriptionText', selector: '#descriptionText'},
        {ref: 'LoggedTimeBar', selector: '#loggedTimeBar'},
        {ref: 'RemainingTimeBar', selector: '#remainingTimeBar'},
        {ref: 'TimeTracking', selector: '#timeTracking'},
        {ref: 'EstimatedTimeBar', selector: '#estimatedTimeBar'},
        {ref: 'Erl', selector: '#erl'}
    ],

    init: function () {
        this.control({
            'issueBrowser': {afterrender: this.renderIssueBrowser},
            'mainIssueContent': {afterrender: this.renderIssueBrowser},
            'issuesGrid': {beforerender: this.renderIssueGrid},
            '#timeTracking': {render: this.renderTimeTracking},
            '#erl': {render: this.renderERL}
        });
        this.callParent();
    },

    renderIssueBrowser: function () {
        var me = this;
        var currentIssue = _decodeSessionStorage('current_issue'),
            dtcr = new Date(currentIssue.created),
            dtupd = new Date(currentIssue.updated);
        me.getDescriptionText().update('<div style="white-space: pre-wrap; text-align: justify">'
        + currentIssue.description + '</div>');
        me.getTestStepsText().update('<div style="white-space: pre-wrap; text-align: justify">'
        + currentIssue.teststeps + '</div>');
        me.getTimeCreated().setText(Ext.Date.format(dtcr, 'Y-m-d | H:i'));
        me.getTimeUpdated().setText(Ext.Date.format(dtupd, 'Y-m-d | H:i'));
        me.getIssuePriority().setText(setIssueIcon(currentIssue.priority));
        me.getIssueType().setText(setIssueIcon(currentIssue.tasktype));
        me.getIssueState().setText(currentIssue.status);

        Ext.Ajax.request({
            url: _path('users/' + currentIssue.assignee),
            method: 'GET',
            success: function (response) {
                var userObj = Ext.JSON.decode(response.responseText);
                me.getPeopleAssignee().setText('<img src="app/icons/ForComment.png" style=" margin-left: -10px "> ' + userObj.name);
            }
        });
        Ext.Ajax.request({
            url: _path('users/' + currentIssue.creator),
            method: 'GET',
            success: function (response) {
                var userObj = Ext.JSON.decode(response.responseText);
                me.getPeopleReporter().setText('<img src="app/icons/ForComment.png" style=" margin-left: -10px "> ' + userObj.name);
            }
        });

        Ext.getStore('AD.store.Comments').getProxy().url = _path('comments/task/' + currentIssue.id);
        var comments = new Ext.panel.Panel({border: false}),
            commentsStore = Ext.getStore('AD.store.Comments');
        commentsStore.load({
            callback: function () {
                this.each(function (item) {
                    var dtCreated = new Date(item.get('created'))
                    comments.add({
                        xtype: 'panel',
                        border: false,
                        style: {paddingBottom: '20px'},
                        items: [
                            {
                                xtype: 'panel',
                                layout: {
                                    type: 'hbox',
                                    align: 'stretch'
                                },
                                border: false,
                                items: [
                                    {
                                        xtype: 'tbtext',
                                        flex: 1,
                                        text: '<img src="app/icons/ForComment.png"><b> ' + item.get('user') + '</b>'
                                    },
                                    {
                                        xtype: 'tbtext',
                                        flex: 8,
                                        text: Ext.Date.format(dtCreated, 'Y-m-d | H:i')
                                    }
                                ]
                            },
                            {
                                xtype: 'panel',
                                border: false,
                                style: {paddingLeft: '18px'},
                                html: '<div style="white-space: pre-wrap; text-align: justify">'+item.get('comment') + '</div>'
                            }
                        ]
                    })
                });
            }
        });
        me.getComments().add(comments);

        Ext.getStore('AD.store.IssueTags').getProxy().url = _path('tags/task/' + currentIssue.id);
        var tags = new Ext.toolbar.Toolbar({
                border: false,
                style: {background: 'white'}
            }),
            tagsStore = Ext.getStore('AD.store.IssueTags');
        tags.add({xtype: 'text', text: 'Tags: '});
        tagsStore.load({
            callback: function () {
                var store = this, i = 0;
                store.each(function (item) {
                    if (i < 7) {
                        tags.add({
                                text: '<b style="text-decoration: underline; color: #4169E1 ">'
                                + item.get('name') + '</b>',
                                handler: function () {
                                    Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/tag/') + item.get('name');
                                    Ext.getStore('AD.store.Issues').load({
                                        callback: function () {
                                            _getFirst();
                                            _changeView('issuesGrid');
                                        }
                                    });
                                }
                            }
                        );
                        if ((store.indexOf(item) < store.getCount() - 1) && (store.indexOf(item) < 6)) {
                            tags.add({xtype: 'tbseparator'});
                        }
                        i++;
                    }
                });
            }
        });
        me.getTagsField().add(tags);
    },
    renderTimeTracking: function () {
        var currentIssue = _decodeSessionStorage('current_issue'),
            me = this, logged, estimated = currentIssue.estimated, loggedPCT = 0;
        logged = currentIssue.logged;
        if (logged / estimated * 100 > 100) loggedPCT = 100;
        else loggedPCT = logged / estimated * 100;
        me.getEstimatedTimeBar().add({
            xtype: 'timeBar',
            totalcolor: '#ddd',
            text: estimated + 'h',
            itemId: 'loggedTimeBar',
            loggedcolor: '#4169E1',
            logged: 100,
            border: false,
            flex: 2
        });
        me.getLoggedTimeBar().add({
            xtype: 'timeBar',
            totalcolor: '#ddd',
            text: logged + 'h',
            itemId: 'loggedTimeBar',
            loggedcolor: '#FF8C00',
            logged: loggedPCT,
            border: false,
            flex: 2
        });
        me.getRemainingTimeBar().add({
            xtype: 'timeBar',
            totalcolor: '#32CD32',
            text: (estimated - logged) + 'h',
            itemId: 'loggedTimeBar',
            loggedcolor: '#ddd',
            logged: loggedPCT,
            border: false,
            flex: 2
        });
    },
    renderIssueGrid: function () {
        var me = this,
            typeColor,
            issues = new Ext.panel.Panel({
                border: false,
                overflowY : 'scroll',
                height: 600
            }),
            issuesStore = Ext.getStore('AD.store.Issues');
        issuesStore.load({
            callback: function () {
                this.each(function (item) {
                    switch (item.get('priority')) {
                        case 'Critical':
                            typeColor = "#F78181";
                            break;
                        case 'Low':
                            typeColor = "#58D3F7";
                            break;
                        case 'Normal':
                            typeColor = "#58FA58";
                            break;
                    }
                    issues.add({
                        xtype: 'panel',
                        bodyStyle: {"background-color": typeColor},
                        listeners: {
                            'render': {
                                fn: function () {
                                    this.body.on('click', this.handleClick, this);
                                },
                                single: true
                            }
                        },
                        handleClick: function () {
                            sessionStorage.setItem('current_issue', Ext.encode(item['data']));
                            me.getIssueGridMainContent().removeAll();
                            me.getIssueGridMainContent().add({xtype: 'lowerToolbar'});
                            me.getIssueGridMainContent().add({xtype: 'mainIssueContent'});
                        },
                        items: [
                            {
                                layout: {
                                    type: 'hbox',
                                    align: 'stretch'
                                },
                                style: {cursor: 'pointer'},
                                border: false,
                                bodyStyle: {"background-color": typeColor},
                                items: [
                                    {
                                        xtype: 'tbtext',
                                        flex: 1,
                                        text: '<b>' + item.get('project') + '-' + item.get('id') + '</b>'
                                    },
                                    {
                                        xtype: 'panel',
                                        border: false,
                                        flex: 3,
                                        layout: {
                                            type: 'vbox',
                                            align: 'right'
                                        },
                                        bodyStyle: {"background-color": typeColor},
                                        items: [
                                            {
                                                xtype: 'tbtext',
                                                style: {cursor: 'pointer'},
                                                text: item.get('priority') + ' ' + item.get('tasktype')
                                            },
                                            {
                                                xtype: 'tbtext',
                                                style: {cursor: 'pointer'},
                                                text: item.get('status')
                                            }
                                        ]
                                    }
                                ]
                            },
                            {
                                xtype: 'tbtext',
                                style: {cursor: 'pointer'},
                                text: item.get('name')
                            }
                        ]
                    })
                });
            }
        });
        me.getIssuesWestGrid().add(issues);
    },
    renderERL: function () {
        var currentIssue = _decodeSessionStorage('current_issue'),
            me = this, logged, estimated = currentIssue.estimated;
        logged = currentIssue.logged;
        me.getErl().setText(estimated + 'h ' + (estimated - logged) + 'h ' + logged + 'h ');
    }
});


function setIssueIcon(value) {
    return '<img src="app/icons/' + value + '.png" />'
        + '<b style="vertical-align: top; margin-left: 5px; font-weight: 200">'
        + '  ' + value
}
