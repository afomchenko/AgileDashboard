package ru.mera.agileboard.rest.servlets;

import com.fasterxml.jackson.core.JsonParseException;
import ru.mera.agileboard.rest.CustomStatusType;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by antfom on 16.03.2015.
 */
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    public Response toResponse(JsonParseException ex) {
        return Response.status(new CustomStatusType(Response.Status.BAD_REQUEST, "Malformed JSON input format")).build();
    }
}
