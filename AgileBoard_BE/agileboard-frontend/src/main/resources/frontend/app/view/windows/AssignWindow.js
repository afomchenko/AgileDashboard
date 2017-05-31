Ext.define('AD.view.windows.AssignWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.assignWindow',
    itemId: 'assignWindow',
    title: 'Assign',
    resizable: false,
    modal : true,
    items: [
        {
            xtype: 'form',
            items: [
                {
                    xtype: 'combobox',
                    itemId: 'assignedUser',
                    store: 'AD.store.TotalUsers',
                    queryMode: 'local',
                    style: {margin: '10px 20px 10px 20px'},
                    width: 200,
                    allowBlank: false,
                    displayField: 'name',
                    valueField: 'id'
                },
                {
                    xtype: 'button',
                    formBind: true,
                    disabled: true,
                    itemId: 'assignWindowOkButton',
                    style: {margin: '0 20px 10px 20px'},
                    text: 'Assign',
                    width: 200
                }
            ]
        }
    ]
});