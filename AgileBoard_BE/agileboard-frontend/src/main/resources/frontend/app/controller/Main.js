Ext.define('AD.controller.Main', {
    extend: 'Ext.app.Controller',
    views: [
        'Main',
        'elements.Separator',
        'elements.MainUserTimeBar'
    ],

    refs: [
        {ref: 'IssueNumber', selector: '#issueNumber'},
        {ref: 'Main', selector: 'main'},
        {ref: 'TimeTrackingGrid', selector: '#timeTrackingGrid'},
        {ref: 'TimeTrackingTopGrid', selector: '#timeTrackingTopGrid'},
        {ref: 'TimeTrackingBottomPanel', selector: '#timeTrackingBottomPanel'},
        {ref: 'TimeTrackingForm', selector: '#timeTrackingForm'},
        {ref: 'FirstDayTracking', selector: '#firstDayTracking'},
        {ref: 'SelectUserSeparator', selector: '#selectUserSeparator'}
    ],

    init: function () {
        this.control({
            '#issueNumber': {click: this.loadIssue},
            '#timeTrackingGrid': {select: this.timeTrackingGridRender},
            'main': {render: this.mainPageRender}
        });
        this.callParent();
    },
    loadIssue: function(grid) {
        var pos = grid.getSelectionModel().getCurrentPosition();
        var record = grid.store.getAt(pos.row);
        sessionStorage.setItem('current_issue', Ext.encode(record.data));
        _changeView('issueBrowser');
    },

    timeTrackingGridRender: function(grid, record) {
        this.getTimeTrackingTopGrid().remove('selectUserSeparator');
        this.getTimeTrackingTopGrid().add({
            xtype: 'separator',
            itemId: 'selectUserSeparator',
            border: false,
            text: record.data.name
        });
        var me=this;
        Ext.Ajax.request({
            url: _path('logs/user/') + record.data.userid,
            method: 'GET',
            success: function (response) {
                var userObj = Ext.JSON.decode(response.responseText);
                me.getTimeTrackingBottomPanel().removeAll();
                Ext.Array.each(userObj, function(dates, index) {
                    me.getTimeTrackingBottomPanel().add({
                        xtype: 'panel',
                        height: 22,
                        width: '100%',
                        border: false,
                        style: {marginBottom: '10px'},
                        layout: {
                            type: 'hbox',
                            align: 'stretch'
                        },
                        items: [
                            {
                                xtype: 'tbtext',
                                text: Ext.Date.format(Ext.Date.parse(dates.date, 'time'), "d M"),
                                border: false,
                                flex: 1
                            },
                            {
                                xtype: 'panel',
                                id: 'date'+index,
                                flex: 6,
                                height: 22,
                                border: false,
                                layout: {
                                    type: 'hbox',
                                    align: 'stretch'
                                }
                            }
                        ]
                    });
                    if(dates.total) {
                        Ext.Array.each(dates.tasks, function (log,n) {
                            var loggedText;
                            loggedText = '<b>'+log.project + '-' + log.task + ' (' + log.logged + 'h)</b>';
                            Ext.getCmp('date' + index).add({
                                xtype: 'mainUserTimeBar',
                                totalColor: switch_color(n),
                                flex: log.logged+2,
                                border: false,
                                height: 22,
                                text: loggedText
                            });

                        });
                    } else {
                        Ext.getCmp('date' + index).add({
                            xtype: 'mainUserTimeBar',
                            totalColor: '#ddd',
                            flex: 1,
                            border: false,
                            height: 22,
                            text: ''
                        });
                    }
                });
            }
        });
    },
    mainPageRender: function(){
        Ext.getStore('AD.store.Users').getProxy().url = _path('logs/project/') + _decodeSessionStorage('current_project').id;
        Ext.getStore('AD.store.Users').load();
    }
});