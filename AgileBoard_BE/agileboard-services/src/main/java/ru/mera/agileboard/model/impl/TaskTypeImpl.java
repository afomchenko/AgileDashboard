package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.TaskType;

import java.sql.SQLException;

/**
 * Created by antfom on 18.02.2015.
 */
//@Table(name = "ab_task_types", nullable = true)
@DatabaseTable(tableName = "ab_task_types")
public class TaskTypeImpl extends AbstractABEntity implements TaskType {


    @DatabaseField(columnName = "task_type_id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "task_type_name")
    private String name;

    @DatabaseField(columnName = "obsolete")
    private boolean obsolete = false;

    public TaskTypeImpl(int id, String name, boolean obsolete) {
        this.id = id;
        this.name = name;
        this.obsolete = obsolete;
    }

    public TaskTypeImpl(String name) {
        this.name = name;
    }

    public TaskTypeImpl() {
    }

    public static Dao<TaskTypeImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<TaskTypeImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), TaskTypeImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), TaskTypeImpl.class);
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
