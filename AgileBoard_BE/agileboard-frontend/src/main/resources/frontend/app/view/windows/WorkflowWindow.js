Ext.define('AD.view.windows.WorkflowWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.workflowWindow',
    itemId: 'workflowWindow',
    title: 'Workflow',
    resizable: false,
    modal : true,
    items:[
        {
            xtype: 'form',
            items:[
                {
                    xtype: 'panel',
                    width: 500,
                    border: false,
                    items: [
                        {
                            xtype: 'combobox',
                            store: 'AD.store.Statuses',
                            displayField: 'status',
                            queryMode: 'local',
                            allowBlank: false,
                            valueField: 'id',
                            itemId: 'workflowWindowStatus',
                            name: 'status',
                            width: 460,
                            fieldLabel: 'State *',
                            style: {margin: '20px'}
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    width: 500,
                    border: false,
                    layout:{
                        type: 'hbox',
                        pack: 'end'
                    },
                    items: [
                        {
                            xtype: 'textareafield',
                            itemId: 'workflowWindowCommentField',
                            width: 460,
                            height: 150,
                            fieldLabel:'Comment',
                            style: {margin: '10px 20px 10px 20px'}
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    width: 500,
                    border: false,
                    layout:{
                        type: 'hbox',
                        pack: 'end'
                    },
                    items: [
                        {
                            xtype: 'button',
                            formBind: true,
                            disabled: true,
                            itemId: 'workflowWindowOkButton',
                            text: 'Save',
                            style: {margin: '0 20px 10px 20px'}
                        }
                    ]
                }
            ]
        }
    ]
});