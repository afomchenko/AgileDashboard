Ext.define('AD.view.Viewport', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.layoutViewport',
    itemId: 'layoutViewport',
    layout: 'fit',
    initComponent: function () {
        this.callParent();
    }
});