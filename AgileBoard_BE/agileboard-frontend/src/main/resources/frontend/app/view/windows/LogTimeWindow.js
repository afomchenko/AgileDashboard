Ext.define('AD.view.windows.LogTimeWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.logTimeWindow',
    itemId: 'logTimeWindow',
    resizable: false,
    title: 'Log time',
    modal : true,
    items: [
        {
            xtype: 'form',
            items: [
                {
                    xtype: 'numberfield',
                    allowBlank: false,
                    fieldLabel: 'Time *',
                    itemId: 'setLoggedTimeField',
                    minValue: 1,
                    maxValue: 24,
                    style: {margin: '10px 20px 10px 20px'},
                    width: 250
                },
                {
                    xtype: 'datefield',
                    width: 250,
                    itemId: 'setDateField',
                    fieldLabel: 'Date *',
                    style: {margin: '10px 20px 10px 20px'},
                    value: new Date()
                },
                {
                    xtype: 'button',
                    formBind: true,
                    disabled: true,
                    itemId: 'logTimeWindowOkButton',
                    style: {margin: '0 20px 10px 20px'},
                    text: 'Log',
                    width: 250
                }
            ]
        }
    ]
});