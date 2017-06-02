package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.rest.LoginException;
import ru.mera.agileboard.rest.info.LoginInfo;
import ru.mera.agileboard.rest.info.ProjectInfo;
import ru.mera.agileboard.rest.info.UserInfo;
import ru.mera.agileboard.rest.info.UserProjectInfo;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.UserSessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by antfom on 18.02.2015.
 */
@Singleton
@Path("/")
public class LoginServiceProvider {

    @Inject
    UserService userService;

    @Inject
    ProjectService projectService;

    @Inject
    UserSessionService sessionService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, LoginInfo login) {
        if (login == null || login.getProject() == null || login.getName() == null) {
            throw new LoginException("Invalid Login parameters");
        }
        int projectId = 0;

        try {
            projectId = Integer.parseInt(login.getProject());
        } catch (NumberFormatException e) {
            throw new LoginException("Invalid Project");
        }

        Optional<Project> getProj = projectService.getProjectByID(projectId);
        Optional<User> getUser = userService.findUserByName(login.getName());

        if (getUser.isPresent() && getProj.isPresent()) {
            if (!projectService.isUserOfProject(getProj.get(), getUser.get())) {
                throw new LoginException("Project " + getProj.get().getShortName() + " doesn't have user " + getUser.get().getName());
            }
            UserProjectInfo info = new UserProjectInfo(new UserInfo(getUser.get()), new ProjectInfo(getProj.get()));
//            request.getSession().putValue("loggedUser", getUser.get());
            Session session = sessionService.newUserSession(getUser.get(), getProj.get());
            info.setAuthtoken(session.getToken());
            return Response.ok(info).build();
        } else {
            throw new LoginException("Project or User not found");
        }
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@QueryParam("authtoken") String authtoken) {
        Optional<Session> session = sessionService.setUserSession(authtoken);

        if (session.isPresent()) {
            session.get().delete();
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
