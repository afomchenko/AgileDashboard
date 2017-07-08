package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.info.ProjectInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProjectServiceProviderTest extends TestServer {

    public static final String PATH = "projects/";

    @Test
    public void testGetProjects() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<ProjectInfo> infos = response.readEntity(new GenericType<List<ProjectInfo>>() {
        });
        for (ProjectInfo info : infos) {
            assertEquals(PROJECT_ID, info.getId());
            assertEquals(PROJECT_SHORT_NAME, info.getShortname());
            assertEquals(PROJECT_NAME, info.getName());
            assertEquals(PROJECT_DESC, info.getDesc());
        }
    }

    @Test
    public void testGetProjectsByUser() throws Exception {
        final WebTarget target = target(PATH + "user/" + PROJECT_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        List<ProjectInfo> infos = response.readEntity(new GenericType<List<ProjectInfo>>() {
        });
        for (ProjectInfo info : infos) {
            assertEquals(PROJECT_ID, info.getId());
            assertEquals(PROJECT_SHORT_NAME, info.getShortname());
            assertEquals(PROJECT_NAME, info.getName());
            assertEquals(PROJECT_DESC, info.getDesc());
        }
    }

    @Test
    public void testCreate() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new ProjectInfo(project), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        ProjectInfo info = response.readEntity(ProjectInfo.class);
        assertEquals(PROJECT_ID, info.getId());
        assertEquals(PROJECT_SHORT_NAME, info.getShortname());
        assertEquals(PROJECT_NAME, info.getName());
        assertEquals(PROJECT_DESC, info.getDesc());
    }

    @Test
    public void testUpdate() throws Exception {
        final WebTarget target = target(PATH + PROJECT_ID);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(new ProjectInfo(project), MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        ProjectInfo info = response.readEntity(ProjectInfo.class);
        assertEquals(PROJECT_ID, info.getId());
        assertEquals(PROJECT_SHORT_NAME, info.getShortname());
        assertEquals(PROJECT_NAME, info.getName());
        assertEquals(PROJECT_DESC, info.getDesc());
    }
}