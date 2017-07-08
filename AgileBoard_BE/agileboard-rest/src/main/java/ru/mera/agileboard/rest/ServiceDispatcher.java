package ru.mera.agileboard.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import ru.mera.agileboard.service.CommentService;
import ru.mera.agileboard.service.LoggingService;
import ru.mera.agileboard.service.ProjectService;
import ru.mera.agileboard.service.TaskService;
import ru.mera.agileboard.service.UserService;
import ru.mera.agileboard.service.UserSessionService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by antfom on 12.02.2015.
 */
@Component(name = "RestServiceDispatcherComponent", immediate = true)
public class ServiceDispatcher {

    private ServiceTracker<Object, Object> httpTracker;
    private UserService userService;
    private TaskService taskService;
    private ProjectService projectService;
    private CommentService commentService;
    private LoggingService loggingService;
    private UserSessionService userSessionService;

    private volatile ServiceRegistration<ServiceDispatcher> registration;

    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTUserServiceRef")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTTasksServiceRef")
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTProjectsServiceRef")
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTCommentsServiceRef")
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTLoggingServiceRef")
    public void setLoggingService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTUserSessionRef")
    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @Activate
    public void start(BundleContext context) {
        System.err.println("Service Dispatcher starting");
        httpTracker = new ServiceTracker<Object, Object>(context, HttpService.class.getName(), null) {
            public Object addingService(ServiceReference reference) {
                ServiceReference ref = context.getServiceReference(HttpService.class.getName());
                HttpService service = (HttpService) context.getService(ref);
                // Register a Jersey container
                System.err.println("creating config");
                ServletContainer servlet = new ServletContainer(createConfig());//new ABServlet(config, userSessionService);
                try {
                    service.registerServlet("/agile", servlet, null, null);// new HttpContextImpl(userSessionService));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return service;
            }
            public void removedService(ServiceReference reference, Object service) {
                // HTTP service is no longer available, unregister our servlet...
                try {
                    ((HttpService) service).unregister("/agile");
                } catch (IllegalArgumentException exception) {
                    // Ignore; servlet registration probably failed earlier on...
                }
            }
        };
        httpTracker.open();
    }

    @Deactivate
    public void stop() {
        httpTracker.close();
    }

    public ResourceConfig createConfig() {

        JacksonJsonProvider json = new JacksonJsonProvider().
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).
                configure(SerializationFeature.INDENT_OUTPUT, true);
        ResourceConfig config = new ResourceConfig();
        config.packages("ru.mera.agileboard.rest.servlets");
        config.register(json);
        config.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(taskService).to(TaskService.class);
                bind(userService).to(UserService.class);
                bind(projectService).to(ProjectService.class);
                bind(commentService).to(CommentService.class);
                bind(loggingService).to(LoggingService.class);
                bind(userSessionService).to(UserSessionService.class);
            }
        });

        ErrorPageGenerator epg = new ErrorPageGenerator() {
            @Override
            public String generate(Request request, int status, String
                    reasonPhrase,
                                   String description,
                                   Throwable exception) {
                StringBuilder sb = new StringBuilder();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                exception.printStackTrace(ps);
                ps.close();
                sb.append(new String(baos.toByteArray()));
                System.out.println(sb.toString());
                return sb.toString();
            }
        };
        config.register(epg);
        return config;
    }
}