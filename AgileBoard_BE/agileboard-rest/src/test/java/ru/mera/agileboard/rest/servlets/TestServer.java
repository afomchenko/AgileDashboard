package ru.mera.agileboard.rest.servlets;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import ru.mera.agileboard.model.Comment;
import ru.mera.agileboard.model.Project;
import ru.mera.agileboard.model.Session;
import ru.mera.agileboard.model.Task;
import ru.mera.agileboard.model.TaskBuilder;
import ru.mera.agileboard.model.TaskLog;
import ru.mera.agileboard.model.TaskPriority;
import ru.mera.agileboard.model.TaskStatus;
import ru.mera.agileboard.model.TaskTag;
import ru.mera.agileboard.model.TaskType;
import ru.mera.agileboard.model.User;
import ru.mera.agileboard.service.CommentService;
import ru.mera.agileboard.service.LoggingService;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.UserSessionService;

import javax.ws.rs.core.Application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.AdditionalMatchers.and;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by antfom on 13.03.2015.
 */
public class TestServer extends JerseyTest {

    public static final String AUTHTOKEN = "c717308e-8f62-4aed-a4fb-67f6c60f675d";

    public static final String PROJECT_SHORT_NAME = "TP1";
    public static final String PROJECT_NAME = "Test Project";
    public static final String PROJECT_DESC = "Test Project Description";
    public static final int PROJECT_ID = 1;

    public static final int USER_ID = 1;
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email@email.ru";
    public static final long DATE = 142608302600L;

    public static final int COMMENT_ID = 1;
    public static final String COMMENT = "this is comment";

    public static final String TAG1 = "tag1";
    public static final String TAG2 = "tag2";
    public static final int TAG_ID1 = 1;
    public static final int TAG_ID2 = 2;

    public static final int LOG_ID = 1;
    public static final int LOGGED = 42;

    public static final int TASK_ID = 1;
    public static final String TASK_NAME = "Task Name";
    public static final String TASK_DESC = "Task Description";
    public static final String TASK_TESTSTEPS = "Task Teststeps";

    public static final String STATUS_NAME = "New";
    public static final int STATUS_ID = 1;

    public static final String TYPE_NAME = "Bug";
    public static final int TYPE_ID = 1;

    public static final String PRIORITY_NAME = "Critical";
    public static final int PRIORITY_ID = 1;
    @Mock
    ProjectService projectService;
    @Mock
    UserService userService;
    @Mock
    UserSessionService sessionService;
    @Mock
    TaskService taskService;
    @Mock
    CommentService commentService;
    @Mock
    LoggingService loggingService;
    @Mock
    User user;
    @Mock
    User userAssignee;
    @Mock
    Project project;
    @Mock
    Session session;
    @Mock
    Comment comment;
    @Mock
    Task task;
    @Mock
    TaskTag tag1;
    @Mock
    TaskTag tag2;
    @Mock
    TaskStatus status;
    @Mock
    TaskLog log;
    @Mock
    TaskPriority priority;
    @Mock
    TaskType taskType;
    @Mock
    TaskBuilder builder;
    private ListeningExecutorService service;

    @Override
    protected Application configure() {
        System.err.println("start configuring");

        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        initMocks();

        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        JacksonJsonProvider json = new JacksonJsonProvider().
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).
                configure(SerializationFeature.INDENT_OUTPUT, true);

        ResourceConfig config = new ResourceConfig();

        config.packages("ru.mera.agileboard.rest.servlets");
        config.register(json);
        config.register(HttpMethodOverrideFilter.class);

