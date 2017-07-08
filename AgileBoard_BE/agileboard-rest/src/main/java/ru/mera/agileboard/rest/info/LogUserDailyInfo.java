package ru.mera.agileboard.rest.info;

import ru.mera.agileboard.model.TaskLog;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antfom on 06.03.2015.
 */
public class LogUserDailyInfo {

    private long date;
    private List<LogUserDailyTasks> tasks;
    private int total = 0;

    public LogUserDailyInfo() {
    }

    public LogUserDailyInfo(long date, List<TaskLog> tasks) {
        this.date = date;
        this.tasks = tasks.stream().map(LogUserDailyTasks::new).collect(Collectors.toList());
        if (tasks.size() > 0) {
            this.total = tasks.stream().mapToInt(TaskLog::getLogged).reduce((a, b) -> a + b).getAsInt();
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<LogUserDailyTasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<LogUserDailyTasks> tasks) {
        this.tasks = tasks;
    }

    public static class LogUserDailyTasks {
        private int task;
        private String project;
        private int logged = 0;

        public LogUserDailyTasks() {
        }


        public LogUserDailyTasks(TaskLog log) {
            this.task = log.getTask().getId();
            this.project = log.getTask().getProject().getShortName();
            this.logged = log.getLogged();
        }

        public int getTask() {
            return task;
        }

        public void setTask(int task) {
            this.task = task;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public int getLogged() {
            return logged;
        }

        public void setLogged(int logged) {
            this.logged = logged;
        }

        @Override
        public String toString() {
            return "LogUserDailyTasks{" +
                    "task=" + task +
                    ", project='" + project + '\'' +
                    ", tasklogged=" + logged +
                    '}';
        }
    }

}
