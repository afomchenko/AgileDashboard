package ru.mera.agileboard.model;

/**
 * Created by antfom on 17.02.2015.
 */
public interface Comment extends ABEntity {
    Task getTask();

    User getUser();

    long getCreated();

    String getComment();

    int getId();
}
