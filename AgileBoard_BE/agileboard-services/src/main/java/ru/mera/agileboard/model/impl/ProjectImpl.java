package ru.mera.agileboard.model.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.User;

import java.sql.SQLException;

/**
 * Created by antfom on 17.02.2015.
 */
@DatabaseTable(tableName = "ab_projects")
public class ProjectImpl extends AbstractABEntity implements Project {


    @DatabaseField(columnName = "project_id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "project_shortname")
    private String shortName;
    @DatabaseField(columnName = "project_name")
    private String name;
    @DatabaseField(columnName = "project_desc")
    private String desc;
    @DatabaseField(columnName = "obsolete")
    private boolean obsolete = false;

    public ProjectImpl(String shortName, String name, String desc) {
        this.shortName = shortName;
        this.desc = desc;
        this.name = name;
    }

    public ProjectImpl() {
    }

    public static Dao<ProjectImpl, Integer> getDao() {
        StorageService storage = StorageSingleton.getStorage();
        Dao<ProjectImpl, Integer> dao = DaoManager.lookupDao(storage.getConnection(), ProjectImpl.class);
        System.err.println(dao);
        if (dao == null) {
            try {
                dao = DaoManager.createDao(storage.getConnection(), ProjectImpl.class);
                DaoManager.registerDao(storage.getConnection(), dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProjectUser(User user) {
        ProjectUsers pu = new ProjectUsers(this, (UserImpl) user);
        pu.store();
    }

    public void deleteProjectUser(User user) {
        ;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectImpl project = (ProjectImpl) o;

        if (id != project.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
