Ext.define('AD.view.header.Header', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.head',
    border: false,
    itemId: 'head',
    initComponent: function () {
        this.items =[
            {
                xtype: 'panel',
                style: {background: 'white'},
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
            },
            {
                xtype: 'panel',
                style: {background: 'white'},
                itemId: 'bottomToolbar',
                border: false,
                layout: {
                    type: 'hbox',
                    align: 'stretch'
                },
                items:[
                    {
                        xtype: 'toolbar',
                        border: false,
                        flex: 1,
                        style: {
                            paddingRight: '50px',
                            background: 'white'
                        },
                        items: [
                            {
                                xtype: 'tbtext',
                                height: 22,
                                itemId: 'projectName',
                                border: 1,
                                style: {
                                    borderColor: 'black',
                                    fontSize: '2em',
                                    borderStyle: 'solid',
                                    background: '#ffffed',
                                    paddingRight: '2px'
                                }
                            },
                            {
                                xtype: 'tbtext',
                                height: 22,
                                text: 'projectDescription',
                                itemId: 'projectDescription',
                                style: {fontSize: '1.5em'}
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        itemId: 'mainPageTagsField',
                        border: false,
                        flex: 1
                    }
                ]
            }
        ];
        this.callParent();
    }
});




