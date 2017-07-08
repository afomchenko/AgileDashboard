package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Created by antfom on 05.03.2015.
 */
@DatabaseTable(tableName = "ab_task_logs")
public class TaskLogImpl extends AbstractABEntity implements ru.mera.agileboard.model.TaskLog {

    @DatabaseField(columnName = "log_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "task_id", foreign = true, foreignAutoRefresh = true)
    private TaskImpl task;
    @DatabaseField(columnName = "user_id", foreign = true, foreignAutoRefresh = true)
    private UserImpl user;


    @DatabaseField(columnName = "log_date")
    private long date;
    @DatabaseField(columnName = "logged")
    private int logged;

    public TaskLogImpl() {
    }

    public TaskLogImpl(Task task, User user, int logged) {
        this.task = (TaskImpl) task;
        this.user = (UserImpl) user;
        this.logged = logged;
        this.date = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    public TaskLogImpl(Task task, User user, int logged, long date) {
        this.task = (TaskImpl) task;
        this.user = (UserImpl) user;
        this.logged = logged;
        this.date = date;
    }

    public static Dao<TaskLogImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<TaskLogImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), TaskLogImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), TaskLogImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
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
    public Task getTask() {
        return task;
    }

    @Override
    public void setTask(Task task) {
        this.task = (TaskImpl) task;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = (UserImpl) user;
    }

    @Override
    public long getDate() {
        return date;
    }

    @Override
    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int getLogged() {
        return logged;
    }

    @Override
    public void setLogged(int logged) {
        this.logged = logged;
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
            Dao<TaskLogImpl, Integer> dao = getDao();
            QueryBuilder<TaskLogImpl, Integer> qLog = dao.queryBuilder();
            qLog.where().eq("task_id", getTask().getId())
                    .and().eq("user_id", getUser().getId())
                    .and().eq("log_date", date);
            TaskLog log = dao.queryForFirst(qLog.prepare());
            if (log != null) {
                log.setLogged(logged + log.getLogged());
                logged = log.getLogged();
                id = log.getId();
                log.store();
            } else {
                dao.create(this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getId();
    }
}

