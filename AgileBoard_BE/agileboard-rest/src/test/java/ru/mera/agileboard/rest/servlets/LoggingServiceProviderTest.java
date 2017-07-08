package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.info.LogInfo;
import ru.mera.agileboard.rest.info.LogUserDailyInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class LoggingServiceProviderTest extends TestServer {

    public static final String PATH = "logs/";

    @Test
    public void testGetLogsByUser() throws Exception {
        final WebTarget target = target(PATH + "user/" + USER_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());


        List<LogUserDailyInfo> infos = response.readEntity(new GenericType<List<LogUserDailyInfo>>() {
        });
        for (LogUserDailyInfo info : infos) {
            System.err.println(info);
            assertEquals(LOGGED, info.getTotal());
            assertEquals(task.getId(), info.getTasks().get(0).getTask());
        }

    }

    @Test
    public void testGetLogsByProjectNew() throws Exception {
        final WebTarget target = target(PATH + "project/" + PROJECT_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());


        String json = response.readEntity(String.class);
        assertNotNull(json);
        assertNotEquals("", json);
    }

    @Test
    public void testGetLoggedByTaskId() throws Exception {
        final WebTarget target = target(PATH + "tasksum/" + TASK_ID);

        Response response = target.request().get();

        assertEquals(200, response.getStatus());


        String sum = response.readEntity(String.class);
        assertEquals(LOGGED, Integer.parseInt(sum));
    }

    @Test
    public void testCreateLog() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new LogInfo(log), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        LogInfo info = response.readEntity(LogInfo.class);
        assertNotNull(info);
        assertEquals(user.getId(), info.getUser());
        assertEquals(task.getId(), info.getTask());
        assertEquals(LOGGED, info.getLogged());
        assertEquals(DATE, info.getDate());
    }
}