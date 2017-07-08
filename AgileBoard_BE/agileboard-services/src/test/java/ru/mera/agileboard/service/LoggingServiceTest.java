package ru.mera.agileboard.service;

import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.ABEntityTest;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.StorageSingleton;
import ru.mera.agileboard.service.impl.LoggingServiceImpl;
import ru.mera.agileboard.service.impl.TaskServiceImpl;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LoggingServiceTest {

    private static LoggingService loggingService;
    private static User user;
    private static Task task;
    private int log;


    public LoggingServiceTest(int log) {
        this.log = log;
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ABEntityTest.initDB();
        StorageSingleton.init(new StorageServiceImpl());
        loggingService = new LoggingServiceImpl();
        user = new UserServiceImpl().findUserByID(4).get();
        task = new TaskServiceImpl().getTaskByID(1).get();

    }

    @Parameterized.Parameters
    public static List<Integer[]> paramGenerator() {
        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(new Integer[]{1});
        list.add(new Integer[]{5});
        list.add(new Integer[]{0});
        list.add(new Integer[]{-1});

        return list;
    }

    @Test
    public void testGetRecentLogByUser() throws Exception {
        List<TaskLog> taskLogs = loggingService.getRecentLogByUser(user);
        for (TaskLog taskLog : taskLogs) {
            assertNotNull(taskLog);
            assertNotNull(taskLog.getUser());
            assertNotNull(taskLog.getTask());
            assertTrue(taskLog.getId() > 0);
            assertEquals(user, taskLog.getUser());
            assertTrue(taskLog.getLogged() >= 0);
            assertTrue(taskLog.getDate() > 0);
            assertTrue(taskLog.getDate() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        }

    }

    @Test
    public void testGetLoggedByTask() throws Exception {
        TaskLog taskLog = loggingService.createLog(task, user, log, LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        Map<Task, Integer> taskLogs = loggingService.getLoggedByTask(Arrays.asList(task, task));
        for (Map.Entry<Task, Integer> entry : taskLogs.entrySet()) {
            assertNotNull(entry.getKey());
            assertEquals(task, entry.getKey());
            assertEquals(task.getName(), entry.getKey().getName());
            assertTrue(entry.getValue() >= log);
        }
        taskLog.delete();
    }

    @Test
    public void testGetLoggedByTaskSingle() throws Exception {
        TaskLog taskLog = loggingService.createLog(task, user, log, LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        int sum = loggingService.getLoggedSummaryByTask(task);
        assertTrue(sum >= log);

        taskLog.delete();
    }

    @Test
    public void testGetRecentLogSumByUser() throws Exception {
        TaskLog taskLog1 = loggingService.createLog(task, user, log, LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog taskLog2 = loggingService.createLog(task, user, log, LocalDateTime.now().minus(5, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog taskLog3 = loggingService.createLog(task, user, log, LocalDateTime.now().minus(8, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

        Map<Long, Integer> logs = loggingService.getRecentLogSumByUser(user);
        assertTrue(logs.size() >= 2);

        for (Map.Entry<Long, Integer> entry : logs.entrySet()) {
            assertTrue(entry.getKey() >= LocalDateTime.now().minus(7, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        }

        taskLog1.delete();
        taskLog2.delete();
        taskLog3.delete();
    }


    @Test
    public void testGetLogByUserDaily() throws Exception {
        TaskLog taskLog1 = loggingService.createLog(task, user, log, LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

        List<TaskLog> taskLogs = loggingService.getLogByUserDaily(user,
                LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        for (TaskLog taskLog : taskLogs) {
            assertNotNull(taskLog);
            assertNotNull(taskLog.getUser());
            assertNotNull(taskLog.getTask());
            assertTrue(taskLog.getId() > 0);
            assertEquals(user, taskLog.getUser());
            assertTrue(taskLog.getLogged() >= 0);
            assertTrue(taskLog.getDate() > 0);
            assertTrue(taskLog.getDate() == LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        }

        taskLog1.delete();
    }

    @Test
    public void testCreateLog() throws Exception {
        TaskLog taskLog = loggingService.createLog(task, user, log, LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        assertNotNull(taskLog);
        assertNotNull(taskLog.getUser());
        assertNotNull(taskLog.getTask());
        assertTrue(taskLog.getId() > 0);
        assertEquals(user, taskLog.getUser());
        assertEquals(task, taskLog.getTask());
        assertTrue(taskLog.getLogged() >= 0);

        if (log >= 0) {
            assertEquals(log, taskLog.getLogged());
        } else {
            assertEquals(0, taskLog.getLogged());
        }

        assertTrue(taskLog.getDate() > 0);
        assertTrue(taskLog.getDate() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

        taskLog.delete();
    }

}