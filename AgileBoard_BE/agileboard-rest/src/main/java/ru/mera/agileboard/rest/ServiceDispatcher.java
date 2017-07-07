package ru.mera.agileboard.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.osgi.service.component.annotations.*;
import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import ru.mera.agileboard.service.*;

import javax.ws.rs.core.UriBuilder;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;

/**
 * Created by antfom on 12.02.2015.
 */
@Component(name = "RestServiceDispatcherComponent", immediate = true)
public class ServiceDispatcher {

    public static final URI BASE_URI = getBaseURI();
    private ServiceTracker httpTracker;
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTUserServiceRef")
    private UserService userService;
    //    private HttpServer server;
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTTasksServiceRef")
    private TaskService taskService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTProjectsServiceRef")
    private ProjectService projectService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTCommentsServiceRef")
    private CommentService commentService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTLoggingServiceRef")
    private LoggingService loggingService;
    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, name = "BTUserSessionRef")
    private UserSessionService userSessionService;

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost").port(8080).path("/agile").build();
    }

    @Activate
    public void start(BundleContext context) {
        System.err.println("Service Dispatcher starting");

        httpTracker = new ServiceTracker(context, HttpService.class.getName(), null) {
            public void removedService(ServiceReference reference, Object service) {
                // HTTP service is no longer available, unregister our servlet...
                try {
                    ((HttpService) service).unregister("/agile");
                } catch (IllegalArgumentException exception) {
                    // Ignore; servlet registration probably failed earlier on...
                }
            }

            public Object addingService(ServiceReference reference) {
                ServiceReference ref = context.getServiceReference(HttpService.class.getName());
                HttpService service = (HttpService) context.getService(ref);

                // Register a Jersey container
                System.err.println("crating config");

                ServletContainer servlet = new ServletContainer(createConfig());//new ABServlet(config, userSessionService);


                try {
                    service.registerServlet("/agile", servlet, null, null);// new HttpContextImpl(userSessionService));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return service;
            }
        };

        httpTracker.open();
//
//
//        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createConfig());

    }

    @Deactivate
    public void stop() {
        httpTracker.close();
//        server.shutdown();
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



