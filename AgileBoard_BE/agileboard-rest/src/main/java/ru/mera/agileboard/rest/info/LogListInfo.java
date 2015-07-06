package ru.mera.agileboard.rest.info;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by antfom on 06.03.2015.
 */
@XmlRootElement(name = "project")
@XmlType(propOrder = {"user", "logs"})

public class LogListInfo {

    private String user;
    private List<LogSum> logs;

    public LogListInfo() {
    }

    public LogListInfo(String userName, Map<Long, Integer> logs) {
        this.user = userName;
        this.logs = logs.entrySet().stream().map(e -> new LogSum(e.getKey(), e.getValue())).collect(Collectors.toList());
        System.err.println(logs);
    }

    public List<LogSum> getLogs() {
        return logs;
    }

    public void setLogs(List<LogSum> logs) {
        this.logs = logs;
    }

    public String getUser() {
        return user;

    }

    public void setUser(String user) {
        this.user = user;
    }

    public static class LogSum {
        private long date;
        private int sum;

        public LogSum() {
        }

        public LogSum(long date, int sum) {
            this.date = date;
            this.sum = sum;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "LogSum{" +
                    "date=" + date +
                    ", sum=" + sum +
                    '}';
        }
    }
}
