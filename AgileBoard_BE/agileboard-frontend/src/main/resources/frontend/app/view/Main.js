var today = new Date();
function _getDay(n) {
    return Ext.Date.format(Ext.Date.add(today, Ext.Date.DAY, -n), "d M")
}
Ext.define('AD.view.Main', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.main',
    border: false,
    itemId: 'main',
    items: [
        {
            xtype: 'head',
            region: 'north'
        },
        {
            xtype: 'panel',
            itemId: 'allGrids',
            style: {
                paddingLeft: '10px',
                paddingRight: '10px'
            },
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            border: false,
            items: [
                {
                    xtype: 'panel',
                    itemId: 'timeTrackingForm',
                    flex: 1,
                    style: {paddingRight: '50px'},
                    border: false,
                    items: [
                        {
                            xtype: 'panel',
                            itemId: 'timeTrackingTopGrid',
                            border: false,
                            items: [
                                {
                                    xtype: 'separator',
                                    border: false,
                                    text: 'Time Tracking'
                                },
                                {
                                    xtype: 'grid',
                                    store: 'AD.store.Users',
                                    itemId: 'timeTrackingGrid',
                                    height: 200,
                                    columns: [
                                        {text: 'User', dataIndex: 'name', flex: 3},
                                        {text: _getDay(6), dataIndex: '6', flex: 1, align: 'center'},
                                        {text: _getDay(5), dataIndex: '5', flex: 1, align: 'center'},
                                        {text: _getDay(4), dataIndex: '4', flex: 1, align: 'center'},
                                        {text: _getDay(3), dataIndex: '3', flex: 1, align: 'center'},
                                        {text: _getDay(2), dataIndex: '2', flex: 1, align: 'center'},
                                        {text: _getDay(1), dataIndex: '1', flex: 1, align: 'center'},
                                        {text: _getDay(0), dataIndex: '0', flex: 1, align: 'center'},
                                        {text: 'Total', dataIndex: 'summary', flex: 1, align: 'center'}
                                    ],
                                    listeners: {
                                        viewready: function (grid) {
                                            grid.getSelectionModel().select(0);
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'timeTrackingBottomPanel',
                            border: false
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'issuesGrid',
                    flex: 1,
                    border: false,
                    items: [
                        {
                            xtype: 'separator',
                            border: false,
                            text: 'My Issues'
                        },
                        {
                            xtype: 'grid',
                            store: 'AD.store.Issues',
                            height: 475,
                            columns: [
                                {
                                    text: 'T',
                                    dataIndex: 'tasktype',
                                    flex: 1,
                                    align: 'center',
                                    renderer: function (v) {
                                        return '<img src="app/icons/' + v.toString() + '.png" />';
                                    }
                                },
                                {
                                    text: 'P',
                                    dataIndex: 'priority',
                                    flex: 1,
                                    align: 'center',
                                    renderer: function (v) {
                                        return '<img src="app/icons/' + v.toString() + '.png" />';
                                    }
                                },
                                {

                                    itemId: 'issueNumber',
                                    text: '#',
                                    dataIndex: 'id',
                                    flex: 1.5,
                                    align: 'center',
                                    renderer: function (v, meta, record) {
                                        return '<b style="text-decoration:  underline;  cursor: pointer; color: #4169E1">'
                                            + record.data.project
                                            + '-' + record.data.id;
                                    }
                                },
                                {text: 'Name', dataIndex: 'name', flex: 6},
                                {text: 'State', dataIndex: 'status', flex: 3, align: 'center'},
                                {
                                    itemId: 'issueProgress',
                                    text: 'Progress',
                                    dataIndex: 'estimated',
                                    flex: 3,
                                    align: 'center',
                                    renderer: function (v, meta, record) {
                                        return (record.data.logged / record.data.estimated) > 1 ? '>100%' :
                                        Math.round((record.data.logged / record.data.estimated) * 100) + '%';
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
});