Ext.define('AD.view.header.HeaderForIssue', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.headerForIssue',
    itemId: 'headerForIssue',
    initComponent: function () {
        this.items =[
            {
                xtype: 'panel',
                itemId: 'topToolbar',
                border: false,
                layout: {
                    type: 'hbox',
                    align: 'stretch'
                },
                items:[
                    {
                        xtype: 'headerMainToolbar'
                    },
                    {
                        xtype: 'toolbar',
                        style: {background: 'white'},
                        border: false,
                        flex: 1,
                        items: [
                            {
                                itemId: 'createIssueButton',
                                text: 'Create Issue'
                            },
                            {
                                itemId: 'commentButton',
                                text: 'Comment'
                            },
                            {
                                itemId: 'logButton',
                                text: 'Log'
                            },
                            {
                                itemId: 'assignButton',
                                text: 'Assign'
                            },
                            '->',
                            {
                                itemId: 'searchField',
                                xtype: 'textfield',
                                name: 'field',
                                emptyText: 'Search'
                            },
                            {
                                itemId: 'profileButton',
                                xtype: 'button',
                                text: _decodeSessionStorage('current_user').name,
                                icon: 'app/icons/User.png',
                                menu:[
                                    {
                                        itemId: 'logout',
                                        text:'Logout'
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ];
        this.callParent();
    }
});
