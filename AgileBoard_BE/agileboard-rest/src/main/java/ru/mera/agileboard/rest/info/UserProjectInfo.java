package ru.mera.agileboard.rest.info;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by antfom on 20.02.2015.
 */
@XmlRootElement
@XmlType(propOrder = {"authtoken", "user", "project"})
public class UserProjectInfo {

    private UserInfo user;

    private ProjectInfo project;

    private String authtoken;


    public UserProjectInfo(UserInfo user, ProjectInfo project) {
        this.user = user;
        this.project = project;
    }

    public UserProjectInfo() {
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }
}
