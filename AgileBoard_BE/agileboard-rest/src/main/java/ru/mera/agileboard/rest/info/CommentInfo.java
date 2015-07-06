package ru.mera.agileboard.rest.info;

import ru.mera.agileboard.model.Comment;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antfom on 27.02.2015.
 */
@XmlRootElement
@XmlType(propOrder = {"user", "task", "comment", "created"})
public class CommentInfo {


    private String user;

    private int task;

    private String comment;

    private long created;

    public CommentInfo() {
    }

    public CommentInfo(Comment comm) {
        user = comm.getUser().getName();
        task = comm.getTask().getId();
        comment = comm.getComment();
        created = comm.getCreated();
    }

    public CommentInfo(String user, int task, String comment) {
        this.user = user;
        this.task = task;
        this.comment = comment;
    }

    public static List<CommentInfo> fromComments(Collection<? extends Comment> comment) {
        return comment.stream().map(CommentInfo::new).collect(Collectors.toList());

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "CommentInfo{" +
                "user='" + user + '\'' +
                ", task=" + task +
                ", comment='" + comment + '\'' +
                ", created=" + created +
                '}';
    }
}
