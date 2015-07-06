package ru.mera.agileboard.model;

/**
 * Created by antfom on 26.02.2015.
 */
public interface TaskPriority extends ABEntity {
    int getId();

    String getName();

    void setName(String name);
}
