package ru.mera.agileboard.service;

import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by antfom on 05.03.2015.
 */
public interface LoggingService {

    List<TaskLog> getRecentLogByUser(User user);

    int getLoggedSummaryByTask(Task task);

    Map<Long, Integer> getRecentLogSumByUser(User user);

    List<TaskLog> getLogByUserDaily(User user, long date);

    TaskLog createLog(Task task, User user, int logged, long date);

    Map<Task, Integer> getLoggedByTask(Collection<? extends Task> tasks);
}

