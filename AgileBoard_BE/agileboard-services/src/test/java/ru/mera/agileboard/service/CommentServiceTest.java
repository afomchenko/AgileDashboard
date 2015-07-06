package ru.mera.agileboard.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.mera.agileboard.db.impl.StorageServiceImpl;
import ru.mera.agileboard.model.Comment;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.StorageSingleton;
import ru.mera.agileboard.service.impl.CommentServiceImpl;
import ru.mera.agileboard.service.impl.TaskServiceImpl;
import ru.mera.agileboard.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CommentServiceTest {
    private static User user;
    private static Task task;
    private static CommentService commentService;
    private String comment;

    public CommentServiceTest(String comment) {
        this.comment = comment;
    }

    @BeforeClass
    public static void setUpClass() {
        StorageSingleton.init(new StorageServiceImpl());
        user = new UserServiceImpl().findUserByID(1).get();
        task = new TaskServiceImpl().getTaskByID(1).get();
        commentService = new CommentServiceImpl();
    }

    @Parameterized.Parameters
    public static List<String[]> comments() {
        ArrayList<String[]> list = new ArrayList<>();
        list.add(new String[]{"comment 1"});
        list.add(new String[]{"comment 2"});
        list.add(new String[]{"comment 3"});
        list.add(new String[]{"comment 4"});

        return list;

    }


    @Test
    public void testFindCommentsByUser() throws Exception {
        List<Comment> comms = commentService.findCommentsByUser(user);
        assertTrue(comms.size() >= 0);
        commentService.createComment(task, user, comment);
        comms = commentService.findCommentsByUser(user);
        for (Comment comm : comms) {
            assertNotNull(comm);
            assertNotNull(comm.getUser());
            assertNotNull(comm.getTask());
            assertNotNull(comm.getComment());
            assertNotEquals("", comm.getComment());
            assertTrue(comm.getCreated() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
            assertTrue(comm.getId() > 0);
        }
    }

    @Test
    public void testFindCommentsByTask() throws Exception {
        List<Comment> comms = commentService.findCommentsByTask(task);
        assertTrue(comms.size() >= 0);
        commentService.createComment(task, user, comment);
        comms = commentService.findCommentsByTask(task);
        for (Comment comm : comms) {
            assertNotNull(comm);
            assertNotNull(comm.getUser());
            assertNotNull(comm.getTask());
            assertNotNull(comm.getComment());
            assertNotEquals("", comm.getComment());
            assertTrue(comm.getCreated() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
            assertTrue(comm.getId() > 0);
        }
    }

    @Test
    public void testGetAllComments() throws Exception {
        List<Comment> comms = commentService.getAllComments();
        for (Comment comm : comms) {
            assertNotNull(comm);
            assertNotNull(comm.getUser());
            assertNotNull(comm.getTask());
            assertNotNull(comm.getComment());
            assertNotEquals("", comm.getComment());
            assertTrue(comm.getCreated() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000);
            assertTrue(comm.getId() > 0);
        }

    }

    @Test
    public void testCreateComment() throws Exception {
        Comment comm = commentService.createComment(task, user, comment);
        assertEquals(user, comm.getUser());
        assertEquals(task, comm.getTask());
        assertEquals(comment, comm.getComment());
        assertTrue(comm.getId() > 0);
        comm.delete();
    }
}