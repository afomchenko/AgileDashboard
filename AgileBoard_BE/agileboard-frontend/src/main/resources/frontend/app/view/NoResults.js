Ext.define('AD.view.NoResults', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.noResults',
    border: false,
    itemId: 'main',
    items:[
        {
            xtype: 'head',
            region: 'north'
        },
        {
            xtype: 'panel',
            border: false,
            html: '<div style="font-size: 3em; padding: 15px">No Results</div>'

        }
    ]
});
