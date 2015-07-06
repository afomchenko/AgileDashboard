package ru.mera.agileboard.rest;

import ru.mera.agileboard.service.UserSessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by antfom on 25.02.2015.
 */
public class ABServlet {

    private UserSessionService userSessionService;

//    public ABServlet(ResourceConfig resourceConfig, UserSessionService userSessionService) {
//        super(resourceConfig);
//        this.userSessionService = userSessionService;
//    }

    //
//    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//        System.err.println(request.getPathInfo());
//        if (!request.getPathInfo().equals("/login") && !request.getPathInfo().equals("/projects")) { //request.getSession(false)!=null  ) {
//            for (String s : request.getQueryString().split("&")) {
//                if (s.startsWith("auth_token")) {
//                    String token = s.substring(11);
//                    Optional<Session> session = userSessionService.setUserSession(token);
//                    request.getSession(true).putValue("session_token", session);
//                }
//            }
//        }


//        HttpSession session = request.getSession(false);
//
//            if (session != null) {
//                System.err.println("from session:" + session.getValue("loggedUser"));
//                userSessionService.setUserSession((User) session.getValue("loggedUser"));
//            }

//
//        super.service(request, response);
    }
}
