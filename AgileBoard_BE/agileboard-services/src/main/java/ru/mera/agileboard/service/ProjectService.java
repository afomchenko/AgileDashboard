package ru.mera.agileboard.service;

import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by antfom on 19.02.2015.
 */
public interface ProjectService {
    Project createProject(String shortName, String name, String desc);

    List<Project> getProjects();

    Optional<Project> getProjectByID(int projectID);

    Optional<Project> getProjectByName(String projectName);

    Optional<Project> getProjectByShortName(String projectShortName);

    List<Project> getProjectsByUser(User user);

    List<User> getUsersOfProject(Project project);

    boolean isUserOfProject(Project project, User user);

}
