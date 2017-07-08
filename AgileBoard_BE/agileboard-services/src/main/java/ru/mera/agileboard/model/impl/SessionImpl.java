package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.model.User;

import java.sql.SQLException;

/**
 * Created by antfom on 04.03.2015.
 */
@DatabaseTable(tableName = "ab_session")
public class SessionImpl extends AbstractABEntity implements Session {

    @DatabaseField(columnName = "session_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "auth_token", unique = true)
    private String token;
    @DatabaseField(columnName = "user_id", foreign = true, foreignAutoRefresh = true)
    private UserImpl user;
    @DatabaseField(columnName = "project_id", foreign = true, foreignAutoRefresh = true)
    private ProjectImpl project;
    @DatabaseField(columnName = "obsolete")
    private boolean obsolete;


    public SessionImpl() {
    }

    public static Dao<SessionImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<SessionImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), SessionImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), SessionImpl.class);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = (UserImpl) user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = (ProjectImpl) project;
    }

    @Override
    public int getId() {
        return 0;
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

}
