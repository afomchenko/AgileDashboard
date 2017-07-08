package ru.mera.agileboard;

import org.osgi.service.http.HttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Created by antfom on 18.03.2015.
 */
public class HttpContextImpl implements HttpContext {
    @Override
    public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return true;
    }

    @Override
    public URL getResource(String name) {
        ClassLoader cl = this.getClass().getClassLoader();
        return cl.getResource(name);
    }

    @Override
    public String getMimeType(String name) {
        return null;
    }
}
