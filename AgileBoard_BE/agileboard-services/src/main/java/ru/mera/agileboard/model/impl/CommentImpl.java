package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.Comment;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by antfom on 17.02.2015.
 */
@DatabaseTable(tableName = "ab_comments")
public class CommentImpl extends AbstractABEntity implements Comment {


    @DatabaseField(columnName = "comment_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "task_id", foreign = true, foreignColumnName = "task_id")
    private TaskImpl task;
    @DatabaseField(columnName = "user_id", foreign = true, foreignColumnName = "user_id")
    private UserImpl user;
    @DatabaseField(columnName = "comment_date")
    private long created;
    @DatabaseField(columnName = "comment", dataType = DataType.LONG_STRING)
    private String comment;


    public CommentImpl(Task task, User user, String comment) {
        this.task = (TaskImpl) task;
        this.user = (UserImpl) user;
        this.comment = comment;
        created = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }


    public CommentImpl() {
    }

    public static Dao<CommentImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<CommentImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), CommentImpl.class);
        System.err.println(dao);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), CommentImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public User getUser() {
        return user;
    }

    public long getCreated() {
        return created;
    }

    @Override
    public String getComment() {
        return comment;
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
    public String toString() {
        return "CommentImpl{" +
                "id=" + id +
                ", task=" + task +
                ", user=" + user +
                ", created=" + created +
                '}';
    }
}
