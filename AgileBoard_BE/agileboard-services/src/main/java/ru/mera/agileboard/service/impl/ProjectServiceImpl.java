package ru.mera.agileboard.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.osgi.service.component.annotations.Component;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.ProjectImpl;
import ru.mera.agileboard.model.impl.ProjectUsers;
import ru.mera.agileboard.model.impl.UserImpl;
import ru.mera.agileboard.service.ProjectService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component(name = "ru.mera.agileboard.service.ProjectServiceComponent", service = ProjectService.class, immediate = true)
public class ProjectServiceImpl implements ProjectService {

    @Override
    public Project createProject(String shortName, String name, String desc) {
        Project project = new ProjectImpl(shortName, name, desc);
        project.store();
        return project;
    }

    @Override
    public List<Project> getProjects() {
        Dao<ProjectImpl, Integer> dao = ProjectImpl.getDao();

        try {
            List<Project> list = new ArrayList<>(dao.queryForAll());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    @Override
    public Optional<Project> getProjectByID(int projectID) {

        Project project = null;
        Dao<ProjectImpl, Integer> dao = ProjectImpl.getDao();

        try {
            project = dao.queryForId(projectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(project);

    }

    @Override
    public Optional<Project> getProjectByName(String projectName) {

        Project project = null;
        Dao<ProjectImpl, Integer> dao = ProjectImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("project_name", projectName);
            project = dao.queryForFieldValues(values).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(project);
    }

    @Override
    public Optional<Project> getProjectByShortName(String projectShortName) {

        Project project = null;
        Dao<ProjectImpl, Integer> dao = ProjectImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("project_shortname", projectShortName);
            project = dao.queryForFieldValues(values).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(project);
    }

    @Override
    public List<Project> getProjectsByUser(User user) {

        Dao<ProjectUsers, Integer> dao = ProjectUsers.getDao();
        QueryBuilder<ProjectUsers, Integer> queryBuilder = dao.queryBuilder();

        queryBuilder.selectColumns("project_id");

        try {
            queryBuilder.where().eq("user_id", user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Dao<ProjectImpl, Integer> pDao = ProjectImpl.getDao();
        QueryBuilder<ProjectImpl, Integer> pQueryBuilder = pDao.queryBuilder();

        try {
            pQueryBuilder.where().in("project_id", queryBuilder);
            return new ArrayList<>(pDao.query(pQueryBuilder.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<User> getUsersOfProject(Project project) {

        Dao<ProjectUsers, Integer> dao = ProjectUsers.getDao();
        QueryBuilder<ProjectUsers, Integer> queryBuilder = dao.queryBuilder();

        queryBuilder.selectColumns("user_id");

        try {
            queryBuilder.where().eq("project_id", project);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Dao<UserImpl, Integer> userDao = UserImpl.getDao();
        QueryBuilder<UserImpl, Integer> userQueryBuilder = userDao.queryBuilder();

        try {
            userQueryBuilder.where().in("user_id", queryBuilder);
            return new ArrayList<>(userDao.query(userQueryBuilder.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    public boolean isUserOfProject(Project project, User user) {
        Dao<ProjectUsers, Integer> dao = ProjectUsers.getDao();
        QueryBuilder<ProjectUsers, Integer> queryBuilder = dao.queryBuilder();

        try {
            queryBuilder.where().eq("project_id", project).and().eq("user_id", user);
            if (dao.queryForFirst(queryBuilder.prepare()) != null) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
