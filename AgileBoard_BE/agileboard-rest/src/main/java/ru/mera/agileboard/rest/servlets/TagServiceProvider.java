package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.rest.info.TagInfo;
import ru.mera.agileboard.rest.info.TagStringInfo;
import ru.mera.agileboard.service.TaskService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by antfom on 19.02.2015.
 */
@Singleton
@Path("/tags")
public class TagServiceProvider {

    @Inject
    TaskService taskService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags() {

        Map<String, Integer> map = taskService.getAllTags();
        System.err.println(map);
        List<TagInfo> tags = TagInfo.fromTagsMap(map);
        GenericEntity<List<TagInfo>> entity = new GenericEntity<List<TagInfo>>(tags) {
        };
        return Response.ok(entity).build();

    }

    @GET
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags(@PathParam("id") int id) {

        Optional<Task> optTask = taskService.getTaskByID(id);

        if (optTask.isPresent()) {
            Map<String, Integer> map = taskService.findTagsByTask(optTask.get());
            System.err.println(map);
            List<TagInfo> tags = TagInfo.fromTagsMap(map);
            GenericEntity<List<TagInfo>> entity = new GenericEntity<List<TagInfo>>(tags) {
            };
            return Response.ok(entity).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TagStringInfo tags) {

        if (tags == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Optional<Task> optTask = taskService.getTaskByID(tags.getTask());

        if (optTask.isPresent()) {
            Task task = optTask.get();
            List<TagInfo> tagInfoList = new ArrayList<>();
            Set<String> tagStringSet = new HashSet<>();

            for (String s : tags.getTags().split("\\p{Punct}")) {
                if (!s.equals("")) {
                    s = s.trim();
                    tagStringSet.add(s);
                }
            }

            for (String s : tagStringSet) {
                tagInfoList.add(new TagInfo(s, 1));
                task.addTag(s);
            }

            task.store();

            return Response.status(201).entity(new GenericEntity<List<TagInfo>>(tagInfoList) {
            }).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("taskId") int taskId, TagStringInfo tags) {

        if (tags == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        Optional<Task> optTask = taskService.getTaskByID(taskId);

        if (optTask.isPresent()) {
            Task task = optTask.get();

            taskService.clearTags(task);

            List<TagInfo> tagInfoList = new ArrayList<>();
            Set<String> tagStringSet = new HashSet<>();

            for (String s : tags.getTags().split("\\p{Punct}")) {
                if (!s.equals("")) {
                    s = s.trim();
                    tagStringSet.add(s);
                }
            }

            for (String s : tagStringSet) {
                tagInfoList.add(new TagInfo(s, 1));
                task.addTag(s);
            }

            task.store();

            return Response.status(201).entity(new GenericEntity<List<TagInfo>>(tagInfoList) {
            }).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}
