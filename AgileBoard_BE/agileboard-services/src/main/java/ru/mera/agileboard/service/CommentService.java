package ru.mera.agileboard.service;

import ru.mera.agileboard.model.Comment;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.User;

import java.util.List;

/**
 * Created by antfom on 27.02.2015.
 */
public interface CommentService {

    List<Comment> findCommentsByUser(User user);

    List<Comment> findCommentsByTask(Task task);

    List<Comment> getAllComments();

    Comment createComment(Task task, User user, String comment);

}
