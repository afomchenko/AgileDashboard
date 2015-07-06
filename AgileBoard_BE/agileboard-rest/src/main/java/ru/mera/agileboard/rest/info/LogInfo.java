package ru.mera.agileboard.rest.info;

import ru.mera.agileboard.model.TaskLog;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antfom on 06.03.2015.
 */
public class LogInfo {
    private int user;
    private int task;
    private int logged;
    private long date;

    public LogInfo() {
    }

    public LogInfo(TaskLog log) {
        this.user = log.getUser().getId();
        this.task = log.getTask().getId();
        this.logged = log.getLogged();
        this.date = log.getDate();
    }

    public static List<LogInfo> fromLogs(Collection<? extends TaskLog> logs) {
        return logs.stream().map(LogInfo::new).collect(Collectors.toList());
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getLogged() {
        return logged;
    }

    public void setLogged(int logged) {
        this.logged = logged;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "user=" + user +
                ", task=" + task +
                ", logged=" + logged +
                ", date=" + date +
                '}';
    }
}
