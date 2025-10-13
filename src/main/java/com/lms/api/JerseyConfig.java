package com.lms.api;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig{
    public JerseyConfig(){
        packages("com.lms.api");
        register(JacksonFeature.class);
    }

}
