package ru.mera.agileboard.model;

/**
 * Created by antfom on 05.02.2015.
 */

public interface User extends ABEntity {

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);

    int getId();

    long getCreated();

    boolean isObsolete();

    void setObsolete(boolean obsolete);
}
