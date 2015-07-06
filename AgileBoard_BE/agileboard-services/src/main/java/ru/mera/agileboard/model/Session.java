package ru.mera.agileboard.model;

/**
 * Created by antfom on 04.03.2015.
 */
public interface Session extends ABEntity {

    boolean isObsolete();

    void setObsolete(boolean obsolete);

    String getToken();

    void setToken(String token);

    User getUser();

    void setUser(User user);

    Project getProject();

    void setProject(Project project);

    int getId();

}
