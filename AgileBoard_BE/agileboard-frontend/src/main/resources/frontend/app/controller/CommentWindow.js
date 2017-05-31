Ext.define('AD.controller.CommentWindow', {
    extend: 'Ext.app.Controller',
    views: ['windows.CommentWindow', 'MainIssueContent'],
    refs: [
        {ref: 'CommentWindow', selector: 'commentWindow'},
        {ref: 'CommentWindowCommentField', selector: '#commentWindowCommentField'},
        {ref: 'Comments', selector: '#comments'},
        {ref: 'CommentWindowOkButton', selector: '#commentWindowOkButton'}
    ],

    init: function () {
        this.control({
            '#commentWindowOkButton': {click: this.comment}
        });
        this.callParent();
    },

    comment: function(){
        var me= this;
        Ext.Ajax.request({
            url: _path('comments'),
            method: 'POST',
            jsonData: {
                task: _decodeSessionStorage('current_issue').id,
                comment: me.getCommentWindowCommentField().getValue()
            },
            success: function () {
                me.getCommentWindow().close();
                _changeView('issueBrowser');
            }
        });
    }
});