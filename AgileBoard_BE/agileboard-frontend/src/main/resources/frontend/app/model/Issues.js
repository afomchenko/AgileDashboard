Ext.define('AD.model.Issues', {
    extend: 'Ext.data.Model',
    fields: ['id','project', 'name',
             'description', 'teststeps',
             'created', 'updated', 'status',
             'tasktype', 'priority', 'creator',
             'assignee', 'estimated', 'logged'],
    proxy: {
        type: 'ajax',
        reader: {type: 'json'}
    }
});