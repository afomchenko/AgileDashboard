Ext.define('AD.model.TotalUsers', {
    extend: 'Ext.data.Model',
    fields: ['id', 'name', 'email', 'created'],
    proxy: {
        type: 'ajax',
        url: _path('users'),
        reader: {type: 'json'}
    }
});