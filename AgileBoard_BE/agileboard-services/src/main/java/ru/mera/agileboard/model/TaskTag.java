package ru.mera.agileboard.model;

/**
 * Created by antfom on 17.02.2015.
 */
public interface TaskTag extends ABEntity {
    int getId();

    String getTag();

    void setTag(String tag);

    Task getTask();

    void setTask(Task task);

}
