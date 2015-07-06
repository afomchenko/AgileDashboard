package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.CustomStatusType;
import ru.mera.agileboard.rest.NotAuthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by antfom on 16.03.2015.
 */
@Provider
public class NotAuthorizedMapper implements ExceptionMapper<NotAuthorizedException> {

    public Response toResponse(NotAuthorizedException ex) {
        return Response.status(new CustomStatusType(Response.Status.UNAUTHORIZED, ex.getMessage())).build();
    }
}
