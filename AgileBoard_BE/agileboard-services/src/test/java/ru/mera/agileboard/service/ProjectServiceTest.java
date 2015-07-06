package ru.mera.agileboard.service;

import com.j256.ormlite.table.TableUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.ProjectImpl;
import ru.mera.agileboard.model.impl.StorageSingleton;
import ru.mera.agileboard.service.impl.ProjectServiceImpl;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class ProjectServiceTest {

    private static ProjectService projectService;
    private static User user;

    private String shortName;
    private String name;
    private String desc;

    public ProjectServiceTest(String shortName, String name, String desc) {
        this.shortName = shortName;
        this.name = name;
        this.desc = desc;
    }

    @BeforeClass
    public static void setUpClass() {
        StorageSingleton.init(new StorageServiceImpl());
        projectService = new ProjectServiceImpl();
        user = new UserServiceImpl().findUserByID(1).get();
    }

    @Parameterized.Parameters
    public static List<String[]> paramGenerator() {
        ArrayList<String[]> list = new ArrayList<>();
        list.add(new String[]{"TP1", "Test 1", "This is test project 1"});
        list.add(new String[]{"TP2", "Test 2", "This is test project 2"});
        list.add(new String[]{"TP3", "Test 3", "This is test project 3"});
        list.add(new String[]{"TP4", "Test 4", "This is test project 4"});

        return list;
    }

    @Before
    public void setUp() throws SQLException {
        Project p1 = new ProjectImpl("SP1", "First project", "First project description");
        Project p2 = new ProjectImpl("SP2", "Second project", "Second project description");
        TableUtils.dropTable(StorageSingleton.getStorage().getConnection(), p1.getClass(), true);
        TableUtils.createTableIfNotExists(StorageSingleton.getStorage().getConnection(), p1.getClass());
        p1.store();
        p2.store();
    }

    @Test
    public void testCreateProject() throws Exception {
        Project project = projectService.createProject(shortName, name, desc);
        assertNotNull(project);
        assertEquals(shortName, project.getShortName());
        assertEquals(name, project.getName());
        assertEquals(desc, project.getDesc());
        assertTrue(project.getId() > 0);
    }

    @Test
    public void testGetProjects() throws Exception {
        List<Project> projects = projectService.getProjects();
        for (Project project : projects) {
            assertNotNull(project);
            assertNotNull(project.getShortName());
            assertNotNull(project.getName());
            assertNotNull(project.getDesc());
            assertTrue(project.getId() > 0);
        }
    }

    @Test
    public void testGetProjectByID() throws Exception {
        Optional<Project> optProj = projectService.getProjectByID(1);
        assertTrue(optProj.isPresent());
        Project project = optProj.get();
        assertNotNull(project);
        assertNotNull(project.getShortName());
        assertNotNull(project.getName());
        assertNotNull(project.getDesc());
        assertEquals(1, project.getId());
    }

    @Test
    public void testGetProjectByName() throws Exception {
        Project ref = projectService.createProject(shortName, name, desc);
        Optional<Project> optProj = projectService.getProjectByName(name);
        assertTrue(optProj.isPresent());
        Project project = optProj.get();
        assertNotNull(project);
        assertNotNull(project.getShortName());
        assertNotNull(project.getName());
        assertNotNull(project.getDesc());
        assertEquals(ref.getId(), project.getId());
        assertEquals(ref.getShortName(), project.getShortName());
        assertEquals(ref.getName(), project.getName());
        assertEquals(ref.getDesc(), project.getDesc());
    }

    @Test
    public void testGetProjectByShortName() throws Exception {
        Project ref = projectService.createProject(shortName, name, desc);
        Optional<Project> optProj = projectService.getProjectByShortName(shortName);
        assertTrue(optProj.isPresent());
        Project project = optProj.get();
        assertNotNull(project);
        assertNotNull(project.getShortName());
        assertNotNull(project.getName());
        assertNotNull(project.getDesc());
        assertEquals(ref.getId(), project.getId());
        assertEquals(ref.getShortName(), project.getShortName());
        assertEquals(ref.getName(), project.getName());
        assertEquals(ref.getDesc(), project.getDesc());
    }

    @Test
    public void testGetProjectsByUser() throws Exception {
        Project ref = projectService.createProject(shortName, name, desc);
        ref.addProjectUser(user);
        ref.store();
        List<Project> projects = projectService.getProjectsByUser(user);
        assertTrue(projects.size() > 0);
        for (Project project : projects) {
            assertNotNull(project);
            assertNotNull(project.getShortName());
            assertNotNull(project.getName());
            assertNotNull(project.getDesc());
            assertTrue(project.getId() > 0);
        }
    }

    @Test
    public void testGetUsersOfProject() throws Exception {
        Project ref = projectService.createProject(shortName, name, desc);
        ref.addProjectUser(user);
        ref.store();
        List<User> projUsers = projectService.getUsersOfProject(ref);
        for (User projUser : projUsers) {
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
}