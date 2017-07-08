package ru.mera.agileboard.rest;

import org.osgi.service.http.HttpContext;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.service.UserSessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by antfom on 26.02.2015.
 */
public class HttpContextImpl implements HttpContext {

    private UserSessionService userSessionService;

    public HttpContextImpl(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getPathInfo().equals("/login") || request.getPathInfo().equals("/logout") || request.getPathInfo().equals("/projects")) {
            return true;
        }


        if (request.getQueryString() != null) { //request.getSession(false)!=null  ) {
            for (String s : request.getQueryString().split("&")) {
                if (s.startsWith("authtoken")) {
                    String token = s.substring(10);
                    Optional<Session> session = userSessionService.setUserSession(token);

                    if (session.isPresent()) {
                        return true;
                    }
                }
            }
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "1000");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        //throw new NotAuthorizedException("authtoken not found");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.sendRedirect("/agile/login");

//        return true;
        return false;
    }

    @Override
    public URL getResource(String name) {
        return getClass().getResource(name);
    }

    @Override
    public String getMimeType(String name) {
        return null;
    }
}
