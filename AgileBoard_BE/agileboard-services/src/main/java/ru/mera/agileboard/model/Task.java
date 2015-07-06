package ru.mera.agileboard.model;

/**
 * Created by antfom on 05.02.2015.
 */
public interface Task extends ABEntity {


    String getName();

    void setName(String name);

    String getTestSteps();

    void setTestSteps(String testSteps);

    long getUpdated();

    void setUpdated(long updated);

    TaskStatus getStatus();

    void setStatus(TaskStatus status);

    TaskType getType();

    void setType(TaskType type);

    int getId();

    TaskPriority getPriority();

    void setPriority(TaskPriority priority);

    String getDescription();

    void setDescription(String description);

    long getCreated();

    void setCreated(long created);

    Project getProject();

    void setProject(Project project);

    User getCreator();

    void setCreator(User creator);

    User getAssignee();

    void setAssignee(User assignee);

    int getEstimated();

    void setEstimated(int estimated);


    void addTag(String tagString);
}
