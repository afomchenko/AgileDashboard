Ext.define('AD.controller.LogTimeWindow', {
    extend: 'Ext.app.Controller',
    views: ['windows.LogTimeWindow'],
    refs: [
        {ref: 'LogTimeWindow', selector: 'logTimeWindow'},
        {ref: 'LogTimeWindowOkButton', selector: '#logTimeWindowOkButton'},
        {ref: 'SetDateField', selector: '#setDateField'},
        {ref: 'SetLoggedTimeField', selector: '#setLoggedTimeField'}
    ],

    init: function () {
        this.control({
            'logTimeWindow': {afterrender: this.renderLogTimeWindow},
            '#logTimeWindowOkButton': {click: this.logTime}
        });
        this.callParent();
    },

    renderLogTimeWindow: function(){
        Ext.getStore('AD.store.TotalUsers').load();
    },

    logTime: function(){
        var me= this,
            currentDate = Ext.Date.format(me.getSetDateField().getValue(), 'time');
        Ext.Ajax.request({
            url: _path('logs'),
            method: 'POST',
            jsonData: {
                task: _decodeSessionStorage('current_issue').id,
                logged: me.getSetLoggedTimeField().getValue(),
                date: currentDate
            },
            success: function () {
                var currentIssue = _decodeSessionStorage('current_issue');
                currentIssue.logged+=me.getSetLoggedTimeField().getValue();
                sessionStorage.setItem('current_issue', Ext.encode(currentIssue));
                me.getLogTimeWindow().close();
                _changeView('issueBrowser');
            }
        });
    }
});