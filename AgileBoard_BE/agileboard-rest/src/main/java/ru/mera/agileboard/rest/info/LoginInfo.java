package ru.mera.agileboard.rest.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antfom on 20.02.2015.
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginInfo {


    private String project;

    private String name;

    public LoginInfo() {
    }

    public LoginInfo(String projectID, String userName) {
        this.project = projectID;
        this.name = userName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {

        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "project=" + project +
                ", name='" + name + '\'' +
                '}';
    }
}
