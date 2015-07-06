package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.model.TaskStatus;
import ru.mera.agileboard.rest.info.TaskStatusInfo;
import ru.mera.agileboard.service.TaskService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by antfom on 05.03.2015.
 */
@Singleton
@Path("/statuses")
public class TaskStatusServiceProvider {

    @Inject
    TaskService taskService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatuses() {

        List<TaskStatus> statuses = taskService.getAllStatuses();
        List<TaskStatusInfo> statusInfos = TaskStatusInfo.fromStatuses(statuses);
        GenericEntity<List<TaskStatusInfo>> entity = new GenericEntity<List<TaskStatusInfo>>(statusInfos) {
        };
        return Response.ok(entity).build();

    }


}