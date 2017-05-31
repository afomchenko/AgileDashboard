Ext.define('AD.view.header.LowerToolbar', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.lowerToolbar',
    itemId: 'lowerToolbar',
    autoScroll: true,
    border: false,
    layout: {
        type: 'hbox',
        align: 'stretch'
    },
    items: [
        {
            xtype: 'toolbar',
            border: false,
            height: 22,
            flex: 4,
            style: {
                paddingRight: '50px',
                background: 'white'
            },
            items: [
                {
                    xtype: 'tbtext',
                    height: 22,
                    itemId: 'issueName',
                    border: 1,
                    style: {
                        borderColor: 'black',
                        fontSize: '2em',
                        borderStyle: 'solid',
                        background: '#ffffed',
                        padding: '2px'
                    }
                },
                {
                    xtype: 'tbtext',
                    height: 22,
                    text: 'issueDescription',
                    itemId: 'issueDescription',
                    style: {fontSize: '1.5em'}
                }
            ]
        },
        {
            xtype: 'toolbar',
            border: false,
            style: {background: 'white'},
            flex: 1,
            items: [
                '->',
                {
                    itemId: 'editIssueButton',
                    text: 'Edit'
                },
                {
                    itemId: 'workflowButton',
                    text: 'Workflow'
                }
            ]
        }
    ]
});