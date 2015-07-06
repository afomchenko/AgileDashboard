package ru.mera.agileboard.model;

import com.j256.ormlite.table.TableUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.mera.agileboard.db.StorageService;
import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.impl.*;
import ru.mera.agileboard.service.LoggingService;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.impl.LoggingServiceImpl;
import ru.mera.agileboard.service.impl.ProjectServiceImpl;
import ru.mera.agileboard.service.impl.TaskServiceImpl;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.UUID;

public class ABEntityTest {

    @BeforeClass
    public static void initDB() throws Exception {


        StorageService storageService = new StorageServiceImpl("jdbc:mysql://localhost:3306/agileboard_db?autoReconnect=true", "root", "admin");
        UserService userService = new UserServiceImpl();
        ProjectService projectService = new ProjectServiceImpl();
        TaskService taskService = new TaskServiceImpl();
        LoggingService loggingService = new LoggingServiceImpl();


        StorageSingleton.init(storageService);
        TableUtils.dropTable(storageService.getConnection(), UserImpl.class, true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), UserImpl.class);


        User u1 = userService.createUser("user", "user@email");
        User u2 = userService.createUser("admin", "firstuser@email");
        User u3 = userService.createUser("Second User", "seconduser@email");
        User u4 = userService.createUser("Third User", "thirduser@email");
        User u5 = userService.createUser("Fourth User", "fourthuser@email");
        User u6 = userService.createUser("Fifth User", "fifthuser@email");
        User u7 = userService.createUser("Sixth User", "sixthuser@email");
        User u8 = userService.createUser("Seventh User", "seventhuser@email");
        User u9 = userService.createUser("Eighth User", "eighthuser@email");
        User u10 = userService.createUser("Nineth User", "ninethuser@email");


