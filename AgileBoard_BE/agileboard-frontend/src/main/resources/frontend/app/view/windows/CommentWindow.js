Ext.define('AD.view.windows.CommentWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.commentWindow',
    itemId: 'commentWindow',
    title: 'Comment',
    resizable: false,
    modal : true,
    items:[
        {
            xtype: 'form',
            items: [
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
                            allowBlank: false,
                            height: 150,
                            itemId: 'commentWindowCommentField',
                            width: 460,
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
                            itemId: 'commentWindowOkButton',
                            text: 'Comment',
                            style: {margin: '0 20px 10px 20px'}
                        }
                    ]
                }
            ]
        }
    ]
});