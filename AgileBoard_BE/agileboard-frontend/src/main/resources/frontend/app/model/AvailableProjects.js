Ext.define('AD.model.AvailableProjects', {
    extend: 'Ext.data.Model',
    fields: ['id', 'name','desc', 'shortname'],
    proxy: {
        type: 'ajax',
        reader: {type: 'json'}
    }
});