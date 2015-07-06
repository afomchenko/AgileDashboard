package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import ru.mera.agileboard.db.StorageService;

import java.sql.SQLException;

/**
 * Created by antfom on 02.03.2015.
 */
@DatabaseTable(tableName = "ab_project_users")
public class ProjectUsers extends AbstractABEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = "project_id", foreignColumnName = "project_id")
    private ProjectImpl project;

    @DatabaseField(foreign = true, columnName = "user_id", foreignColumnName = "user_id")
    private UserImpl user;

    public ProjectUsers(ProjectImpl project, UserImpl user) {
        this.project = project;
        this.user = user;
    }


    public ProjectUsers() {
    }

    public static Dao<ProjectUsers, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<ProjectUsers, Integer> dao = DaoManager.lookupDao(storage.getConnection(), ProjectUsers.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), ProjectUsers.class);
                DaoManager.registerDao(storage.getConnection(), dao);
                TableUtils.createTableIfNotExists(storage.getConnection(), ProjectUsers.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    @Override
    public int getId() {
        return id;
    }

    protected int update() {
        try {
            getDao().update(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }

    protected int insert() {
        try {
            getDao().create(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }

    @Override
    public boolean delete() {
        try {
            getDao().delete(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
