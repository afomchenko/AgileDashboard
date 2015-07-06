package ru.mera.agileboard.service;

import com.google.common.util.concurrent.ListenableFuture;
import ru.mera.agileboard.model.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by antfom on 10.02.2015.
 */
public interface TaskService {
    public TaskBuilder createTask();


    public List<Task> getTasks(Filter filter, String... attr);

    public List<Task> getTasks();

    public List<Task> getAllTasks();

    public Optional<Task> getTaskByID(int taskId);

    public Optional<TaskType> getTaskTypeByID(int taskTypeId);

    public Optional<TaskStatus> getTaskStatusByID(int taskStatusId);

    public Optional<TaskType> getTaskTypeByName(String taskTypeName);

    public Optional<TaskStatus> getTaskStatusByName(String statusName);

    public Optional<TaskPriority> getTaskPriorityByName(String taskPriorityName);

    public Optional<TaskPriority> getTaskPriorityByID(int taskPriorityId);

    List<Task> getTaskByUserOpen(int userId);

    List<Task> getTaskByUserProgress(int userId);

    List<Task> getTaskByUserCompleted(int userId);

    List<Task> getTaskByUserCommented(int userId);

    Map<String, Integer> getAllTags();

    Map<String, Integer> findTagsByTask(Task task);

    List<TaskStatus> getAllStatuses();

    List<Task> getTaskFulltext(String str);

    TaskTag createTag(String tag);

    int clearTags(Task task);

    ListenableFuture<List<Task>> getAllTasksAsync();

    List<Task> filterByProjects(Collection<Task> tasks, Collection<Project> projects);

    enum Filter {
        ALL, USERID, TASKID, TASKNAME, PROJECTID, TAG, ASSIGNEE, CREATOR, USERPROJ, USEROPEN, USERCOMPL,
        USERINPROGRESS, USERLOGGED, USERCOMMENTED;
    }
}
