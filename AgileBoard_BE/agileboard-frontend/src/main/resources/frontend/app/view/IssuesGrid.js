Ext.define('AD.view.IssuesGrid', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.issuesGrid',
    itemId: 'issuesGrid',
    autoScroll: true,
    items: [
        {
            xtype: 'headerForIssue',
            border: false
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
                    xtype: 'panel',
                    itemId: 'issuesWestGrid',
                    flex: 1,
                    style: {
                        paddingLeft: '10px'
                    },
                    border: false
                },
                {
                    xtype: 'panel',
                    itemId: 'issueGridMainContent',
                    flex: 5,
                    style: {
                        paddingLeft: '20px',
                        paddingRight: '10px'
                    },
                    border: false,
                    items: [
                        {
                            xtype: 'lowerToolbar',
                            border: false
                        },

                        {
                            xtype: 'mainIssueContent'
                        }
                    ]
                }

            ]
        }
    ]
});