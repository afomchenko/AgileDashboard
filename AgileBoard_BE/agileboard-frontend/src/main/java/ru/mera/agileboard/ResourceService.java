package ru.mera.agileboard;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Created by antfom on 18.03.2015.
 */
@Component(immediate = true)
public class ResourceService {
    private ServiceTracker httpTracker;

    @Activate
    public void start(BundleContext context) {
        System.err.println("Service Dispatcher starting");

        httpTracker = new ServiceTracker(context, HttpService.class.getName(), null) {
            public void removedService(ServiceReference reference, Object service) {
                // HTTP service is no longer available, unregister our servlet...
                try {
                    ((HttpService) service).unregister("/agileboard");
                } catch (IllegalArgumentException exception) {
                    // Ignore; servlet registration probably failed earlier on...
                }
            }

            public Object addingService(ServiceReference reference) {
                ServiceReference ref = context.getServiceReference(HttpService.class.getName());
                HttpService service = (HttpService) context.getService(ref);

                // Register a Jersey container
                System.err.println("creating conf service");

                try {
                    service.registerResources("/agileboard","frontend",new HttpContextImpl());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return service;
            }
        };

        httpTracker.open();
        System.err.println("service started");
//
//
//        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createConfig());

    }

    @Deactivate
    public void stop() {
        httpTracker.close();
//        server.shutdown();
    }
}
