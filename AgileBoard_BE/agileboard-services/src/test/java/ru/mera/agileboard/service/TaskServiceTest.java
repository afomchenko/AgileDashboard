package ru.mera.agileboard.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.*;
import ru.mera.agileboard.model.impl.StorageSingleton;
import ru.mera.agileboard.service.impl.ProjectServiceImpl;
import ru.mera.agileboard.service.impl.TaskServiceImpl;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TaskServiceTest {

    private static TaskService taskService;
    private static Project project;
    private static User user;
    private static User assigneeUser;
    private static TaskType type;
    private static TaskPriority priority;
    private static TaskStatus status;

    private String name;
    private String desc;
    private String teststeps;

    public TaskServiceTest(String name, String desc, String teststeps) {
        this.name = name;
        this.desc = desc;
        this.teststeps = teststeps;
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ABEntityTest.initDB();
        StorageSingleton.init(new StorageServiceImpl());
        taskService = new TaskServiceImpl();
        project = new ProjectServiceImpl().getProjectByID(1).get();
        user = new UserServiceImpl().findUserByID(1).get();
        assigneeUser = new UserServiceImpl().findUserByID(2).get();
        type = taskService.getTaskTypeByID(1).get();
        priority = taskService.getTaskPriorityByID(1).get();
        status = taskService.getTaskStatusByID(1).get();
    }

    @Parameterized.Parameters
    public static List<String[]> paramGenerator() {
        ArrayList<String[]> list = new ArrayList<>();
        list.add(new String[]{"Test Task 1", "Test Task 1 description", "Test Task 1 teststeps"});
        list.add(new String[]{"Test Task 2", "Test Task 2 description", "Test Task 2 teststeps"});
        list.add(new String[]{"Test Task 3", "Test Task 3 description", "Test Task 3 teststeps"});
        list.add(new String[]{"Test Task 4", "Test Task 4 description", "Test Task 4 teststeps"});
        return list;
    }


    @Test
    public void testCreateTask() throws Exception {
        TaskBuilder builder = taskService.createTask();
        builder.creator(user);
        builder.project(project);
        builder.name(name);
        builder.description(desc);
        builder.testSteps(teststeps);
        builder.type(type);
        builder.estimated(8);
        builder.priority(priority);
        Task task = builder.build();

        task.store();

        assertNotNull(task);
        assertTrue(task.getId() > 0);
        assertEquals(name, task.getName());
        assertEquals(desc, task.getDescription());
        assertEquals(teststeps, task.getTestSteps());
        assertEquals(user.getName(), task.getCreator().getName());
        assertEquals(user.getName(), task.getAssignee().getName());
        assertEquals("New", task.getStatus().getName());
        assertEquals(priority, task.getPriority());
        assertEquals(type, task.getType());
    }


    @Test
    public void testGetTasks() throws Exception {
        List<Task> tasks = taskService.getTasks();
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
        }
    }

    @Test
    public void testGetTasksWithFilter() throws Exception {
        List<Task> tasks = taskService.getTasks(TaskService.Filter.ALL);
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
        }
        tasks = taskService.getTasks(TaskService.Filter.CREATOR, "1");
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
        }
    }

    @Test
    public void testGetTaskByID() throws Exception {
        Optional<Task> optTask = taskService.getTaskByID(1);
        assertTrue(optTask.isPresent());
        Task task = optTask.get();
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertNotNull(task.getName());
        assertNotNull(task.getDescription());
        assertNotNull(task.getTestSteps());
        assertNotNull(task.getCreator());
        assertNotNull(task.getAssignee());
        assertNotNull(task.getStatus());
        assertNotNull(task.getPriority());
        assertNotNull(task.getType());
    }

    @Test
    public void testGetTaskTypeByID() throws Exception {
        Optional<TaskType> optType = taskService.getTaskTypeByID(1);
        assertTrue(optType.isPresent());
        TaskType typ = optType.get();
        assertNotNull(typ);
        assertEquals(1, typ.getId());
        assertNotNull(typ.getName());
        assertEquals(type.getName(), typ.getName());
    }

    @Test
    public void testGetTaskStatusByID() throws Exception {
        Optional<TaskStatus> optStatus = taskService.getTaskStatusByID(1);
        assertTrue(optStatus.isPresent());
        TaskStatus stat = optStatus.get();
        assertNotNull(stat);
        assertEquals(1, stat.getId());
        assertNotNull(stat.getName());
    }

    @Test
    public void testGetTaskTypeByName() throws Exception {
        Optional<TaskType> optType = taskService.getTaskTypeByName("Bug");
        assertTrue(optType.isPresent());
        TaskType typ = optType.get();
        assertNotNull(typ);
        assertEquals("Bug", typ.getName());
        assertTrue(typ.getId() > 0);
        assertEquals(type.getName(), typ.getName());
    }

    @Test
    public void testGetTaskStatusByName() throws Exception {
        Optional<TaskStatus> optStatus = taskService.getTaskStatusByName("New");
        assertTrue(optStatus.isPresent());
        TaskStatus stat = optStatus.get();
        assertNotNull(stat);
        assertEquals("New", stat.getName());
        assertTrue(stat.getId() > 0);
    }

    @Test
    public void testGetTaskPriorityByName() throws Exception {
        Optional<TaskPriority> optPriority = taskService.getTaskPriorityByName("Normal");
        assertTrue(optPriority.isPresent());
        TaskPriority prior = optPriority.get();
        assertNotNull(prior);
        assertTrue(prior.getId() > 0);
        assertEquals("Normal", prior.getName());
    }

    @Test
    public void testGetTaskPriorityByID() throws Exception {
        Optional<TaskPriority> optPriority = taskService.getTaskPriorityByID(1);
        assertTrue(optPriority.isPresent());
        TaskPriority prior = optPriority.get();
        assertNotNull(prior);
        assertEquals(1, prior.getId());
        assertNotNull(prior.getName());
    }

    @Test
    public void testGetTaskByUserOpen() throws Exception {
        List<Task> tasks = taskService.getTaskByUserOpen(4);
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
            assertEquals("New", task.getStatus().getName());
        }
    }

    @Test
    public void testGetTaskByUserProgress() throws Exception {
        List<Task> tasks = taskService.getTaskByUserProgress(4);
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
            assertEquals("Assigned", task.getStatus().getName());
        }
    }

    @Test
    public void testGetTaskByUserCompleted() throws Exception {
        List<Task> tasks = taskService.getTaskByUserCompleted(4);
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
            assertTrue("Resolved".equals(task.getStatus().getName()) || "Closed".equals(task.getStatus().getName()));
        }
    }

    @Test
    public void testGetTaskByUserCommented() throws Exception {
        List<Task> tasks = taskService.getTaskByUserCommented(1);
        for (Task task : tasks) {
            assertNotNull(task);
            assertTrue(task.getId() > 0);
            assertNotNull(task.getName());
            assertNotNull(task.getDescription());
            assertNotNull(task.getTestSteps());
            assertNotNull(task.getCreator());
            assertNotNull(task.getAssignee());
            assertNotNull(task.getStatus());
            assertNotNull(task.getPriority());
            assertNotNull(task.getType());
        }
    }

    @Test
    public void testGetAllTags() throws Exception {
        Map<String, Integer> tags = taskService.getAllTags();
        for (Map.Entry<String, Integer> entry : tags.entrySet()) {
            assertNotEquals("", entry.getKey());
            assertTrue(entry.getValue() > 0);
        }
    }

    @Test
    public void testFindTagsByTask() throws Exception {
        Map<String, Integer> tags = taskService.findTagsByTask(taskService.getTaskByID(1).get());
        for (Map.Entry<String, Integer> entry : tags.entrySet()) {
            assertNotEquals("", entry.getKey());
        }
    }

    @Test
    public void testGetAllStatuses() throws Exception {
        List<TaskStatus> statuses = taskService.getAllStatuses();
        for (TaskStatus taskStatus : statuses) {
            assertTrue(taskStatus.getId() > 0);
            assertNotNull(taskStatus.getName());
            assertNotEquals("", taskStatus.getName());
        }
    }

    @Test
    public void testGetTaskFulltext() throws Exception {
        String search = "Third Task";
        List<Task> tasks = taskService.getTaskFulltext("\"" + search + "\"");
        for (Task task : tasks) {
            assertTrue(task.getName().contains(search) || task.getDescription().contains(search) || task.getTestSteps().contains(search));
        }
    }
}