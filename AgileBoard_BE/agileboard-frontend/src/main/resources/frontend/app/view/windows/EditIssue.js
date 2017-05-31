Ext.define('AD.view.windows.EditIssue', {
    extend: 'Ext.window.Window',
    alias: 'widget.editIssue',
    itemId: 'editIssue',
    title: 'Edit Issue',
    resizable: false,
    modal: true,
    style: {padding: '10px'},
    width: 750,
    items: [
        {
            xtype: 'form',
            items: [
                {
                    xtype: 'panel',
                    border: false,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            border: false,
                            style: {margin: '20px'},
                            items: [
                                {
                                    xtype: 'radiogroup',
                                    allowBlank: false,
                                    fieldLabel: 'Type *',
                                    itemId: 'radioTaskType',
                                    width: 400,
                                    items: [
                                        {
                                            boxLabel: 'Bug',
                                            name: 'tasktype',
                                            inputValue: 'Bug'
                                        },
                                        {
                                            boxLabel: 'CR',
                                            name: 'tasktype',
                                            inputValue: 'CR'
                                        },
                                        {
                                            boxLabel: 'Enhancement',
                                            name: 'tasktype',
                                            inputValue: 'Enhancement'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'radiogroup',
                                    allowBlank: false,
                                    fieldLabel: 'Priority *',
                                    itemId: 'radioPriority',
                                    width: 400,
                                    items: [
                                        {
                                            boxLabel: 'Critical',
                                            name: 'priority',
                                            inputValue: 'Critical'
                                        },
                                        {
                                            boxLabel: 'Normal',
                                            name: 'priority',
                                            inputValue: 'Normal'
                                        },
                                        {
                                            boxLabel: 'Low',
                                            name: 'priority',
                                            inputValue: 'Low'
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            allowBlank: false,
                            name: 'name',
                            itemId: 'taskName',
                            width: 670,
                            margin: '20',
                            enableKeyEvents: true,
                            fieldLabel: 'Name *'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    items: [
                        {
                            xtype: 'textareafield',
                            allowBlank: false,
                            name: 'description',
                            itemId: 'taskDescription',
                            height: 100,
                            width: 670,
                            margin: '20',
                            enableKeyEvents: true,
                            fieldLabel: 'Description *'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    items: [
                        {
                            xtype: 'textareafield',
                            allowBlank: false,
                            name: 'teststeps',
                            itemId: 'taskTestSteps',
                            height: 100,
                            width: 670,
                            margin: '20',
                            enableKeyEvents: true,
                            fieldLabel: 'Test Steps *'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    items: [
                        {
                            xtype: 'textfield',
                            itemId: 'editWindowTagsField',
                            name: 'tags',
                            width: 670,
                            margin: '20',
                            enableKeyEvents: true,
                            fieldLabel: 'Tags'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    layout: {
                        type: 'hbox',
                        pack: 'end'
                    },
                    items: [
                        {
                            xtype: 'button',
                            formBind: true,
                            disabled: true,
                            text: 'OK',
                            itemId: 'editButton',
                            style: {margin: '20px'}
                        }
                    ]
                }
            ]
        }
    ]
});