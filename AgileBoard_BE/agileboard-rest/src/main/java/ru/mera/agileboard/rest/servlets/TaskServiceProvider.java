package ru.mera.agileboard.rest.servlets;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.glassfish.jersey.server.ManagedAsync;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskBuilder;
import ru.mera.agileboard.model.TaskStatus;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.rest.PATCH;
import ru.mera.agileboard.rest.info.PatchParameter;
import ru.mera.agileboard.rest.info.TaskInfo;
import ru.mera.agileboard.rest.info.TaskStatusInfo;
import ru.mera.agileboard.rest.info.UserInfo;
import ru.mera.agileboard.service.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


@Singleton
@Path("/tasks")
public class TaskServiceProvider {

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    @Inject
    ProjectService projectService;

    @Inject
    UserSessionService userSessionService;

    @Inject
    LoggingService loggingService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskById(@PathParam("id") int id) {

        Optional<Task> t = taskService.getTaskByID(id);

        if (t.isPresent()) {
            TaskInfo task = new TaskInfo(t.get());
            return Response.ok(task).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("/project")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserProject(@QueryParam("user") int user, @QueryParam("project") int project) {
        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(taskService.getTasks(TaskService.Filter.USERPROJ, String.valueOf(user), String.valueOf(project))));
        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }

    @GET
    @Path("user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUser(@PathParam("id") int id) {
        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(taskService.getTasks(TaskService.Filter.USERID, String.valueOf(id))));
        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }
//
//    @GET
//    @Path("search/{str}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getTasksByFulltext(@PathParam("str") String str) throws UnsupportedEncodingException {
//        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(taskService.getTaskFulltext(URLDecoder.decode(str, "UTF-8"))));
//        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
//        }).build();
//    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByFulltext(@QueryParam("query") String str) throws UnsupportedEncodingException {

        str = URLDecoder.decode(str, "UTF-8");
        List<TaskInfo> tasks = new ArrayList<>();

