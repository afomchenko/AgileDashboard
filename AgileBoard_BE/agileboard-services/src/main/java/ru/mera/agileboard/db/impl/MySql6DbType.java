package ru.mera.agileboard.db.impl;

import com.j256.ormlite.db.MysqlDatabaseType;

class MySql6DbType extends MysqlDatabaseType {
    @Override
    protected String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public void loadDriver() {
        // Do nothing:
        // Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'.
        // The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
        try {
            Class.forName(getDriverClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
