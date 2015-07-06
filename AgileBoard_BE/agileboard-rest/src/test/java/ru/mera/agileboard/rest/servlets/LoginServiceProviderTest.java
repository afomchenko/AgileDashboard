package ru.mera.agileboard.rest.servlets;

import org.junit.Test;
import ru.mera.agileboard.rest.info.LoginInfo;
import ru.mera.agileboard.rest.info.UserProjectInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class LoginServiceProviderTest extends TestServer {

    public static final String PATH_LOGIN = "login";
    public static final String PATH_LOGOUT = "logout";


    @Test
    public void testLogin() throws Exception {
        final WebTarget target = target(PATH_LOGIN);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new LoginInfo(String.valueOf(project.getId()), user.getName()), MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        UserProjectInfo info = response.readEntity(UserProjectInfo.class);
        assertEquals(project.getId(), info.getProject().getId());
        assertEquals(project.getName(), info.getProject().getName());
        assertEquals(user.getId(), info.getUser().getId());
        assertEquals(user.getName(), info.getUser().getName());
        assertNotNull(info.getAuthtoken());
        assertNotEquals("", info.getAuthtoken());
    }

    @Test
    public void testLoginNotFound() throws Exception {
        final WebTarget target = target(PATH_LOGIN);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new LoginInfo(String.valueOf(project.getId()), "anothername"), MediaType.APPLICATION_JSON));

        assertEquals(400, response.getStatus());

    }

    @Test
    public void testLogin994() throws Exception {
        final WebTarget target = target(PATH_LOGIN);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(null);

        assertEquals(400, response.getStatus());

    }


    @Test
    public void testLogout() throws Exception {
        final WebTarget target = target(PATH_LOGOUT).queryParam("authtoken", AUTHTOKEN);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testLogoutNotFound() throws Exception {
        final WebTarget target = target(PATH_LOGOUT).queryParam("authtoken", AUTHTOKEN + "sometext");

        Response response = target.request().get();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void testLogoutWithoutToken() throws Exception {
        final WebTarget target = target(PATH_LOGOUT);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, response.getStatus());
    }
}