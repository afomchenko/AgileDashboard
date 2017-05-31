Ext.define('AD.model.Statuses', {
    extend: 'Ext.data.Model',
    fields: ['id', 'status'],
    proxy: {
        type: 'ajax',
        url: _path('statuses'),
        reader: {type: 'json'}
    }
});