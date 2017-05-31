Ext.define('AD.view.windows.CreateIssue', {
    extend: 'Ext.window.Window',
    alias: 'widget.createIssue',
    itemId: 'createIssue',
    title: 'Create Issue',
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
                            xtype: 'combobox',
                            store: 'AD.store.AvailableProjects',
                            allowBlank: false,
                            displayField: 'name',
                            valueField: 'shortname',
                            editable: false,
                            itemId: 'createWindowProjectName',
                            name: 'project',
                            fieldLabel: 'Project *',
                            style: {margin: '20px'}
                        },
                        {
                            xtype: 'panel',
                            border: false,
                            style: {margin: '4px'},
                            items: [
                                {
                                    xtype: 'radiogroup',
                                    allowBlank: false,
                                    fieldLabel: 'Type *',
                                    itemId: 'taskType',
                                    width: 400,
                                    items: [
                                        {
                                            boxLabel: 'Bug',
                                            name: 'tasktype',
                                            inputValue: 'Bug',
                                            checked: true
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
                                    itemId: 'priority',
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
                                            inputValue: 'Normal',
                                            checked: true
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
                            itemId: 'inputTagsField',
                            name: 'tags',
                            width: 670,
                            style: {margin: '20px 20px 1px 20px'},
                            enableKeyEvents: true,
                            fieldLabel: 'Tags'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    items: [
                        {
                            xtype: 'tbtext',
                            text: 'Example: bug, message, timeout',
                            style: {marginLeft: '125px'}

                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'combobox',
                            id: 'createIssueAssignUser',
                            allowBlank: false,
                            store: 'AD.store.TotalUsers',
                            displayField: 'name',
                            queryMode: 'local',
                            valueField: 'id',
                            itemId: 'createWindowIssueAssignUser',
                            name: 'assignee',
                            fieldLabel: 'Assignee *',
                            flex: 1,
                            style: {margin: '20px 20px 1px 20px'}
                        },
                        {
                            xtype: 'numberfield',
                            allowBlank: false,
                            fieldLabel: 'Estimate *',
                            itemId: 'estimateField',
                            minValue: 0,
                            flex: 1,
                            style: {margin: '20px 20px 1px 20px'}
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    border: false,
                    listeners: {
                        'render': {
                            fn: function () {
                                this.body.on('click', this.handleClick, this);
                            },
                            single: true
                        }
                    },
                    handleClick: function () {
                        Ext.getCmp('createIssueAssignUser')
                            .setValue(_decodeSessionStorage('current_user').id);

                    },
                    items: [
                        {
                            xtype: 'tbtext',
                            text: '<span style="text-decoration: underline; ' +
                                'color: #4169E1; cursor: pointer;">assign to me</span>',
                            itemId: 'assignToMe',
                            style: {marginLeft: '125px'}

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
                            text: 'OK',
                            formBind: true,
                            disabled: true,
                            itemId: 'createButton',
                            style: {margin: '1px 20px 20px 20px'}
                        }
                    ]
                }
            ]
        }

    ]
});