Ext.define('AD.view.IssueBrowser', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.issueBrowser',
    itemId: 'issueBrowser',
    autoScroll: true,
    defaults:{
        border: false
    },
    items: [
        {xtype: 'headerForIssue'},

        {xtype: 'lowerToolbar'},

        {
            xtype: 'panel',
            itemId: 'mainContent',
            border: false,
            style: {
                paddingLeft: '10px',
                paddingRight: '10px'
            },
            items: [
                {
                    xtype: 'panel',
                    itemId: 'aboutIssue',
                    border: false,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            itemId: 'textAboutIssue',
                            flex: 4,
                            border: false,
                            style: {paddingRight: '50px'},
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
                            itemId: 'infoAboutIssue',
                            flex: 1,
                            border: false,
                            items: [
                                {
                                    xtype: 'panel',
                                    border: false,
                                    itemId: 'people',
                                    items: [
                                        {
                                            xtype: 'separator',
                                            border: false,
                                            text: 'People'
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
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
                                            style: {paddingTop: '10px'},
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
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    border: false,
                                    itemId: 'dates',
                                    items: [
                                        {
                                            xtype: 'separator',
                                            border: false,
                                            text: 'Dates'
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Created:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'timeCreated',
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
                                            style: {paddingTop: '10px'},
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Updated:',
                                                    flex: 1
                                                },
                                                {
                                                    xtype: 'tbtext',
                                                    itemId: 'timeUpdated',
                                                    flex: 1

                                                }
                                            ]
                                        }
                                    ]
                                },
                                {
                                    xtype: 'panel',
                                    border: false,
                                    itemId: 'timeTracking',
                                    items: [
                                        {
                                            xtype: 'separator',
                                            border: false,
                                            text: 'Time tracking'
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            itemId: 'estimatedTimeBar',
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Estimated:',
                                                    flex: 1
                                                }
                                            ]
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            itemId: 'remainingTimeBar',
                                            style: {marginTop: '20px'},
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Remaining:',
                                                    flex: 1
                                                }
                                            ]
                                        },
                                        {
                                            layout: {
                                                type: 'hbox',
                                                align: 'stretch'
                                            },
                                            itemId: 'loggedTimeBar',
                                            style: {marginTop: '20px'},
                                            border: false,
                                            items: [
                                                {
                                                    xtype: 'label',
                                                    text: 'Logged:',
                                                    flex: 1
                                                }
                                            ]
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
        }
    ]
});
