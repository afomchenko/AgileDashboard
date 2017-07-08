package ru.mera.agileboard.service;

import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.StorageSingleton;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private static UserService userService;
    private String name = "test";
    private String email = "test@email";

    @BeforeClass
    public static void setUp() {
        StorageSingleton.init(new StorageServiceImpl());
        userService = new UserServiceImpl();
    }

    @Test
    public void testFindUserByName() throws Exception {
        Optional<User> optUser = userService.findUserByName("user");

        assertTrue(optUser.isPresent());
        User user = optUser.get();

        assertNotNull(user);
        assertFalse(user.getName() == null);
        assertFalse(user.getName().equals(""));
        assertFalse(user.getEmail() == null);
        assertFalse(user.getEmail().equals(""));
        assertTrue(user.getCreated() > 0);
        assertTrue(user.getCreated() < LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        assertTrue(user.getId() > 0);
    }

    @Test
    public void testFindUserByID() throws Exception {

        Optional<User> optUser = userService.findUserByID(1);

        assertTrue(optUser.isPresent());
        User user = optUser.get();

        assertNotNull(user);
        assertFalse(user.getName() == null);
        assertFalse(user.getName().equals(""));
        assertFalse(user.getEmail() == null);
        assertFalse(user.getEmail().equals(""));
        assertTrue(user.getCreated() > 0);
        assertTrue(user.getCreated() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        assertTrue(user.getId() > 0);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            assertNotNull(user);
            assertFalse(user.getName() == null);
            assertFalse(user.getName().equals(""));
            assertFalse(user.getEmail() == null);
            assertFalse(user.getEmail().equals(""));
            assertTrue(user.getCreated() > 0);
            assertTrue(user.getCreated() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
            assertTrue(user.getId() > 0);
        }
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = userService.createUser(name, email);

        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertTrue(user.getCreated() > 0);
        assertTrue(user.getCreated() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
        assertFalse(user.isObsolete());
        assertTrue(user.getId() > 0);

        user.delete();
    }
}