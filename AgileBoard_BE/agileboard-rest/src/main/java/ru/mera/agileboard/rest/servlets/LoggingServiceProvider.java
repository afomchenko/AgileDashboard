package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.rest.info.LogInfo;
import ru.mera.agileboard.rest.info.LogUserDailyInfo;
import ru.mera.agileboard.service.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by antfom on 06.03.2015.
 */
@Singleton
@Path("/logs")
public class LoggingServiceProvider {

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    @Inject
    LoggingService loggingService;

    @Inject
    ProjectService projectService;

    @Inject
    UserSessionService userSessionService;

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogsByUser(@PathParam("id") int id) {

        List<LogUserDailyInfo> logs = new ArrayList<>();
        Optional<User> optUser = userService.findUserByID(id);

        if (optUser.isPresent()) {
            User user = optUser.get();
            for (long i = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
                         ; i > LocalDateTime.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
                 i -= 86400000) {

                logs.add(new LogUserDailyInfo(i, loggingService.getLogByUserDaily(user, i)));

            }

            return Response.ok(new GenericEntity<List<LogUserDailyInfo>>(logs) {
            }).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("/project/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogsByProjectNew(@PathParam("id") int id) {


        List<Map<String, String>> list = new ArrayList<>();

        Optional<Project> optProj = projectService.getProjectByID(id);

        Long now = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;

        if (optProj.isPresent()) {
            for (User user : projectService.getUsersOfProject(optProj.get())) {

                Map<String, String> map = new HashMap<>();
                map.put("name", user.getName());
                map.put("userid", String.valueOf(user.getId()));

                Map<Long, Integer> tempMap = loggingService.getRecentLogSumByUser(user);

                for (Map.Entry<Long, Integer> entry : tempMap.entrySet()) {
                    map.put(String.valueOf((now - entry.getKey()) / 86400000), String.valueOf(entry.getValue()));
                }

                map.put("summary", String.valueOf(tempMap.values().stream().reduce(0, (a, b) -> a + b)));
                list.add(map);

            }

            return Response.ok(list).build();
        }


        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("tasksum/{id}")
    public Response getLoggedByTaskId(@PathParam("id") int id) {

        Optional<Task> t = taskService.getTaskByID(id);

        if (t.isPresent()) {

            return Response.ok(loggingService.getLoggedSummaryByTask(t.get())).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLog(LogInfo logInfo) {
        Optional<Task> optTask = taskService.getTaskByID(logInfo.getTask());

        if (optTask.isPresent()) {

            long date = LocalDateTime.ofInstant(Instant.ofEpochMilli(logInfo.getDate()), ZoneId.systemDefault())
                    .truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;

            TaskLog log = loggingService.createLog(optTask.get(), userSessionService.getUserSession().getUser(), logInfo.getLogged(), date);


            return Response.status(201).entity(new LogInfo(log)).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
