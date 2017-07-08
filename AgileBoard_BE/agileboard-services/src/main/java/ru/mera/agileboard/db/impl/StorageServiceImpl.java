package ru.mera.agileboard.db.impl;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import ru.mera.agileboard.db.StorageService;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by antfom on 09.02.2015.
 */
@Component(name = "ru.mera.bt.db.DBService", service = StorageService.class, immediate = true)
public class StorageServiceImpl implements StorageService {

    private static ConnectionSource connectionSource;
    private String DATABASE_URL;
    private String DATABASE_USER;
    private String DATABASE_PASS;
    private volatile boolean isCreated = false;


    public StorageServiceImpl() {
    }


    public StorageServiceImpl(String DATABASE_URL, String DATABASE_USER, String DATABASE_PASS) {
        this.DATABASE_URL = DATABASE_URL;
        this.DATABASE_USER = DATABASE_USER;
        this.DATABASE_PASS = DATABASE_PASS;
    }

    @Activate
    public void start(BundleContext context) {
        DATABASE_URL = context.getProperty("ru.mera.agileboard.db.url");
        DATABASE_USER = context.getProperty("ru.mera.agileboard.db.login");
        DATABASE_PASS = context.getProperty("ru.mera.agileboard.db.password");

        System.out.println("storage service started");
    }

    @Deactivate
    public void stop() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        if (!isCreated) {
            synchronized (this) {
                if (!isCreated) {
                    try {
                        connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USER, DATABASE_PASS, new MySql6DbType());
                        isCreated = true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public ConnectionSource getConnection() {
        init();
        return connectionSource;
    }
}
