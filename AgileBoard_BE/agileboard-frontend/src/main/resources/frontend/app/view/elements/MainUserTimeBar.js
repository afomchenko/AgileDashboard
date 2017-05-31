Ext.define('AD.view.elements.MainUserTimeBar', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mainUserTimeBar',
    initComponent: function () {
        this.items = {
            xtype: 'box',
            autoEl: {
                tag: 'div',
                html: '<div style="width: 100%;"><div style="height: 20px;padding-top: 4px; background-color:'
                + this.totalColor + ';text-align: center; width: 100%; font-size: 10px">'+ this.text
                +'</div></div>'
            }
        };
        this.callParent(arguments);
    }
});