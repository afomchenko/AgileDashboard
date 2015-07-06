package ru.mera.agileboard.rest.servlets;

import org.junit.Test;
import ru.mera.agileboard.rest.info.TagInfo;
import ru.mera.agileboard.rest.info.TagStringInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

public class TagServiceProviderTest extends TestServer {

    public static final String PATH = "tags/";


    @Test
    public void testGetTags() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        List<TagInfo> infos = response.readEntity(new GenericType<List<TagInfo>>() {
        });

        assertNotNull(infos);
        for (TagInfo info : infos) {
            if (info.getCount() == TAG_ID1) {
                assertEquals(TAG1, info.getName());
            } else if (info.getCount() == TAG_ID2) {
                assertEquals(TAG2, info.getName());
            }
        }
    }

    @Test
    public void testGetTagsByTask() throws Exception {
        final WebTarget target = target(PATH + "task/" + TASK_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        List<TagInfo> infos = response.readEntity(new GenericType<List<TagInfo>>() {
        });

        assertNotNull(infos);
        for (TagInfo info : infos) {
            if (info.getCount() == TAG_ID1) {
                assertEquals(TAG1, info.getName());
            } else if (info.getCount() == TAG_ID2) {
                assertEquals(TAG2, info.getName());
            }
        }
    }

    @Test
    public void testCreate() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new TagStringInfo(task.getId(), TAG1 + ", " + TAG2 + " : " + TAG1), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        List<TagInfo> infos = response.readEntity(new GenericType<List<TagInfo>>() {
        });
        assertEquals(2, infos.size());
        for (TagInfo info : infos) {
            assertTrue(info.getName().equals(TAG1) || info.getName().equals(TAG2));
        }


    }

    @Test
    public void testUpdate() throws Exception {
        final WebTarget target = target(PATH + TASK_ID);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(new TagStringInfo(task.getId(), TAG1 + ", " + TAG2 + ","), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        List<TagInfo> infos = response.readEntity(new GenericType<List<TagInfo>>() {
        });
        assertEquals(2, infos.size());
        for (TagInfo info : infos) {
            assertTrue(info.getName().equals(TAG1) || info.getName().equals(TAG2));
        }
    }
}