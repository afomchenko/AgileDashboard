package ru.mera.agileboard.db.impl;

import com.j256.ormlite.db.MysqlDatabaseType;

class MySql6DbType extends MysqlDatabaseType {
    @Override
    protected String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public void loadDriver() {
        try {
            Class.forName(getDriverClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}