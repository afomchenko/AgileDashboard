Ext.define('AD.model.Projects', {
    extend: 'Ext.data.Model',
    fields: ['desc','id', 'name', 'shortname'],
    proxy: {
        type: 'ajax',
        url: _path('projects'),
        reader: {type: 'json'}
    }
});