package ru.mera.agileboard.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.TaskImpl;
import ru.mera.agileboard.model.impl.TaskLogImpl;
import ru.mera.agileboard.service.LoggingService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by antfom on 05.03.2015.
 */
@Component(name = "ru.mera.agileboard.service.LoggingComponent", service = LoggingService.class, immediate = true)
public class LoggingServiceImpl implements LoggingService {

    @Activate
    public void start() {
    }

    @Deactivate
    public void stop() {
    }

    public List<TaskLog> getRecentLogByUser(User user) {
        Dao<TaskLogImpl, Integer> dao = TaskLogImpl.getDao();

        try {
            QueryBuilder<TaskLogImpl, Integer> qLog = dao.queryBuilder();
            qLog.where()
                    .eq("user_id", user.getId())
                    .and()
                    .gt("log_date", LocalDateTime.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
            ArrayList<TaskLog> list = new ArrayList<>(dao.query(qLog.prepare()));

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int getLoggedSummaryByTask(Task task) {
        Dao<TaskLogImpl, Integer> dao = TaskLogImpl.getDao();

        try {
            QueryBuilder<TaskLogImpl, Integer> qLog = dao.queryBuilder();
            qLog.selectRaw("SUM(logged)").where().eq("task_id", task.getId());
            System.err.println(qLog.prepare().toString());
            String result = dao.queryRaw(qLog.prepareStatementString()).getFirstResult()[0];
            if (result != null) {
                return Integer.parseInt(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<Long, Integer> getRecentLogSumByUser(User user) {
        Dao<TaskLogImpl, Integer> dao = TaskLogImpl.getDao();

        try {
            QueryBuilder<TaskLogImpl, Integer> qLog = dao.queryBuilder();
            qLog.selectRaw("log_date, SUM(logged) ").groupBy("log_date").where()
                    .eq("user_id", user.getId())
                    .and().gt("log_date", LocalDateTime.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

            System.err.println(qLog.prepare().toString());
            List<String[]> results = dao.queryRaw(qLog.prepareStatementString()).getResults();

            Map<Long, Integer> logs = new HashMap<>();
            for (String[] result : results) {
                logs.put(Long.parseLong(result[0]), Integer.parseInt(result[1]));
            }

            return logs;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public List<TaskLog> getLogByUserDaily(User user, long date) {
        Dao<TaskLogImpl, Integer> dao = TaskLogImpl.getDao();

        try {
            QueryBuilder<TaskLogImpl, Integer> qLog = dao.queryBuilder();
            qLog.where().eq("user_id", user.getId()).and().eq("log_date", date);
            ArrayList<TaskLog> list = new ArrayList<>(dao.query(qLog.prepare()));

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public TaskLog createLog(Task task, User user, int logged, long date) {
        if (logged < 0) {
            logged = 0;
        }
        TaskLog log = new TaskLogImpl(task, user, logged, date);
        log.store();
        return log;
    }

    public Map<Task, Integer> getLoggedByTask(Collection<? extends Task> tasks) {
        Map<Task, Integer> map = new HashMap<>();
        if (tasks.size() < 1) {
            return map;
        }
        Dao<TaskLogImpl, Integer> dao = TaskLogImpl.getDao();
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();

        try {
            QueryBuilder<TaskImpl, Integer> qLog = taskDao.queryBuilder();
            qLog.selectRaw("ab_tasks.task_id, IFNULL(SUM(ab_task_logs.logged),0) ")
                    .leftJoin(dao.queryBuilder())
                    .groupBy("task_id")
                    .where()
                    .in("task_id", tasks.stream().map(Task::getId).collect(Collectors.toList()));
            System.err.println(qLog.prepare().toString());
            List<String[]> result = dao.queryRaw(qLog.prepareStatementString()).getResults();


            for (String[] r : result) {

                int taskid = 0;
                int count = 0;

                if (r != null) {
                    taskid = Integer.parseInt(r[0]);
                    count = Integer.parseInt(r[1]);
                }

                for (Task task : tasks) {
                    if (task.getId() == taskid) {
                        map.put(task, count);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