        //config.register(JsonMoxyConfigurationContextResolver.class);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(userService).to(UserService.class);
                bind(projectService).to(ProjectService.class);
                bind(sessionService).to(UserSessionService.class);
                bind(commentService).to(CommentService.class);
                bind(taskService).to(TaskService.class);
                bind(loggingService).to(LoggingService.class);
            }
        });
        System.err.println("end configuring");
        return config;

    }

    @Override
    protected void configureClient(ClientConfig clientConfig) {
        JacksonJsonProvider json = new JacksonJsonProvider();
        json.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        clientConfig.register(json);
        clientConfig.connectorProvider(new GrizzlyConnectorProvider());
    }

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        when(tag1.getTag()).thenReturn(TAG1);
        when(tag1.getId()).thenReturn(TAG_ID1);
        when(tag2.getTag()).thenReturn(TAG1);
        when(tag2.getId()).thenReturn(TAG_ID2);

        when(taskType.getId()).thenReturn(TYPE_ID);
        when(taskType.getName()).thenReturn(TYPE_NAME);

        when(priority.getName()).thenReturn(PRIORITY_NAME);
        when(priority.getId()).thenReturn(PRIORITY_ID);

        Map<String, Integer> resMap = new HashMap<>();
        resMap.put(TAG1, TAG_ID1);
        resMap.put(TAG2, TAG_ID2);

        when(log.getId()).thenReturn(LOG_ID);
        when(log.getDate()).thenReturn(DATE);
        when(log.getUser()).thenReturn(user);
        when(log.getTask()).thenReturn(task);
        when(log.getLogged()).thenReturn(LOGGED);

        when(status.getName()).thenReturn(STATUS_NAME);
        when(status.getId()).thenReturn(STATUS_ID);

        when(user.getName()).thenReturn(USER_NAME);
        when(user.getEmail()).thenReturn(USER_EMAIL);
        when(user.getCreated()).thenReturn(DATE);
        when(user.getId()).thenReturn(USER_ID);
        when(user.isObsolete()).thenReturn(false);

        when(userAssignee.getName()).thenReturn(USER_NAME);
        when(userAssignee.getEmail()).thenReturn(USER_EMAIL);
        when(userAssignee.getCreated()).thenReturn(DATE);
        when(userAssignee.getId()).thenReturn(USER_ID + 1);
        when(userAssignee.isObsolete()).thenReturn(false);

        when(project.getId()).thenReturn(PROJECT_ID);
        when(project.getName()).thenReturn(PROJECT_NAME);
        when(project.getShortName()).thenReturn(PROJECT_SHORT_NAME);
        when(project.getDesc()).thenReturn(PROJECT_DESC);

        when(session.getProject()).thenReturn(project);
        when(session.getUser()).thenReturn(user);
        when(session.getToken()).thenReturn(AUTHTOKEN);

        when(comment.getUser()).thenReturn(user);
        when(comment.getTask()).thenReturn(task);
        when(comment.getId()).thenReturn(COMMENT_ID);
        when(comment.getCreated()).thenReturn(DATE);
        when(comment.getComment()).thenReturn(COMMENT);

        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getId()).thenReturn(TASK_ID);
        when(task.getName()).thenReturn(TASK_NAME);
        when(task.getCreated()).thenReturn(DATE);
        when(task.getDescription()).thenReturn(TASK_DESC);
        when(task.getTestSteps()).thenReturn(TASK_TESTSTEPS);
        when(task.getCreator()).thenReturn(user);
        when(task.getAssignee()).thenReturn(userAssignee);
        when(task.getPriority()).thenReturn(priority);
        when(task.getType()).thenReturn(taskType);
        when(task.getEstimated()).thenReturn(LOGGED);
        when(task.getUpdated()).thenReturn(DATE);
        when(task.getProject()).thenReturn(project);
        when(task.getStatus()).thenReturn(status);

        when(sessionService.getUserSession()).thenReturn(session);
        when(sessionService.newUserSession(user, project)).thenReturn(session);
        when(sessionService.setUserSession(eq(AUTHTOKEN))).thenReturn(Optional.ofNullable(session));
        when(sessionService.setUserSession(not(eq(AUTHTOKEN)))).thenReturn(Optional.ofNullable(null));

        when(userService.findUserByID(eq(USER_ID))).thenReturn(Optional.ofNullable(user));
        when(userService.findUserByID(eq(USER_ID + 1))).thenReturn(Optional.ofNullable(userAssignee));
        when(userService.findUserByID(and(not(eq(USER_ID)), not(eq(USER_ID + 1))))).thenReturn(Optional.ofNullable(null));
        when(userService.getAllUsers()).thenReturn(new ArrayList<User>(Arrays.asList(user)));
        when(userService.findUserByName(eq(USER_NAME))).thenReturn(Optional.ofNullable(user));
        when(userService.findUserByName(not(eq(USER_NAME)))).thenReturn(Optional.ofNullable(null));
        when(userService.createUser(USER_NAME, USER_EMAIL)).thenReturn(user);

        when(projectService.createProject(PROJECT_SHORT_NAME, PROJECT_NAME, PROJECT_DESC)).thenReturn(project);
        when(projectService.getProjectByID(PROJECT_ID)).thenReturn(Optional.ofNullable(project));
        when(projectService.getProjectByName(PROJECT_NAME)).thenReturn(Optional.ofNullable(project));
        when(projectService.getProjectByShortName(PROJECT_SHORT_NAME)).thenReturn(Optional.ofNullable(project));
        when(projectService.getProjects()).thenReturn(new ArrayList<>(Arrays.asList(project)));
        when(projectService.getProjectsByUser(user)).thenReturn(new ArrayList<>(Arrays.asList(project)));
        when(projectService.getUsersOfProject(project)).thenReturn(new ArrayList<>(Arrays.asList(user)));
        when(projectService.isUserOfProject(project, user)).thenReturn(true);

        when(commentService.createComment(task, user, COMMENT)).thenReturn(comment);
        when(commentService.findCommentsByTask(task)).thenReturn(new ArrayList<>(Arrays.asList(comment)));
        when(commentService.findCommentsByUser(user)).thenReturn(new ArrayList<>(Arrays.asList(comment)));
        when(commentService.getAllComments()).thenReturn(new ArrayList<>(Arrays.asList(comment)));

        when(loggingService.createLog(eq(task), eq(user), eq(LOGGED), anyLong())).thenReturn(log);
        when(loggingService.getLogByUserDaily(eq(user), anyLong())).thenReturn(new ArrayList<TaskLog>(Arrays.asList(log)));
        when(loggingService.getRecentLogByUser(user)).thenReturn(new ArrayList<TaskLog>(Arrays.asList(log)));
        when(loggingService.getLoggedSummaryByTask(task)).thenReturn(42);
        HashMap<Task, Integer> logMapTask = new HashMap<>();
        logMapTask.put(task, LOGGED);
        HashMap<Long, Integer> logMapDate = new HashMap<>();
        logMapDate.put(DATE, LOGGED);
        when(loggingService.getLoggedByTask(Arrays.asList(task))).thenReturn(logMapTask);
        when(loggingService.getRecentLogSumByUser(user)).thenReturn(logMapDate);

        when(taskService.clearTags(task)).thenReturn(5);
        when(taskService.createTag(TAG1)).thenReturn(tag1);
        when(taskService.createTag(TAG2)).thenReturn(tag2);
        when(taskService.getAllTags()).thenReturn(resMap);
        when(taskService.findTagsByTask(task)).thenReturn(resMap);
        when(taskService.getTaskByID(eq(TASK_ID))).thenReturn(Optional.ofNullable(task));
        when(taskService.getTaskByID(not(eq(TASK_ID)))).thenReturn(Optional.ofNullable(null));
        when(taskService.getTasks(TaskService.Filter.USERPROJ, String.valueOf(user.getId()), String.valueOf(project.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.USERID, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.USEROPEN, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.USERCOMPL, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.USERINPROGRESS, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.USERCOMMENTED, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.CREATOR, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.USERLOGGED, String.valueOf(user.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.ASSIGNEE, String.valueOf(userAssignee.getId())))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks(TaskService.Filter.TAG, tag1.getTag()))
                .thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.getTasks()).thenReturn(new ArrayList<>(Arrays.asList(task)));
        when(taskService.createTask()).thenReturn(builder);
        when(taskService.getTaskTypeByName(TYPE_NAME)).thenReturn(Optional.ofNullable(taskType));
        when(taskService.getTaskTypeByID(TYPE_ID)).thenReturn(Optional.ofNullable(taskType));
        when(taskService.getTaskPriorityByID(PRIORITY_ID)).thenReturn(Optional.ofNullable(priority));
        when(taskService.getTaskPriorityByName(PRIORITY_NAME)).thenReturn(Optional.ofNullable(priority));
        when(taskService.getTaskFulltext(TASK_DESC)).thenReturn(Arrays.asList(task));
        when(taskService.getAllTasksAsync()).thenReturn(service.submit(() -> taskService.getAllTasks()));
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task));
        when(taskService.filterByProjects(anyCollectionOf(Task.class), anyCollectionOf(Project.class))).thenReturn(Arrays.asList(task));


        when(builder.build()).thenReturn(task);
        when(builder.updated(DATE)).thenReturn(builder);
        when(builder.assignee(userAssignee)).thenReturn(builder);
        when(builder.creator(user)).thenReturn(builder);
        when(builder.project(project)).thenReturn(builder);
        when(builder.created(DATE)).thenReturn(builder);
        when(builder.name(TASK_NAME)).thenReturn(builder);
        when(builder.description(TASK_DESC)).thenReturn(builder);
        when(builder.testSteps(TASK_TESTSTEPS)).thenReturn(builder);
        when(builder.status(status)).thenReturn(builder);
        when(builder.estimated(LOGGED)).thenReturn(builder);
        when(builder.priority(priority)).thenReturn(builder);
        when(builder.type(taskType)).thenReturn(builder);


        System.err.println("0!!!!" + userService.hashCode());
    }
}
