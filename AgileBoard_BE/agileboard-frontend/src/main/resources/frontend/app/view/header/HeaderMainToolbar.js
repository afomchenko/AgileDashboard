Ext.define('AD.view.header.HeaderMainToolbar', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.headerMainToolbar',
    itemId: 'headerMainToolbar',
    border: false,
    flex: 1,

    style: {
        paddingRight: '50px',
        background: 'white'
    },
    items: [
        {
            xtype: 'panel',
            border: false,
            html: '<b style="cursor: pointer; font-size: 2em">Agile Dashboard</b>',
            listeners: {
                'render': {
                    fn: function() {
                        this.body.on('click', this.handleClick, this);
                    },
                    single: true
                }
            },
            handleClick: function(){
                Ext.getStore('AD.store.Issues').getProxy().url = issuesByProjectAndUser();
                Ext.getStore('AD.store.Issues').load();
                _changeView('main');
            }
        },
        '->',
        {
            xtype: 'button',
            itemId: 'projectButton',
            text:'Projects',
            menu:[]
        },
        {
            xtype: 'button',
            itemId: 'issuesButton',
            text : 'Issues',
            menu:[
                {
                    text : 'My opened issues',
                    itemId: 'openedIssues'
                },

                {
                    text : 'My completed issues',
                    itemId: 'completedIssues'
                },

                {
                    text : 'My in progress',
                    itemId: 'inProgressIssues'
                },
                '-',
                {
                    text : 'Reported by me',
                    itemId: 'reportedByMe'
                },
                {
                    text : 'Commented by me',
                    itemId: 'commentedByMe'
                },
                {
                    text : 'Logged by me',
                    itemId: 'loggedByMe'
                }
            ]
        }
    ]
});
