package ru.mera.agileboard.model;

/**
 * Created by antfom on 17.02.2015.
 */
public interface TaskStatus extends ABEntity {
    int getId();

    String getName();

    void setName(String name);
}
