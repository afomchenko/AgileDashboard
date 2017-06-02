package ru.mera.agileboard.rest.servlets;

import org.glassfish.jersey.server.ContainerRequest;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.rest.NotAuthorizedException;
import ru.mera.agileboard.service.UserSessionService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by antfom on 16.03.2015.
 */
@Provider
public class RequestFilter implements ContainerRequestFilter {

    @Inject
    UserSessionService userSessionService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String path = ((ContainerRequest) containerRequestContext).getPath(true);
        if (path.equals("login") || path.equals("logout") || path.equals("projects")) {
            return;
        }

        String query = ((ContainerRequest) containerRequestContext).getRequestUri().getQuery();

        if (query != null) { //request.getSession(false)!=null  ) {
            for (String s : query.split("&")) {
                if (s.startsWith("authtoken")) {
                    String token = s.substring(10);
                    Optional<Session> session = userSessionService.setUserSession(token);
                    if (!session.isPresent()) {
                        throw new NotAuthorizedException("Authorization error. Please Relogin.");
                    } else {
                        System.err.println("logged " + token);
                        return;
                    }
                }
            }
        }

    }
}
