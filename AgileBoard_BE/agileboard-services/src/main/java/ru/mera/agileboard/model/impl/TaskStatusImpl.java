package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.TaskStatus;

import java.sql.SQLException;

/**
 * Created by antfom on 18.02.2015.
 */
@DatabaseTable(tableName = "ab_task_statuses")
public class TaskStatusImpl extends AbstractABEntity implements TaskStatus {

    @DatabaseField(columnName = "task_status_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "task_status_name")
    private String name;
    @DatabaseField(columnName = "obsolete")
    private boolean obsolete = false;


    public TaskStatusImpl(String name) {
        this.name = name;
    }

    public TaskStatusImpl() {
    }

    public static Dao<TaskStatusImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<TaskStatusImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), TaskStatusImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), TaskStatusImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
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
            obsolete = true;
            getDao().update(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
