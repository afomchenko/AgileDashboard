package ru.mera.agileboard.model.impl;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 * Created by antfom on 02.02.2015.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@DatabaseTable(tableName = "ab_users")
public class UserImpl extends AbstractABEntity implements User {

    @DatabaseField(columnName = "user_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "user_name", canBeNull = false, unique = true)
    private String name;
    @DatabaseField(columnName = "user_email", canBeNull = false, unique = true)
    private String email;
    @DatabaseField(columnName = "user_created", canBeNull = false)
    private long created;
    @DatabaseField(columnName = "obsolete")
    private boolean obsolete;


    public UserImpl(String name, String email) {
        this.name = name;
        this.email = email;
        this.created = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    public UserImpl() {
    }

    public static Dao<UserImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<UserImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), UserImpl.class);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), UserImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
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

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public boolean isObsolete() {
        return obsolete;
    }

    @Override
    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    @Override
    public int getId() {
        return id;
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

        UserImpl user = (UserImpl) o;

        if (getId() != user.getId()) {
            return false;
        }

        return true;
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
}
