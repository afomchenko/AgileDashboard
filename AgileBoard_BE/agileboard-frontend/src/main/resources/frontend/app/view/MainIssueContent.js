Ext.define('AD.view.MainIssueContent', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mainIssueContent',
    itemId: 'mainIssueContent',
    border: false,

    items: [
        {
            xtype: 'panel',
            itemId: 'textAboutIssue',
            flex: 4,
            border: false,
            //style: {paddingRight: '50px'},
            items: [
                {
                    xtype: 'panel',
                    itemId: 'otherTextAboutIssue',
                    border: false,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            itemId: 'details',
                            flex: 1,
                            border: false,
                            style: {paddingRight: '50px'},
                            items: [
                                {
                                    xtype: 'separator',
                                    border: false,
                                    text: 'Details'
                                },
                                {
                                    layout: {
                                        type: 'hbox',
                                        align: 'stretch'
                                    },
                                    border: false,
                                    items: [
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            flex: 1,
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Type:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'issueType',
                                                    flex: 2
                                                }
                                            ]
                                        },

                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            flex: 1,
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Priority:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'issuePriority',
                                                    flex: 2
                                                }
                                            ]
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            flex: 1,
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'State:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'issueState',
                                                    flex: 2
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    border: false,
                                    itemId: 'people',
                                    layout: {
                                        type: 'hbox',
                                        align: 'stretch'
                                    },
                                    items: [
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
                                            flex: 1,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Assignee:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'peopleAssignee',
                                                    flex: 1
                                                }
                                            ]
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
                                            flex: 1,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Reporter:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'peopleReporter',
                                                    flex: 1

                                                }
                                            ]
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
                                            flex: 1,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'ERL:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'erl',
                                                    flex: 2
                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    border: false,
                                    itemId: 'dates',
                                    style: {paddingTop: '8px'},
                                    layout: {
                                        type: 'hbox',
                                        align: 'stretch'
                                    },
                                    items: [
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
                                            flex: 1,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Created:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'timeCreated',
                                                    flex: 2
                                                }
                                            ]
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
                                            flex: 1,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Updated:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'timeUpdated',
                                                    flex: 2

                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    border: false,
                                    itemId: 'tagsField'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'testSteps',
                            flex: 1,
                            border: false,
                            items: [
                                {
                                    xtype: 'separator',
                                    border: false,
                                    text: 'Test steps'
                                },
                                {
                                    xtype:'panel',
                                    border: false,
                                    itemId: 'testStepsText'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'description',
                    border: false,
                    items: [
                        {
                            xtype: 'separator',
                            border: false,
                            text: 'Description'
                        },
                        {
                            xtype:'panel',
                            border: false,
                            itemId: 'descriptionText'
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
                    xtype: 'separator',
                    border: false,
                    text: 'Comments'
                },
                {
                    xtype: 'panel',
                    border: false,
                    itemId: 'comments',
                    style: {paddingRight: '18px'}
                }
            ]
        }
    ]
});