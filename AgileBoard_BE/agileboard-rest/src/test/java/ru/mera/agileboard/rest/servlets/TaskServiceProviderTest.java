package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.info.TaskInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TaskServiceProviderTest extends TestServer {

    public static final String PATH = "tasks/";

    @Test
    public void testGetTaskById() throws Exception {
        final WebTarget target = target(PATH + TASK_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        TaskInfo info = response.readEntity(TaskInfo.class);
        assertNotNull(info);

        assertEquals(TASK_ID, info.getId());
        assertEquals(TASK_NAME, info.getName());
        assertEquals(TASK_DESC, info.getDescription());
        assertEquals(TASK_TESTSTEPS, info.getTeststeps());
        assertEquals(user.getId(), info.getCreator());
        assertEquals(project.getShortName(), info.getProject());
        assertEquals(status.getName(), info.getStatus());
        assertEquals(taskType.getName(), info.getTasktype());
        assertEquals(priority.getName(), info.getPriority());
        assertEquals(LOGGED, info.getEstimated());
        assertEquals(DATE, info.getCreated());
        assertEquals(DATE, info.getUpdated());

    }

    @Test
    public void testGetTasksByUser() throws Exception {
        final WebTarget target = target(PATH + "user/" + USER_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }

    }

    @Test
    public void testGetTasksByFulltext() throws Exception {
        final WebTarget target = target(PATH + "search/").queryParam("query", URLEncoder.encode(TASK_DESC, "UTF-8"));

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }


    }

    @Test
    public void testGetTasksByUserProject() throws Exception {
        final WebTarget target = target(PATH + "project")
                .queryParam("user", USER_ID).queryParam("project", PROJECT_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByUserOpen() throws Exception {
        final WebTarget target = target(PATH + "open");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByUserInProgress() throws Exception {
        final WebTarget target = target(PATH + "progress");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByUserCompleted() throws Exception {
        final WebTarget target = target(PATH + "complete");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByUserCommentsed() throws Exception {
        final WebTarget target = target(PATH + "commented");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByUserReported() throws Exception {
        final WebTarget target = target(PATH + "reported");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByUserLogged() throws Exception {
        final WebTarget target = target(PATH + "logged");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksByTag() throws Exception {
        final WebTarget target = target(PATH + "tag/" + tag1.getTag());

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasks() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testGetTasksAsync() throws Exception {
        final WebTarget target = target(PATH + "async");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<TaskInfo> infos = response.readEntity(new GenericType<List<TaskInfo>>() {
        });
        assertNotNull(infos);
        assertNotEquals(0, infos.size());
        for (TaskInfo info : infos) {
            assertEquals(TASK_ID, info.getId());
            assertEquals(TASK_NAME, info.getName());
            assertEquals(TASK_DESC, info.getDescription());
            assertEquals(TASK_TESTSTEPS, info.getTeststeps());
            assertEquals(user.getId(), info.getCreator());
            assertEquals(project.getShortName(), info.getProject());
            assertEquals(status.getName(), info.getStatus());
            assertEquals(taskType.getName(), info.getTasktype());
            assertEquals(priority.getName(), info.getPriority());
            assertEquals(LOGGED, info.getEstimated());
            assertEquals(DATE, info.getCreated());
            assertEquals(DATE, info.getUpdated());
        }
    }

    @Test
    public void testCreate() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new TaskInfo(task), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        TaskInfo info = response.readEntity(TaskInfo.class);
        assertNotNull(info);

        assertEquals(TASK_ID, info.getId());
        assertEquals(TASK_NAME, info.getName());
        assertEquals(TASK_DESC, info.getDescription());
        assertEquals(TASK_TESTSTEPS, info.getTeststeps());
        assertEquals(user.getId(), info.getCreator());
        assertEquals(project.getShortName(), info.getProject());
        assertEquals(status.getName(), info.getStatus());
        assertEquals(taskType.getName(), info.getTasktype());
        assertEquals(priority.getName(), info.getPriority());
        assertEquals(LOGGED, info.getEstimated());
        assertEquals(DATE, info.getCreated());
        assertEquals(DATE, info.getUpdated());

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testPatch() throws Exception {

    }

    @Test
    public void testAssign() throws Exception {

    }

    @Test
    public void testChangeStatus() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {
        final WebTarget target = target(PATH + TASK_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        assertEquals(200, response.getStatus());
    }
}