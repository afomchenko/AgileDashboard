package ru.mera.agileboard.db.impl;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.impl.*;

import java.sql.SQLException;


/**
 * Created by antfom on 09.02.2015.
 */
@Component(name = "ru.mera.bt.db.DBService", immediate = true)
@Service(value = ru.mera.agileboard.db.StorageService.class)
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
        init();
    }

    @Deactivate
    public void stop() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        if (!isCreated) {
            synchronized (this) {
                if (!isCreated) {
                    try {
                        connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USER, DATABASE_PASS);

//                        TableUtils.createTableIfNotExists(connectionSource, UserImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, ProjectImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, TaskTagImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, TaskTypeImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, TaskStatusImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, TaskPriorityImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, TaskImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, CommentImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, SessionImpl.class);
//
//                        TableUtils.createTableIfNotExists(connectionSource, TaskLogImpl.class);


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
