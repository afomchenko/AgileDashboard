package ru.mera.agileboard.rest.servlets;

import ru.mera.agileboard.rest.info.PatchParameter;
import ru.mera.agileboard.rest.info.UserInfo;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceProviderTest extends TestServer {

    public static final String PATH = "users/";


    @Test
    public void testGetUserById() throws Exception {

        final WebTarget target = target(PATH + USER_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());
        UserInfo userInfo = response.readEntity(UserInfo.class);
        assertNotNull(userInfo);
        assertEquals(USER_NAME, userInfo.getName());
        assertEquals(USER_EMAIL, userInfo.getEmail());
        assertEquals(DATE, userInfo.getCreated());
        assertEquals(USER_ID, userInfo.getId());
        System.err.println("1!!!!!!!!!!" + super.userService.hashCode());
        Mockito.verify(super.userService).findUserByID(Mockito.anyInt());
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {

        final WebTarget target = target(PATH + 999);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetUsers() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        assertEquals(200, response.getStatus());

        List<UserInfo> userInfos = response.readEntity(new GenericType<List<UserInfo>>() {
        });

        for (UserInfo userInfo : userInfos) {
            assertNotNull(userInfo);
            assertEquals(USER_NAME, userInfo.getName());
            assertEquals(USER_EMAIL, userInfo.getEmail());
            assertEquals(DATE, userInfo.getCreated());
            assertEquals(USER_ID, userInfo.getId());
        }
    }

    @Test
    public void testCreateUser() throws Exception {
        final WebTarget target = target(PATH);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new UserInfo(user), MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());
        UserInfo userInfo = response.readEntity(UserInfo.class);
        assertNotNull(userInfo);
        assertEquals(USER_NAME, userInfo.getName());
        assertEquals(USER_EMAIL, userInfo.getEmail());
        assertEquals(DATE, userInfo.getCreated());
        assertEquals(USER_ID, userInfo.getId());
    }

    @Test
    public void testUpdateUser() throws Exception {
        final WebTarget target = target(PATH + USER_ID);

        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(new UserInfo(USER_ID, USER_NAME, USER_EMAIL, DATE, false), MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        UserInfo userInfo = response.readEntity(UserInfo.class);
        assertNotNull(userInfo);
//        verify(user).setName(NAME);
//        verify(user).setEmail(EMAIL);
//        verify(user).store();
    }

    @Test
    public void testPatch() throws Exception {
        final WebTarget target = target(PATH);

        Entity<List<PatchParameter>> entity = Entity.entity(Arrays.asList(new PatchParameter("replace", "/1/name", "new")), MediaType.APPLICATION_JSON);

        Response response = target.request().build("PATCH", entity).invoke();

        assertEquals(200, response.getStatus());
        List<UserInfo> userInfos = response.readEntity(new GenericType<List<UserInfo>>() {
        });
        assertNotNull(userInfos);
//
//        verify(user).store();

    }

    @Test
    public void testDelete() throws Exception {
        final WebTarget target = target(PATH + USER_ID);

        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        assertEquals(200, response.getStatus());
    }

}