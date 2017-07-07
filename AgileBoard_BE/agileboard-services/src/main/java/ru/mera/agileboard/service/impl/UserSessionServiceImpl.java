package ru.mera.agileboard.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.SessionImpl;
import ru.mera.agileboard.service.UserSessionService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by antfom on 25.02.2015.
 */
@Component(name = "ru.mera.agileboard.service.UserSessionComponent", service = UserSessionService.class, immediate = true)
public class UserSessionServiceImpl implements UserSessionService {

    ThreadLocal<Session> userSession = new ThreadLocal<>();

    @Activate
    public void start() {
        System.err.println("user session service started");
    }

    @Deactivate
    public void stop() {
    }

    @Override
    public Session getUserSession() {
        return userSession.get();
    }

    @Override
    public Optional<Session> setUserSession(String auth_token) {
        Dao<SessionImpl, Integer> sessionDao = SessionImpl.getDao();
        Session session = null;

        QueryBuilder<SessionImpl, Integer> qSession = sessionDao.queryBuilder();
        try {
            qSession.where().eq("auth_token", auth_token);
            session = sessionDao.queryForFirst(qSession.prepare());
            if (session != null) {
                userSession.set(session);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(session);
    }

    @Override
    public Session newUserSession(User user, Project project) {
        SessionImpl s = new SessionImpl();
        s.setUser(user);
        s.setProject(project);
        s.setToken(UUID.randomUUID().toString());
        s.store();
        userSession.set(s);
        return s;
    }
}
