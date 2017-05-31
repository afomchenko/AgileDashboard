Ext.define('AD.controller.Login', {
    extend: 'Ext.app.Controller',
    views: ['Login'],
    refs: [
        {ref: 'LoginForm', selector: '#loginForm'},
        {ref: 'ProjectCombo', selector: '#loginFormProjectCombo'},
        {ref: 'LoginField', selector: '#loginFormLoginField'}
    ],

    init: function () {
        this.control({
            '#loginFormProjectCombo': {render: this.loadProjectsStore},
            '#loginFormLoginField': {specialkey: this.onLoginFieldKeyPress}
        });
        this.callParent();
    },
    loadProjectsStore: function (combo) {
        combo.store.load();
    },
    onLoginFieldKeyPress: function (field, e) {
        var me = this, ENTER = 13;
        if (e.getKey() == ENTER) {
            Ext.Ajax.request({
                url: _path('login'),
                method: 'POST',
                jsonData: {name: me.getLoginField().getValue(),
                           project: me.getProjectCombo().getValue()},
                success: function (response) {
                    var respObj = Ext.JSON.decode(response.responseText);
                    sessionStorage.setItem('current_project', Ext.encode(respObj.project));
                    sessionStorage.setItem('current_user', Ext.encode(respObj.user));
                    sessionStorage.setItem('auth_token', respObj.authtoken);
                    Ext.getStore('AD.store.Issues').getProxy().url = issuesByProjectAndUser();
                    Ext.getStore('AD.store.Issues').load({
                        callback: function () {
                            _getFirst();
                            _changeView('main');
                        }
                    });
                }
            });
        }
    }
});