Ext.define('AD.model.Comments', {
    extend: 'Ext.data.Model',
    fields: ['user', 'task', 'comment', 'created'],
    proxy: {
        type: 'ajax',
        reader: {type: 'json'}
    }
});