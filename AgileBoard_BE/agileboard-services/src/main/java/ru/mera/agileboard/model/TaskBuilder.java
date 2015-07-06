package ru.mera.agileboard.model;

/**
 * Created by antfom on 17.02.2015.
 */
public interface TaskBuilder {

    TaskBuilder name(String name);

    TaskBuilder description(String description);

    TaskBuilder testSteps(String testSteps);

    TaskBuilder created(long created);

    TaskBuilder updated(long updated);

    TaskBuilder type(TaskType type);

    TaskBuilder status(TaskStatus status);

    TaskBuilder priority(TaskPriority priority);

    TaskBuilder project(Project project);

    TaskBuilder creator(User user);

    TaskBuilder assignee(User user);

    TaskBuilder estimated(int estimated);

    Task build();
}
