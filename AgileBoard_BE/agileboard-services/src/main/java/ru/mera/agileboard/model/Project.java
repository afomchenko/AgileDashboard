package ru.mera.agileboard.model;

/**
 * Created by antfom on 17.02.2015.
 */
public interface Project extends ABEntity {

    int getId();

    String getName();

    void setName(String name);

    String getDesc();

    void setDesc(String desc);

    String getShortName();

    void setShortName(String shortName);

    void deleteProjectUser(User user);

    void addProjectUser(User user);
}
