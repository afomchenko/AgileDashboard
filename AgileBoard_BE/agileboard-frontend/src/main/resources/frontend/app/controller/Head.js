Ext.define('AD.controller.Head', {
    extend: 'Ext.app.Controller',
    views: [
        'header.Header',
        'header.HeaderMainToolbar',
        'header.HeaderForIssue',
        'header.LowerToolbar',
        'NoResults'
    ],
    refs: [
        {ref: 'HeaderMainToolbar', selector: 'headerMainToolbar'},
        {ref: 'HeaderForIssue', selector: 'headerForIssue'},
        {ref: 'LowerToolbar', selector: 'lowerToolbar'},
        {ref: 'issueBrowser', selector: 'issueBrowser'},
        {ref: 'IssuesGrid', selector: 'issuesGrid'},
        {ref: 'Head', selector: 'head'},
        {ref: 'CurrentProject', selector: '#projectName'},
        {ref: 'MainPageTagsField', selector: '#mainPageTagsField'},
        {ref: 'Description', selector: '#projectDescription'},
        {ref: 'SwitchProject', selector: '#projectButton'},
        {ref: 'ProjectMenu', selector: '#projectMenu'},
        {ref: 'IssueName', selector: '#issueName'},
        {ref: 'IssueDescription', selector: '#issueDescription'},
        {ref: 'CreateIssueButton', selector: '#createIssueButton'},
        {ref: 'CommentButton', selector: '#commentButton'},
        {ref: 'AssignButton', selector: '#assignButton'},
        {ref: 'EditIssueButton', selector: '#editIssueButton'},
        {ref: 'WorkflowButton', selector: '#workflowButton'},
        {ref: 'LogButton', selector: '#logButton'},
        {ref: 'Logout', selector: '#logout'},
        {ref: 'OpenedIssues', selector: '#openedIssues'},
        {ref: 'CompletedIssues', selector: '#completedIssues'},
        {ref: 'InProgressIssues', selector: '#inProgressIssues'},
        {ref: 'ReportedByMe', selector: '#reportedByMe'},
        {ref: 'CommentedByMe', selector: '#commentedByMe'},
        {ref: 'LoggedByMe', selector: '#loggedByMe'},
        {ref: 'SearchField', selector: '#searchField'}
    ],
    init: function () {

        this.control({

            'head': {afterrender: this.renderMainHeader},
            'lowerToolbar': {afterrender: this.renderIssueHeader},
            'issueBrowser': {afterrender: this.renderIssueHeader},
            'issuesGrid': {afterrender: this.renderIssueHeader},
            'headerMainToolbar': {afterrender: this.afterRender},
            '#logout': {click: this.logout},
            '#openedIssues': {click: this.showMyOpenedIssues},
            '#completedIssues': {click: this.showMyCompletedIssues},
            '#inProgressIssues': {click: this.showMyInProgressIssues},
            '#reportedByMe': {click: this.showReportedByMeIssues},
            '#commentedByMe': {click: this.showCommentedByMeIssues},
            '#loggedByMe': {click: this.showLoggedByMeIssues},
            '#createIssueButton': {click: this.showCreateIssueWindow},
            '#commentButton': {click: this.showCommentWindow},
            '#assignButton': {click: this.showAssignWindow},
            '#editIssueButton': {click: this.showEditIssueWindow},
            '#workflowButton': {click: this.showWorkflowWindow},
            '#logButton': {click: this.showLogTimeWindow},
            '#searchField': {specialkey: this.onSearchFieldKeyPress}
        });
        this.callParent();
    },
    renderMainHeader: function () {
        var me = this, currentProject = _decodeSessionStorage('current_project');
        me.getCurrentProject().setText('<img src="app/icons/Lightbulb.gif">'
        + currentProject.shortname);
        me.getDescription().setText(currentProject.desc);
        var tagsToolbar = new Ext.panel.Panel({
                border: false,
                layout: {
                    type: 'hbox',
                    align: 'bottom'
                }
            }),
            totalTagsStore = Ext.getStore('AD.store.TotalTags');
        totalTagsStore.load({
            callback: function () {
                var averageCount = totalTagsStore.average('count');
                this.each(function (item) {
                    tagsToolbar.add({
                        xtype: 'panel',
                        border: false,
                        html: '<span style="font-size:' + (10 + item.get('count')/averageCount*7)
                        + 'px;cursor: pointer ">' + item.get('name') + '</span>',
                        style: {marginRight: '15px'},
                        listeners: {
                            'render': {
                                fn: function () {
                                    this.body.on('click', this.handleClick, this);
                                },
                                single: true
                            }
                        },
                        handleClick: function () {
                            Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/tag/') + item.get('name');
                            Ext.getStore('AD.store.Issues').load({
                                callback: function () {
                                    _getFirst();
                                    _changeView('issuesGrid');
                                }
                            });
                        }
                    });
                });
            }
        });
        me.getMainPageTagsField().add(tagsToolbar);

    },
    renderIssueHeader: function () {
        var me = this;
        me.getIssueName().setText(projectIssueId());
        me.getIssueDescription().setText(_decodeSessionStorage('current_issue').name);
    },
    afterRender: function () {
        var me = this,
            menu = new Ext.menu.Menu;
        Ext.getStore('AD.store.AvailableProjects').getProxy().url = _path('projects/user/')
        + _decodeSessionStorage('current_user').id;

        var projectsStore = Ext.getStore('AD.store.AvailableProjects');
        projectsStore.load({
            callback: function () {
                this.each(function (item) {
                    menu.add({
                        text: item.get('name'),
                        handler: function () {
                            sessionStorage.setItem('current_project', Ext.encode(item['data']));
                            Ext.getStore('AD.store.Issues').getProxy().url = issuesByProjectAndUser();
                            Ext.getStore('AD.store.Issues').load();
                            _changeView('main');
                        }
                    });
                });
            }
        });
        me.getSwitchProject().menu = menu;
    },
    logout: function () {
        Ext.Ajax.request({
            url: _path('logout'),
            method: 'GET'
        });
        sessionStorage.clear();
        _changeView('loginForm');
    },
    showMyOpenedIssues: function () {
        Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/open/');
        Ext.getStore('AD.store.Issues').load({
            callback: function () {
                _getFirst();
                _changeView('issuesGrid');
            }
        });
    },
    showMyCompletedIssues: function () {
        Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/complete/');
        Ext.getStore('AD.store.Issues').load({
            callback: function () {
                _getFirst();
                _changeView('issuesGrid');
            }
        });
    },
    showMyInProgressIssues: function () {
        Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/progress/');
        Ext.getStore('AD.store.Issues').load({
            callback: function () {
                _getFirst();
                _changeView('issuesGrid');
            }
        });
    },
    showReportedByMeIssues: function () {
        Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/reported');
        Ext.getStore('AD.store.Issues').load({
            callback: function () {
                _getFirst();
                _changeView('issuesGrid');
            }
        });
    },
    showCommentedByMeIssues: function () {
        Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/commented');
        Ext.getStore('AD.store.Issues').load({
            callback: function () {
                _getFirst();
                _changeView('issuesGrid');
            }
        });
    },
    showLoggedByMeIssues: function () {
        Ext.getStore('AD.store.Issues').getProxy().url = _path('tasks/logged');
        Ext.getStore('AD.store.Issues').load({
            callback: function () {
                _getFirst();
                _changeView('issuesGrid');
            }
        });
    },
    showCreateIssueWindow: function () {
        Ext.create('AD.view.windows.CreateIssue').show();
    },
    showCommentWindow: function () {
        Ext.create('AD.view.windows.CommentWindow', {title: projectIssueId() + ' Comment'}).show();
    },
    showAssignWindow: function () {
        Ext.create('AD.view.windows.AssignWindow', {title: projectIssueId() + ' Assign'}).show();
    },
    showEditIssueWindow: function () {
        Ext.create('AD.view.windows.EditIssue', {title: projectIssueId() + ' Edit'}).show();
    },
    showWorkflowWindow: function () {
        Ext.create('AD.view.windows.WorkflowWindow', {title: projectIssueId() + ' Workflow'}).show();
    },
    showLogTimeWindow: function () {
        Ext.create('AD.view.windows.LogTimeWindow', {title: projectIssueId() + ' Log time'}).show();
    },
    onSearchFieldKeyPress: function (field, e) {
        var me = this, ENTER = 13;
        if (e.getKey() == ENTER) {
            var store = Ext.getStore('AD.store.Issues');
            store.getProxy().url = _path('tasks/search');
            store.getProxy().extraParams = {query: me.getSearchField().getValue()};
            store.load({
                callback: function () {
                    if(store.count()) {
                        _getFirst();
                        _changeView('issuesGrid');
                    } else {
                        _changeView('noResults');
                    }
                }
            });
        }
    }
});

function projectIssueId() {
    return _decodeSessionStorage('current_issue').project
        + '-' + _decodeSessionStorage('current_issue').id;

}

