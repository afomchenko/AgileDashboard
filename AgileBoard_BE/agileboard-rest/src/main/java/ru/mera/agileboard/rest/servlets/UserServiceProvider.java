package ru.mera.agileboard.rest.servlets;


import ru.mera.agileboard.model.User;
import ru.mera.agileboard.rest.PATCH;
import ru.mera.agileboard.rest.info.PatchParameter;
import ru.mera.agileboard.rest.info.UserInfo;
import ru.mera.agileboard.service.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Singleton
@Path("/users")
public class UserServiceProvider {// extends ServletContainer {

    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        Optional<User> userOptional = userService.findUserByID(id);
        if (userOptional.isPresent()) {
            return Response.ok(new UserInfo(userOptional.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<UserInfo> users = UserInfo.fromUsers(userService.getAllUsers());
        GenericEntity<List<UserInfo>> entity = new GenericEntity<List<UserInfo>>(users) {
        };
        return Response.ok(entity).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserInfo user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User createdUser = userService.createUser(user.getName(), user.getEmail());
        user.setId(createdUser.getId());
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int userID, UserInfo user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<User> optUser = userService.findUserByID(userID);
        if (optUser.isPresent()) {
            User updUser = optUser.get();
            updUser.setName(user.getName());
            updUser.setEmail(user.getEmail());
            updUser.store();
            return Response.ok(new UserInfo(updUser)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patch(List<PatchParameter> params) {
        System.err.println("starting patch");
        Set<User> users = new HashSet<>();
        for (PatchParameter param : params) {
            try {
                int userID = Integer.parseInt(param.pathElements()[1]);
                Optional<User> updUser = userService.findUserByID(userID);
                if (updUser.isPresent()) {
                    User user = updUser.get();
                    users.add(user);
                    switch (param.pathElements()[2]) {
                        case "name":
                            user.setName(param.getValue());
                            break;
                        case "email":
                            user.setEmail(param.getValue());
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        }
        for (User user : users) {
            user.store();
        }
        return Response.ok(new GenericEntity<List<UserInfo>>(UserInfo.fromUsers(users)) {}).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        Optional<User> user = userService.findUserByID(id);
        if (user.isPresent()) {
            user.get().delete();
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}