        if (str.trim().startsWith("#")) {
            tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(taskService.getTasks
                    (TaskService.Filter.TAG, str.trim().replaceFirst("#", ""))));
        } else {
            tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(
                    taskService.filterByProjects(taskService.getTaskFulltext("\"" + str + "\""),
                            projectService.getProjectsByUser(userSessionService.getUserSession().getUser())
                    )));
        }
        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }

    @GET
    @Path("/open")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserOpen(@PathParam("id") int id) {

        List<TaskInfo> tasks = TaskInfo.fromTasks(
                taskService.getTasks(TaskService.Filter.USEROPEN,
                        String.valueOf(userSessionService.getUserSession().getUser().getId())));

        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }

    @GET
    @Path("/progress")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserInProgress(@PathParam("id") int id) {
        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(
                taskService.getTasks(TaskService.Filter.USERINPROGRESS,
                        String.valueOf(userSessionService.getUserSession().getUser().getId()))));
        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }

    @GET
    @Path("/complete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserCompleted(@PathParam("id") int id) {

        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(
                taskService.getTasks(TaskService.Filter.USERCOMPL,
                        String.valueOf(userSessionService.getUserSession().getUser().getId()))));

        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }

    @GET
    @Path("/commented")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserCommentsed() {

        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(
                taskService.getTasks(TaskService.Filter.USERCOMMENTED,
                        String.valueOf(userSessionService.getUserSession().getUser().getId()))));

        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }


    @GET
    @Path("/reported")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserReported() {

        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(
                taskService.getTasks(TaskService.Filter.CREATOR,
                        String.valueOf(userSessionService.getUserSession().getUser().getId()))));

        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }


    @GET
    @Path("/logged")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByUserLogged() {

        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(
                taskService.getTasks(TaskService.Filter.USERLOGGED,
                        String.valueOf(userSessionService.getUserSession().getUser().getId()))));

        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }


    @GET
    @Path("tag/{tag}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByTag(@PathParam("tag") String tag) {
        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(

                taskService.filterByProjects(taskService.getTasks(TaskService.Filter.TAG, tag),
                        projectService.getProjectsByUser(userSessionService.getUserSession().getUser())
                )));

        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();
    }


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks() {
        List<TaskInfo> tasks = TaskInfo.fromTasks(loggingService.getLoggedByTask(taskService.getTasks()));
        return Response.ok(new GenericEntity<List<TaskInfo>>(tasks) {
        }).build();

    }

    @GET
    @Path("/async")
    @ManagedAsync
    @Produces(MediaType.APPLICATION_JSON)
    public void getTasks(@Suspended final AsyncResponse response) {

        ListenableFuture<List<Task>> future = taskService.getAllTasksAsync();
        Futures.addCallback(future, new FutureCallback<List<Task>>() {
            public void onSuccess(List<Task> tasks) {
                response.resume(TaskInfo.fromTasks(tasks));
            }

            public void onFailure(Throwable thrown) {
                response.resume(thrown);
            }
        });

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TaskInfo task) {
        if (task == null) return Response.status(Response.Status.BAD_REQUEST).build();

        TaskBuilder builder = taskService.createTask();
        builder.project(projectService.getProjectByShortName(task.getProject()).get())
                .name(task.getName()).description(task.getDescription())
                .testSteps(task.getTeststeps())
                .type(taskService.getTaskTypeByName(task.getTasktype()).get())
                .priority(taskService.getTaskPriorityByName(task.getPriority()).get())
                .estimated(task.getEstimated())
                .creator(userSessionService.getUserSession().getUser())
                .assignee(userService.findUserByID(task.getAssignee()).get());
        Task createdTask = builder.build();
//        createdTask.setCreator(userSessionService.getUserSession());
        createdTask.store();
        return Response.status(201).entity(new TaskInfo(createdTask)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, TaskInfo task) {

        Optional<Task> optTask = taskService.getTaskByID(id);

        if (optTask.isPresent()) {
            Task updTask = optTask.get();
            updTask.setName(task.getName());
            updTask.setDescription(task.getDescription());
            updTask.setTestSteps(task.getTeststeps());
            updTask.setType(taskService.getTaskTypeByName(task.getTasktype()).get());
            updTask.setPriority(taskService.getTaskPriorityByName(task.getPriority()).get());
            updTask.store();

            return Response.ok(new TaskInfo(updTask)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PATCH
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patch(List<PatchParameter> params) {
        System.err.println("starting patch");

        Set<Task> tasks = new HashSet<>();

        for (PatchParameter param : params) {

            try {
                int taskID = Integer.parseInt(param.pathElements()[1]);
                Optional<Task> optTask = taskService.getTaskByID(taskID);
                if (optTask.isPresent()) {
                    Task task = optTask.get();
                    tasks.add(task);
                    switch (param.pathElements()[2]) {
                        case "name":
                            task.setName(param.getValue());
                            break;
                        case "desc":
                            task.setDescription(param.getValue());
                            break;
                        case "teststeps":
                            task.setTestSteps(param.getValue());
                            break;
                        case "type":
                            try {
                                task.setType(taskService.getTaskTypeByName(param.getValue()).get());
                            } catch (Exception e) {/*do nothing*/}
                            break;
                        case "priority":
                            try {
                                task.setPriority(taskService.getTaskPriorityByName(param.getValue()).get());
                            } catch (Exception e) {/*do nothing*/}
                            break;
                        case "assignee":
                            try {
                                Optional<User> optUser = userService.findUserByID(Integer.parseInt(param.getValue()));
                                if (optUser.isPresent()) {
                                    task.setAssignee(optUser.get());
                                    task.setStatus(taskService.getTaskStatusByName("Assigned").get());
                                }
                            } catch (Exception e) {/*do nothing*/}
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        }

        tasks.forEach(Task::store);

        return Response.ok(new GenericEntity<List<TaskInfo>>(TaskInfo.fromTasks(tasks)) {
        }).build();
    }


    @PUT
    @Path("/assign/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response assign(@PathParam("id") int id, UserInfo userInfo) {

        System.err.println(userInfo);


        Optional<Task> optTask = taskService.getTaskByID(id);
        Optional<User> optUser = userService.findUserByID(userInfo.getId());

        if (optTask.isPresent() && optUser.isPresent()) {
            Task updTask = optTask.get();
            System.err.println(updTask);

            updTask.setAssignee(optUser.get());
            System.err.println("assign");
            updTask.store();
            System.err.println("stored");

            return Response.ok(new TaskInfo(updTask)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/status/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeStatus(@PathParam("id") int id, TaskStatusInfo statusInfo) {

        Optional<Task> optTask = taskService.getTaskByID(id);
        Optional<TaskStatus> optStatus = taskService.getTaskStatusByID(statusInfo.getId());

        if (optTask.isPresent() && optStatus.isPresent()) {
            Task updTask = optTask.get();
            System.err.println(updTask);

            updTask.setStatus(optStatus.get());
            updTask.store();

            return Response.ok(new TaskInfo(updTask)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {

        Optional<Task> task = taskService.getTaskByID(id);

        if (task.isPresent()) {
            task.get().delete();
            return Response.ok().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();


    }
}
