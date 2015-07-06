package ru.mera.agileboard.service;

import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.model.User;

import java.util.Optional;

/**
 * Created by antfom on 25.02.2015.
 */
public interface UserSessionService {
    Session getUserSession();

    Optional<Session> setUserSession(String auth_token);

    Session newUserSession(User user, Project project);
}
