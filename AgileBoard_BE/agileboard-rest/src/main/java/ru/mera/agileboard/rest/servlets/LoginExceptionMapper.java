package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.CustomStatusType;
import ru.mera.agileboard.rest.LoginException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by antfom on 16.03.2015.
 */
@Provider
public class LoginExceptionMapper implements ExceptionMapper<LoginException> {

    public Response toResponse(LoginException ex) {
        return Response.status(new CustomStatusType(Response.Status.BAD_REQUEST, ex.getMessage())).build();
    }
}
