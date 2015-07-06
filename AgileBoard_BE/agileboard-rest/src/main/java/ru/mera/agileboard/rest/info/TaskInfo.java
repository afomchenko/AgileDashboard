package ru.mera.agileboard.rest.info;

import ru.mera.agileboard.model.Task;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement
@XmlType(propOrder = {"id", "project", "name", "description", "teststeps", "created",
        "updated", "status", "tasktype", "priority", "creator", "assignee", "estimated", "logged"})
public class TaskInfo {
    private int id;
    private String project;
    private String name;
    private String description;
    private String teststeps;
    private long created;
    private long updated;
    private String status;
    private String tasktype;
    private String priority;
    private int creator;
    private int assignee;
    private int estimated;
    private int logged;

    public TaskInfo() {
    }

    public TaskInfo(Task task) {
        id = task.getId();
        project = task.getProject().getShortName();
        name = task.getName();
        description = task.getDescription();
        teststeps = task.getTestSteps();
        created = task.getCreated();
        updated = task.getUpdated();
        status = task.getStatus().getName();
        tasktype = task.getType().getName();
        priority = task.getPriority().getName();
        creator = task.getCreator().getId();
        assignee = task.getAssignee().getId();
        estimated = task.getEstimated();
    }

    public TaskInfo(Task task, int logged) {
        id = task.getId();
        project = task.getProject().getShortName();
        name = task.getName();
        description = task.getDescription();
        teststeps = task.getTestSteps();
        created = task.getCreated();
        updated = task.getUpdated();
        status = task.getStatus().getName();
        tasktype = task.getType().getName();
        priority = task.getPriority().getName();
        creator = task.getCreator().getId();
        assignee = task.getAssignee().getId();
        estimated = task.getEstimated();
        this.logged = logged;
    }

    public static List<TaskInfo> fromTasks(Collection<? extends Task> tasks) {
        List<TaskInfo> list = tasks.stream().map(TaskInfo::new).collect(Collectors.toList());
        return list;
    }

    public static List<TaskInfo> fromTasks(Map<? extends Task, Integer> tasks) {
        List<TaskInfo> list = tasks.entrySet().stream().map(e -> new TaskInfo(e.getKey(), e.getValue())).collect(Collectors.toList());
        return list;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeststeps() {
        return teststeps;
    }

    public void setTeststeps(String teststeps) {
        this.teststeps = teststeps;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getAssignee() {
        return assignee;
    }

    public void setAssignee(int assignee) {
        this.assignee = assignee;
    }

    public int getEstimated() {
        return estimated;
    }

    public void setEstimated(int estimated) {
        this.estimated = estimated;
    }

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teststeps='" + teststeps + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", status='" + status + '\'' +
                ", type='" + tasktype + '\'' +
                ", priority=" + priority +
                ", creator=" + creator +
                ", assignee=" + assignee +
                ", estimated=" + estimated +
                '}';
    }

}
