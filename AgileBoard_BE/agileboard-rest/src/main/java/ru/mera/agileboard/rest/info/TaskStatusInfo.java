package ru.mera.agileboard.rest.info;

import ru.mera.agileboard.model.TaskStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antfom on 05.03.2015.
 */
public class TaskStatusInfo {
    private int id;
    private String status;

    public TaskStatusInfo(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public TaskStatusInfo() {
    }

    public TaskStatusInfo(TaskStatus taskStatus) {
        this.id = taskStatus.getId();
        this.status = taskStatus.getName();
    }

    public static List<TaskStatusInfo> fromStatuses(Collection<? extends TaskStatus> statuses) {
        List<TaskStatusInfo> list = statuses.stream().map(TaskStatusInfo::new).collect(Collectors.toList());
        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