        TableUtils.dropTable(storageService.getConnection(), ProjectImpl.class, true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), ProjectImpl.class);

        Project p1 = projectService.createProject("SP1", "First project", "First project description");
        Project p2 = projectService.createProject("SP2", "Second project", "Second project description");
        Project p3 = projectService.createProject("SP3", "Third project", "Third project description");
        Project p4 = projectService.createProject("SP4", "Fourth project", "Fourth project description");


        p1.addProjectUser(u3);
        p2.addProjectUser(u2);
        p1.addProjectUser(u4);
        p2.addProjectUser(u5);
        p1.addProjectUser(u5);
        p3.addProjectUser(u1);
        p2.addProjectUser(u1);
        p3.addProjectUser(u4);
        p4.addProjectUser(u2);
        p4.addProjectUser(u3);
        p4.addProjectUser(u5);

        p1.addProjectUser(u6);
        p2.addProjectUser(u7);
        p2.addProjectUser(u8);
        p2.addProjectUser(u9);
        p2.addProjectUser(u6);
        p3.addProjectUser(u5);
        p3.addProjectUser(u6);
        p3.addProjectUser(u7);
        p4.addProjectUser(u8);
        p4.addProjectUser(u9);
        p4.addProjectUser(u10);

        p1.store();
        p2.store();
        p3.store();
        p4.store();


        TableUtils.dropTable(storageService.getConnection(), TaskTagImpl.class, true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), TaskTagImpl.class);

        TableUtils.dropTable(storageService.getConnection(), TaskTypeImpl.class, true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), TaskTypeImpl.class);

        TaskType tt1 = new TaskTypeImpl("Bug");
        TaskType tt2 = new TaskTypeImpl("CR");
        TaskType tt3 = new TaskTypeImpl("Enhancement");

        tt1.store();
        tt2.store();
        tt3.store();

        TaskStatus ts1 = new TaskStatusImpl("New");
        TaskStatus ts2 = new TaskStatusImpl("Assigned");
        TaskStatus ts3 = new TaskStatusImpl("Resolved");
        TaskStatus ts4 = new TaskStatusImpl("Closed");
        TableUtils.dropTable(storageService.getConnection(), ts1.getClass(), true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), ts1.getClass());
        ts1.store();
        ts2.store();
        ts3.store();
        ts4.store();

        TaskPriority tp1 = new TaskPriorityImpl("Critical");
        TaskPriority tp2 = new TaskPriorityImpl("Normal");
        TaskPriority tp3 = new TaskPriorityImpl("Low");
        TableUtils.dropTable(storageService.getConnection(), tp1.getClass(), true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), tp1.getClass());
        tp1.store();
        tp2.store();
        tp3.store();


        TaskBuilder tb1 = new TaskImpl.TaskBuilderImpl(null);
        Task t1 = tb1.name("First Task").description("This is description. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp1).project(p1)
                .testSteps("This is teststeps. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt1).creator(u2).estimated(6).build();

        TableUtils.dropTable(storageService.getConnection(), t1.getClass(), true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), t1.getClass());
        TaskImpl.getDao().executeRaw("ALTER TABLE ab_tasks ADD FULLTEXT index task_index (task_name, task_desc, task_teststeps);");
        t1.store();
        t1.setAssignee(u4);
        t1.store();
        t1.addTag("tag1");
        t1.addTag("tag4");
        t1.addTag("tag3");

        TaskBuilder tb2 = new TaskImpl.TaskBuilderImpl(null);
        Task t2 = tb2.name("Second Task").description("This is description of second task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p1)
                .testSteps("This is teststeps of second task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt2).creator(u3).estimated(4).build();

        t2.store();
        t2.addTag("tag1");
        t2.addTag("tag3");

        TaskBuilder tb3 = new TaskImpl.TaskBuilderImpl(null);
        Task t3 = tb3.name("Third Task").description("This is description of third task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p1)
                .testSteps("This is teststeps of third task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt2).creator(u3).estimated(4).build();

        t3.store();
        t3.addTag("tag3");

        TaskBuilder tb4 = new TaskImpl.TaskBuilderImpl(null);
        Task t4 = tb4.name("FourthTask").description("This is description of fourth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p2)
                .testSteps("This is teststeps of fourth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt1).creator(u3).estimated(2).build();

        t4.store();
        t4.addTag("tag1");
        t4.addTag("tag2");
        t4.addTag("tag3");
        t4.addTag("tag4");
        t4.addTag("tag5");
        t4.addTag("tag6");
        t4.addTag("tag7");
        t4.addTag("tag8");
        t4.addTag("tag9");
        t4.addTag("tag12");
        t4.addTag("tag11");
        t4.addTag("tag10");
        t4.addTag("tag13");

        TaskBuilder tb5 = new TaskImpl.TaskBuilderImpl(null);
        Task t5 = tb5.name("Fifth Task").description("This is description of fifth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p1)
                .testSteps("This is teststeps of fifth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt2).creator(u3).estimated(8).build();

        t5.store();
        t5.addTag("tag1");
        t5.addTag("tag2");
        t5.addTag("tag5");
        t5.addTag("tag6");
        t5.addTag("tag7");
        t5.addTag("tag8");
        t5.addTag("tag9");
        t5.addTag("tag12");
        t5.addTag("tag11");
        t5.addTag("tag10");
        t5.addTag("tag13");


        TaskBuilder tb6 = new TaskImpl.TaskBuilderImpl(null);
        Task t6 = tb6.name("Sixth Task").description("This is description of sixth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p2)
                .testSteps("This is teststeps of sixth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt1).creator(u4).estimated(16).build();

        t6.store();

        t6.addTag("tag2");
        t6.addTag("tag5");
        t6.addTag("tag6");
        t6.addTag("tag7");
        t6.addTag("tag8");
        t6.addTag("tag9");
        t6.addTag("tag12");
        t6.addTag("tag11");
        t6.addTag("tag10");
        t6.addTag("tag13");

        TaskBuilder tb7 = new TaskImpl.TaskBuilderImpl(null);
        Task t7 = tb7.name("Seventh Task").description("This is description of seventh task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p1)
                .testSteps("This is teststeps of seventh task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt2).creator(u4).estimated(10).build();

        t7.addTag("tag12");
        t7.addTag("tag11");
        t7.addTag("tag13");

        t7.store();

        TaskBuilder tb8 = new TaskImpl.TaskBuilderImpl(null);
        Task t8 = tb8.name("Eighth Task").description("This is description of eighth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .priority(tp2).project(p2)
                .testSteps("This is teststeps of eighth task. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .type(tt1).creator(u5).estimated(6).build();

        t8.store();
        t8.addTag("tag5");
        t8.addTag("tag6");
        t8.addTag("tag7");
        t8.addTag("tag11");
        t8.addTag("tag10");
        t8.addTag("tag13");

        Comment c1 = new CommentImpl(t1, u2, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        Comment c2 = new CommentImpl(t2, u2, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        Comment c3 = new CommentImpl(t3, u3, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        Comment c4 = new CommentImpl(t4, u4, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        Comment c5 = new CommentImpl(t4, u2, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        Comment c6 = new CommentImpl(t4, u5, "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        TableUtils.dropTable(storageService.getConnection(), c1.getClass(), true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), c1.getClass());

        c1.store();
        c2.store();
        c3.store();
        c4.store();
        c5.store();
        c6.store();

        SessionImpl s = new SessionImpl();
        s.setUser(u4);
        s.setProject(p1);
        s.setToken(UUID.randomUUID().toString());
        TableUtils.dropTable(storageService.getConnection(), s.getClass(), true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), s.getClass());
        s.store();

        TaskLog tl1 = new TaskLogImpl(t1, u3, 2);
        TaskLog tl2 = new TaskLogImpl(t2, u2, 4);
        TaskLog tl12 = new TaskLogImpl(t1, u2, 4, LocalDateTime.now().minus(4, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl13 = new TaskLogImpl(t3, u2, 4, LocalDateTime.now().minus(4, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);

        TaskLog tl14 = new TaskLogImpl(t1, u5, 2);
        TaskLog tl15 = new TaskLogImpl(t1, u5, 4);
        TaskLog tl3 = new TaskLogImpl(t5, u5, 4);
        TaskLog tl4 = new TaskLogImpl(t1, u5, 1, LocalDateTime.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl5 = new TaskLogImpl(t1, u5, 2, LocalDateTime.now().minus(2, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl6 = new TaskLogImpl(t1, u5, 3, LocalDateTime.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl7 = new TaskLogImpl(t1, u5, 4, LocalDateTime.now().minus(4, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl8 = new TaskLogImpl(t1, u5, 5, LocalDateTime.now().minus(5, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl9 = new TaskLogImpl(t1, u5, 6, LocalDateTime.now().minus(6, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl10 = new TaskLogImpl(t1, u5, 7, LocalDateTime.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        TaskLog tl11 = new TaskLogImpl(t1, u5, 8, LocalDateTime.now().minus(8, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);


        TableUtils.dropTable(storageService.getConnection(), tl1.getClass(), true);
        TableUtils.createTableIfNotExists(storageService.getConnection(), tl1.getClass());

        tl1.store();
        tl2.store();
        tl3.store();
        tl4.store();
        tl5.store();
        tl6.store();
        tl7.store();
        tl8.store();
        tl9.store();
        tl10.store();
        tl11.store();
        tl12.store();
        tl13.store();
        tl14.store();
        tl15.store();


        for (int i = 0; i < 100; i++) {
            TaskBuilder tb = new TaskImpl.TaskBuilderImpl(null);

            User u = null;
            User a = null;
            do {
                u = userService.findUserByID((int) (Math.random() * 10 + 1)).get();
            } while (u == null);
            do {
                a = userService.findUserByID((int) (Math.random() * 10 + 1)).get();
            } while (a == null);

            Project p = null;
            TaskType tt = null;
            TaskPriority pr = null;
            do {
                p = projectService.getProjectByID((int) (Math.random() * 4 + 1)).get();
            } while (!projectService.isUserOfProject(p, u));
            do {
                tt = taskService.getTaskTypeByID((int) (Math.random() * 3 + 1)).get();
            } while (tt == null);
            do {
                pr = taskService.getTaskPriorityByID((int) (Math.random() * 3 + 1)).get();
            } while (pr == null);
            TaskStatus st = null;
            do {
                st = taskService.getTaskStatusByID((int) (Math.random() * 4 + 1)).get();
            } while (st == null);

            Task t = tb.name("Task #" + i).description("This is description of task #" + i + ". Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                    .priority(pr)
                    .project(p)
                    .testSteps("This is teststeps of task#" + i + ". Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                    .type(tt)
                    .creator(u)
                    .estimated((int) (Math.random() * 32 + 1)).build();
            t.setAssignee(a);
            t.setStatus(st);
            t.store();
            HashSet<String> tags = new HashSet<>();
            for (int j = 0; j < (int) (Math.random() * 15 + 1); j++) {
                tags.add("tag" + (int) (Math.random() * 15 + 1));
            }
            for (String tag : tags) {
                t.addTag(tag);
            }

            User commentor = null;
            int logged = 0;

            for (int j = 0; j < (int) (Math.random() * 15 + 1); j++) {
                do {
                    commentor = userService.findUserByID((int) (Math.random() * 10 + 1)).get();
                } while (commentor != null && !projectService.isUserOfProject(p, commentor));
                new CommentImpl(t, commentor, "This is comment of task #" + i + ". Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.").store();


                int log = (int) (Math.random() * 2 + 1);
                if (t.getEstimated() >= (logged + log)) {
                    loggingService.createLog(t, commentor, log, LocalDateTime.now().minus((int) (Math.random() * 20 + 1), ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
                    logged += log;
                }
            }
        }


        storageService.getConnection().close();

    }

    @Test
    public void tst() {

    }


}