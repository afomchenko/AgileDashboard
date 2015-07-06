package ru.mera.agileboard.service.impl;

import com.j256.ormlite.dao.Dao;
import org.apache.felix.scr.annotations.*;
import ru.mera.agileboard.model.Comment;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.model.impl.CommentImpl;
import ru.mera.agileboard.service.CommentService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by antfom on 27.02.2015.
 */
@Component(name = "ru.mera.agileboard.service.CommentServiceComponent", immediate = true)
@Service(value = ru.mera.agileboard.service.CommentService.class)
public class CommentServiceImpl implements CommentService {

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC, name = "userService")
    private volatile UserService userService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC, name = "taskService")
    private volatile TaskService taskService;

    @Override
    public List<Comment> findCommentsByUser(User user) {
        Dao<CommentImpl, Integer> dao = CommentImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("user_id", user);
            List<Comment> list = new ArrayList<>(dao.queryForFieldValues(values));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Comment> findCommentsByTask(Task task) {
        System.err.println("taskid = " + task.getId());
        Dao<CommentImpl, Integer> dao = CommentImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("task_id", task);
            List<Comment> list = new ArrayList<>(dao.queryForFieldValues(values));
            System.err.println(list);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<Comment> getAllComments() {
        Dao<CommentImpl, Integer> dao = CommentImpl.getDao();

        try {
            List<Comment> list = new ArrayList<>(dao.queryForAll());
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Comment createComment(Task task, User user, String comment) {
        Comment newComment = new CommentImpl(task, user, comment);
        newComment.store();
        return newComment;
    }
}
