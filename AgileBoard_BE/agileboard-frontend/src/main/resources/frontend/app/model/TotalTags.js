Ext.define('AD.model.TotalTags', {
    extend: 'Ext.data.Model',
    fields: ['name', 'count'],
    proxy: {
        type: 'ajax',
        url: _path('tags'),
        reader: {type: 'json'}
    }
});