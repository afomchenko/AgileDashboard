Ext.define('AD.view.elements.Separator', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.separator',
    initComponent: function () {
        this.items = {
            xtype: 'box',
            autoEl: {
                tag: 'div',
                html: '<div style="margin: 30px 0; height: 2px; background-color: #A4A4A4">' +
                '<span style="color: #666; padding: 0 10px; background-color: white; ' +
                'position: relative; top: -0.5em; left: 1.5em">' +
                this.text + '</span></div>'
            }
        };
        this.callParent(arguments);
    }
});