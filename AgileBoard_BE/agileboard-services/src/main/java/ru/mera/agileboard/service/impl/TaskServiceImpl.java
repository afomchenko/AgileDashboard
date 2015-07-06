package ru.mera.agileboard.service.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import org.apache.felix.scr.annotations.*;
import ru.mera.agileboard.model.*;
import ru.mera.agileboard.model.impl.*;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.UserSessionService;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by antfom on 10.02.2015.
 */
@Component(name = "ru.mera.agileboard.service.TaskServiceComponent", immediate = true)
@Service(value = ru.mera.agileboard.service.TaskService.class)
public class TaskServiceImpl implements TaskService {

    private ListeningExecutorService service;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC, name = "userService")
    private volatile UserService userService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC, name = "projectService")
    private volatile ProjectService projectService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC, name = "userSessionRef")
    private volatile UserSessionService userSessionService;

    public TaskServiceImpl() {
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }

    public TaskBuilder createTask() {
        return new TaskImpl.TaskBuilderImpl(userSessionService);
    }

    @Override
    public List<Task> getTasks() {
        return getTasks(Filter.ALL, "");
    }

    @Override
    public Optional<Task> getTaskByID(int taskId) {

        Task task = null;
        Dao<TaskImpl, Integer> dao = TaskImpl.getDao();

        try {
            task = dao.queryForId(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(task);
    }

    @Override
    public List<Task> getTasks(Filter filter, String... attr) {

        switch (filter) {
            case USERID:
                return getTaskByUserID(Integer.parseInt(attr[0]));
            case ASSIGNEE:
                return getTaskByAssignee(Integer.parseInt(attr[0]));
            case CREATOR:
                return getTaskByCreator(Integer.parseInt(attr[0]));
            case PROJECTID:
                return getTaskByProjectID(Integer.parseInt(attr[0]));
            case USERPROJ:
                return getTaskByUserProjectID(Integer.parseInt(attr[0]), Integer.parseInt(attr[1]));
            case TAG:
                return getTaskByTag(attr[0]);
            case USEROPEN:
                return getTaskByUserOpen(Integer.parseInt(attr[0]));
            case USERINPROGRESS:
                return getTaskByUserProgress(Integer.parseInt(attr[0]));
            case USERCOMPL:
                return getTaskByUserCompleted(Integer.parseInt(attr[0]));
            case USERCOMMENTED:
                return getTaskByUserCommented(Integer.parseInt(attr[0]));
            case USERLOGGED:
                return getTaskByUserLogged(Integer.parseInt(attr[0]));

            default:
                return getAllTasks();
        }
    }

    public ListenableFuture<List<Task>> getAllTasksAsync() {
        return service.submit(new Callable<List<Task>>() {
            @Override
            public List<Task> call() throws Exception {
                return TaskServiceImpl.this.getAllTasks();
            }
        });
    }

    public List<Task> getAllTasks() {
        Dao<TaskImpl, Integer> dao = TaskImpl.getDao();

        try {
            return new ArrayList<>(dao.queryForAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    public List<Task> getTaskByTag(String tag) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<TaskTagImpl, Integer> tagDao = TaskTagImpl.getDao();

        try {
            QueryBuilder<TaskTagImpl, Integer> qTag = tagDao.queryBuilder();
            qTag.selectColumns("task_id").where().eq("tag_name", tag);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("task_id", qTag);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<Task> getTaskByUserID(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<ProjectUsers, Integer> projectUsersDao = ProjectUsers.getDao();

        try {
            QueryBuilder<ProjectUsers, Integer> qProjUser = projectUsersDao.queryBuilder();
            qProjUser.selectColumns("project_id").where().eq("user_id", userId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("project_id", qProjUser);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByUserOpen(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<ProjectUsers, Integer> projectUsersDao = ProjectUsers.getDao();

        try {
            QueryBuilder<ProjectUsers, Integer> qProjUser = projectUsersDao.queryBuilder();
            qProjUser.selectColumns("project_id").where().eq("user_id", userId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("project_id", qProjUser).and().eq("task_status_id", 1);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByUserProgress(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<ProjectUsers, Integer> projectUsersDao = ProjectUsers.getDao();

        try {
            QueryBuilder<ProjectUsers, Integer> qProjUser = projectUsersDao.queryBuilder();
            qProjUser.selectColumns("project_id").where().eq("user_id", userId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("project_id", qProjUser).and().eq("task_status_id", 2);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByUserCompleted(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<ProjectUsers, Integer> projectUsersDao = ProjectUsers.getDao();

        try {
            QueryBuilder<ProjectUsers, Integer> qProjUser = projectUsersDao.queryBuilder();
            qProjUser.selectColumns("project_id").where().eq("user_id", userId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("project_id", qProjUser).and().gt("task_status_id", 2);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByUserCommented(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<CommentImpl, Integer> commentDao = CommentImpl.getDao();

        try {
            QueryBuilder<CommentImpl, Integer> qComment = commentDao.queryBuilder();
            qComment.selectColumns("task_id").where().eq("user_id", userId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("task_id", qComment);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByUserLogged(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<TaskLogImpl, Integer> logDao = TaskLogImpl.getDao();

        try {
            QueryBuilder<TaskLogImpl, Integer> qLog = logDao.queryBuilder();
            qLog.selectColumns("task_id").where().eq("user_id", userId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("task_id", qLog);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByUserProjectID(int userId, int projectId) {
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        Dao<ProjectUsers, Integer> projectUsersDao = ProjectUsers.getDao();

        try {
            QueryBuilder<ProjectUsers, Integer> qProjUser = projectUsersDao.queryBuilder();
            qProjUser.selectColumns("project_id").where().eq("user_id", userId).and().eq("project_id", projectId);

            QueryBuilder<TaskImpl, Integer> qTasks = taskDao.queryBuilder();
            qTasks.where().in("project_id", qProjUser);

            return new ArrayList<>(taskDao.query(qTasks.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskFulltext(String str) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        try {
            QueryBuilder<TaskImpl, Integer> qTask = taskDao.queryBuilder();
            qTask.where().raw("MATCH (task_name, task_desc, task_teststeps) AGAINST ('" + str + "')");
            System.err.println(qTask.prepare());
            return new ArrayList<>(taskDao.query(qTask.prepare()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public TaskTag createTag(String tag) {
        TaskTag tg = new TaskTagImpl(tag);
        tg.store();
        return tg;
    }

    @Override
    public int clearTags(Task task) {
        Dao<TaskTagImpl, Integer> tagDao = TaskTagImpl.getDao();

        try {
            DeleteBuilder<TaskTagImpl, Integer> qTag = tagDao.deleteBuilder();

            qTag.where().eq("task_id", task);

            return tagDao.delete(qTag.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Task> getTaskByAssignee(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        try {
            return new ArrayList<>(taskDao.queryForEq("user_assignee_id", userId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByCreator(int userId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        try {
            return new ArrayList<>(taskDao.queryForEq("user_created_id", userId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> getTaskByProjectID(int projectId) {
        TaskType task = null;
        Dao<TaskImpl, Integer> taskDao = TaskImpl.getDao();
        try {
            return new ArrayList<>(taskDao.queryForEq("project_id", projectId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public Optional<TaskType> getTaskTypeByID(int taskTypeId) {

        TaskType taskType = null;
        Dao<TaskTypeImpl, Integer> dao = TaskTypeImpl.getDao();

        try {
            taskType = dao.queryForId(taskTypeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return Optional.ofNullable(taskType);

    }

    @Override
    public Optional<TaskStatus> getTaskStatusByID(int taskStatusId) {

        TaskStatus taskStatus = null;
        Dao<TaskStatusImpl, Integer> dao = TaskStatusImpl.getDao();

        try {
            taskStatus = dao.queryForId(taskStatusId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return Optional.ofNullable(taskStatus);

    }

    @Override
    public Optional<TaskPriority> getTaskPriorityByID(int taskPriorityId) {


        TaskPriority taskPriority = null;
        Dao<TaskPriorityImpl, Integer> dao = TaskPriorityImpl.getDao();

        try {
            taskPriority = dao.queryForId(taskPriorityId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(taskPriority);
    }

    @Override
    public Optional<TaskPriority> getTaskPriorityByName(String taskPriorityName) {


        TaskPriority taskPriority = null;

        Dao<TaskPriorityImpl, Integer> dao = TaskPriorityImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("task_priority_name", taskPriorityName);
            taskPriority = dao.queryForFieldValues(values).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(taskPriority);
    }

    @Override
    public Optional<TaskType> getTaskTypeByName(String taskTypeName) {
        TaskType taskType = null;

        Dao<TaskTypeImpl, Integer> dao = TaskTypeImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("task_type_name", taskTypeName);
            taskType = dao.queryForFieldValues(values).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(taskType);
    }

    @Override
    public Optional<TaskStatus> getTaskStatusByName(String statusName) {

        TaskStatus taskStatus = null;

        Dao<TaskStatusImpl, Integer> dao = TaskStatusImpl.getDao();

        try {
            HashMap<String, Object> values = new HashMap<>();
            values.put("task_status_name", statusName);
            taskStatus = dao.queryForFieldValues(values).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(taskStatus);
    }

    public Map<String, Integer> findTagsByTask(Task task) {

        Dao<TaskTagImpl, Integer> tagDao = TaskTagImpl.getDao();

        try {
            List<TaskTag> list = new ArrayList<>(tagDao.queryForEq("task_id", task.getId()));
            return list.stream().collect(HashMap::new, (map, i) -> {
                map.merge(i.getTag(), 1, (j, k) -> j + k);
            }, HashMap::putAll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public Map<String, Integer> getAllTags() {

        Dao<TaskTagImpl, Integer> tagDao = TaskTagImpl.getDao();

        try {
            List<TaskTag> list = new ArrayList<>(tagDao.queryForAll());
            return list.stream().collect(HashMap::new, (map, i) -> {
                map.merge(i.getTag(), 1, (j, k) -> j + k);
            }, HashMap::putAll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public List<TaskStatus> getAllStatuses() {

        Dao<TaskStatusImpl, Integer> statusDao = TaskStatusImpl.getDao();

        try {
            return new ArrayList<>(statusDao.queryForAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Task> filterByProjects(Collection<Task> tasks, Collection<Project> projects) {
        return tasks.stream().filter(e -> projects.contains(e.getProject())).collect(Collectors.toList());
    }
}
