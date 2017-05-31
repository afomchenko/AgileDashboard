Ext.define('AD.model.IssueTags', {
    extend: 'Ext.data.Model',
    fields: ['name'],
    proxy: {
        type: 'ajax',
        reader: {type: 'json'}
    }
});