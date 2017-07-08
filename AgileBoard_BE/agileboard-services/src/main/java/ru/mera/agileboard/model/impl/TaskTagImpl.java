package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskTag;

import java.sql.SQLException;

/**
 * Created by antfom on 18.02.2015.
 */
@DatabaseTable(tableName = "ab_tags")
public class TaskTagImpl extends AbstractABEntity implements TaskTag {

    @DatabaseField(columnName = "tag_name")
    private String tag;

    @DatabaseField(columnName = "tag_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "task_id", foreign = true, foreignAutoRefresh = true)
    private TaskImpl task;

    public TaskTagImpl(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public TaskTagImpl(String tag) {
        this.tag = tag;
    }


    public TaskTagImpl() {
    }

    public static Dao<TaskTagImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<TaskTagImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), TaskTagImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), TaskTagImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    @Override
    public int hashCode() {
        if (getId() < 1) {
            throw new IllegalStateException("id is not assigned");
        }
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskTagImpl taskTag = (TaskTagImpl) o;

        if (getId() != taskTag.getId()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int getId() {
        return id;
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

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = (TaskImpl) task;
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
}
