package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.rest.info.ProjectInfo;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

/**
 * Created by antfom on 19.02.2015.
 */
@Singleton
@Path("/projects")
public class ProjectServiceProvider {

    @Inject
    ProjectService projectService;

    @Inject
    UserService userService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjects() throws JAXBException {
        return Response.ok(new GenericEntity<List<ProjectInfo>>(ProjectInfo.fromProjects(projectService.getProjects())) {
        }).build();
    }

    @GET
    @Path("user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProjects(@PathParam("id") int id) throws JAXBException {
        Optional<User> optUser = userService.findUserByID(id);
        if (optUser.isPresent()) {
            return Response.ok(new GenericEntity<List<ProjectInfo>>(ProjectInfo.fromProjects(projectService.getProjectsByUser(optUser.get()))) {
            }).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ProjectInfo project) {
        if (project == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Project createdProj = projectService.createProject(project.getShortname(), project.getName(), project.getDesc());
        project.setId(createdProj.getId());
        return Response.status(Response.Status.CREATED).entity(project).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int projID, ProjectInfo proj) {
        if (proj == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Project> updProj = projectService.getProjectByID(projID);
        if (updProj.isPresent()) {
            updProj.get().setShortName(proj.getShortname());
            updProj.get().setName(proj.getName());
            updProj.get().setDesc(proj.getDesc());
            updProj.get().store();
            return Response.ok(new ProjectInfo(updProj.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}