var _host = 'http://localhost:8080/agile/';
switch_color = function(n) {
    switch(n){
        case 0: return '#BEF781';
            break;
        case 1: return '#81F781';
            break;
        case 2: return '#81DAF5';
            break;
        case 3: return '#81BEF7';
            break;
        case 4: return '#A9A9F5';
            break;
        case 5: return '#F5DA81';
            break;
        case 6: return '#FAAC58';
            break;
        case 7: return '#FF8000';
            break;
        case 8: return '#FE2E2E';
            break;
    }
};

    Ext.Loader.setConfig({
    enabled: true,
    disableCaching: true,
    paths: {AD: 'app', Ext: 'ext'}
});

Ext.Loader.onReady(function () {
    Ext.Ajax.on('beforerequest', function (conn, options) {
        Ext.getBody().mask('Please wait...').dom.style.zIndex = '20000';
        Ext.Ajax.extraParams = {
            authtoken: sessionStorage['auth_token']
        };
    }, Ext.getBody());

    Ext.Ajax.on('requestcomplete', function (conn, response) {
        Ext.getBody().unmask();
    }, Ext.getBody());

    Ext.Ajax.on('requestexception', function (conn, response) {

        switch (response.status) {
            case 401:
                Ext.Msg.alert('Error', response.statusText);
                sessionStorage.clear();
                _changeView('loginForm');
                break;
            case 400:
                Ext.Msg.alert('Error', response.statusText);
                break;
        }
        Ext.getBody().unmask();
    }, Ext.getBody());



    Ext.application({
        requires: ['Ext.container.Viewport'],
        name: 'AD',
        appFolder: 'app',
        controllers: [
            'Login', 'Main', 'Head',
            'IssueBrowser', 'CreateIssueWindow',
            'EditIssueWindow','AssignWindow',
            'CommentWindow', 'WorkflowWindow',
            'LogTimeWindow'
        ],
        stores: [
            'AD.store.Projects', 'AD.store.Users',
            'AD.store.Issues', 'AD.store.Comments',
            'AD.store.TotalUsers', 'AD.store.AvailableProjects',
            'AD.store.IssueTags', 'AD.store.TotalTags', 'AD.store.Statuses'
        ],
        launch: function () {
            if (sessionStorage['current_user'] && sessionStorage['current_project']) {

                Ext.getStore('AD.store.Issues').getProxy().url = issuesByProjectAndUser();
                Ext.getStore('AD.store.Issues').load();
                _changeView('main');
            } else {
                _changeView('loginForm');
            }
        }
    });
});

function _path(url) {
    return _host + url;
}

function issuesByProjectAndUser() {
    return _path('tasks/project?user='
    + _decodeSessionStorage('current_user').id
    + '&project=' + _decodeSessionStorage('current_project').id);
}

function _changeView(viewAlias) {
    var vp = Ext.ComponentQuery.query('layoutViewport')[0];
    vp ? vp.removeAll(true) : vp = Ext.create('AD.view.Viewport');
    vp.add({xtype: viewAlias});
}

function _getFirst(){
    if(Ext.getStore('AD.store.Issues').first()){
        sessionStorage.setItem('current_issue', Ext.encode(Ext.getStore('AD.store.Issues').first().data));
    }
}

function _decodeSessionStorage(currentObject){
    if (sessionStorage[currentObject])
       return Ext.JSON.decode(sessionStorage[currentObject]);
}


