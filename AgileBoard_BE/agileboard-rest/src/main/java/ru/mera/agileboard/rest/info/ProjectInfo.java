package ru.mera.agileboard.rest.info;

import ru.mera.agileboard.model.Project;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antfom on 19.02.2015.
 */
@XmlRootElement(name = "project")
@XmlType(propOrder = {"id", "shortname", "name", "desc"})

public class ProjectInfo {

    private int id;
    private String shortname;
    private String name;
    private String desc;

    public ProjectInfo() {
    }

    public ProjectInfo(Project project) {
        this.id = project.getId();
        this.shortname = project.getShortName();
        this.name = project.getName();
        this.desc = project.getDesc();
    }

    public static List<ProjectInfo> fromProjects(Collection<? extends Project> project) {
        return project.stream().map(ProjectInfo::new).collect(Collectors.toList());

    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
