package org.example;

import org.example.point.PointResource;
import org.example.users.UserResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PointResource.class);
        classes.add(CORSFilter.class);
        classes.add(UserResource.class);
        return classes;
    }
}
