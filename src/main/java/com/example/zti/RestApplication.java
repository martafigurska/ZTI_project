package com.example.zti;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(com.example.zti.resource.RecipeEndpoint.class);
        classes.add(com.example.zti.resource.AuthResource.class); // ← to było brakujące!
        return classes;
    }
}
