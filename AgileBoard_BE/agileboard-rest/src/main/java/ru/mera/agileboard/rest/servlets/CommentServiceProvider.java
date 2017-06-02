package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.model.Comment;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.rest.info.CommentInfo;
import ru.mera.agileboard.service.CommentService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.UserSessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * Created by antfom on 27.02.2015.
 */
@Singleton
@Path("/comments")
public class CommentServiceProvider {

    @Inject
    CommentService commentService;

    @Inject
    UserService userService;

    @Inject
    TaskService taskService;

    @Inject
    UserSessionService userSessionService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments() {
        return Response.ok(new GenericEntity<List<CommentInfo>>(CommentInfo.fromComments(commentService.getAllComments())) {
        }).build();
    }

    @GET
    @Path("user/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByUser(@PathParam("name") String name) {
        System.err.println(name);
        Optional<User> user = userService.findUserByName(name);
        if (user.isPresent()) {
            return Response.ok(new GenericEntity<List<CommentInfo>>(CommentInfo.fromComments(commentService.findCommentsByUser(user.get()))) {
            }).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsByTask(@PathParam("id") int id) {
        Optional<Task> task = taskService.getTaskByID(id);
        if (task.isPresent()) {
            return Response.ok(new GenericEntity<List<CommentInfo>>(CommentInfo.fromComments(commentService.findCommentsByTask(task.get()))) {
            }).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(CommentInfo commentInfo) {
        if (commentInfo == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Task> task = taskService.getTaskByID(commentInfo.getTask());
        if (task.isPresent()) {
            Comment comment = commentService.createComment(
                    task.get(),
                    userSessionService.getUserSession().getUser(),
                    commentInfo.getComment()
            );
            return Response.status(Response.Status.CREATED).entity(new CommentInfo(comment)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
