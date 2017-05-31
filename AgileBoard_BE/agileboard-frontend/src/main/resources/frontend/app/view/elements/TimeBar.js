Ext.define('AD.view.elements.TimeBar', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.timeBar',
    initComponent: function () {
        this.items = {
            xtype: 'box',
            autoEl: {
                tag: 'div',
                html: '<div style="display: inline-block; width: 20%; padding-top:1px; text-align: right;"><span>'
                + this.text +'</span></div><div style="display: inline-block; width: 79%; margin-bottom: -4px; padding-left: 10px"><div style="height: 20px; background-color:'
                + this.totalcolor + '; width: 100%"><div style=" height: 20px; background-color:'
                + this.loggedcolor + '; width:'
                + this.logged + '%"></div></div></div>'
            }
        };
        this.callParent(arguments);
    }
});