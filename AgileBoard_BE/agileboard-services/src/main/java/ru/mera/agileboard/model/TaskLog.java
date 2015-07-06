package ru.mera.agileboard.model;

/**
 * Created by antfom on 05.03.2015.
 */
public interface TaskLog extends ABEntity {
    int getId();

    Task getTask();

    void setTask(Task task);

    User getUser();

    void setUser(User user);

    long getDate();

    public void setDate(long date);

    int getLogged();

    void setLogged(int logged);
}
