package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.info.CommentInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommentServiceProviderTest extends TestServer {

    public static final String PATH = "comments/";


    @Test
    public void testGetComments() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());


        List<CommentInfo> infos = response.readEntity(new GenericType<List<CommentInfo>>() {
        });

        for (CommentInfo info : infos) {
            assertNotNull(info);
            assertEquals(user.getName(), info.getUser());
            assertEquals(COMMENT, info.getComment());
            assertEquals(DATE, info.getCreated());
        }
    }

    @Test
    public void testGetCommentsByUser() throws Exception {
        final WebTarget target = target(PATH + "user/" + USER_NAME);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());


        List<CommentInfo> infos = response.readEntity(new GenericType<List<CommentInfo>>() {
        });

        for (CommentInfo info : infos) {
            assertNotNull(info);
            assertEquals(user.getName(), info.getUser());
            assertEquals(COMMENT, info.getComment());
            assertEquals(DATE, info.getCreated());
        }
    }

    @Test
    public void testGetCommentsByUserNotFound() throws Exception {
        final WebTarget target = target(PATH + "user/" + USER_ID); //wrong parameter,

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetCommentsByTaskNotFound() throws Exception {
        final WebTarget target = target(PATH + "task/-1");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetCommentsByTask() throws Exception {
        final WebTarget target = target(PATH + "task/" + TASK_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());


        List<CommentInfo> infos = response.readEntity(new GenericType<List<CommentInfo>>() {
        });

        for (CommentInfo info : infos) {
            assertNotNull(info);
            assertEquals(task.getId(), info.getTask());
            assertEquals(COMMENT, info.getComment());
            assertEquals(DATE, info.getCreated());
        }
    }

    @Test
    public void testCreateComment() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new CommentInfo(user.getName(), task.getId(), COMMENT), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        CommentInfo info = response.readEntity(CommentInfo.class);
        assertNotNull(info);
        assertEquals(user.getName(), info.getUser());
        assertEquals(task.getId(), info.getTask());
        assertEquals(COMMENT, info.getComment());
        assertEquals(DATE, info.getCreated());
    }
}