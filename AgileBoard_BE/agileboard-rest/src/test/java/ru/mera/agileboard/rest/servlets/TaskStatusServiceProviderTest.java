package ru.mera.agileboard.rest.servlets;

import org.junit.Test;
import ru.mera.agileboard.rest.info.TaskStatusInfo;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskStatusServiceProviderTest extends TestServer {
    public static final String PATH = "statuses/";


    @Test
    public void testGetStatuses() throws Exception {

        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        List<TaskStatusInfo> ststusInfos = response.readEntity(new GenericType<List<TaskStatusInfo>>() {
        });

        assertNotNull(ststusInfos);
        for (TaskStatusInfo ststusInfo : ststusInfos) {
            assertEquals(STATUS_ID, ststusInfo.getId());
            assertEquals(STATUS_NAME, ststusInfo.getStatus());
        }
    }
}