Ext.define('AD.view.Login', {
    extend: 'Ext.form.Panel',
    alias: 'widget.loginForm',
    itemId: 'loginForm',
    layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
    },
    initComponent: function () {
        this.items = [
            {
                xtype: 'fieldset',
                itemId: 'loginFormFieldSet',
                width: 400,
                padding: '30',
                items: [
                    {
                        xtype: 'tbtext',
                        border: false,
                        text: 'Agile Dashboard',
                        style: {
                            textAlign: 'center',
                            marginBottom: '35px',
                            marginTop: '5px',
                            fontSize: '3em',
                            fontWeight: 'bold'
                        }
                    },
                    {
                        xtype: 'combo',
                        itemId: 'loginFormProjectCombo',
                        name: 'project',
                        store: 'AD.store.Projects',
                        displayField: 'name',
                        valueField: 'id',
                        style: {marginBottom: '10px'},
                        editable: false,
                        fieldLabel: 'Choose Project',
                        anchor: '100%'

                    },
                    {
                        xtype: 'textfield',
                        itemId: 'loginFormLoginField',
                        name: 'name',
                        anchor: '100%',
                        margin: '0 0 15 0',
                        enableKeyEvents: true,
                        fieldLabel: 'Username'
                    }
                ]
            }
        ];
        this.callParent();
    }
});