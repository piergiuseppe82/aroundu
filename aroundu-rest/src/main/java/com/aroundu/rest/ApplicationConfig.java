package com.aroundu.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author piergiuseppe82
 *
 */
@ApplicationPath("/service")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        resources.add(com.aroundu.rest.filters.ResponseHeaderFilter.class);
        resources.add(com.aroundu.rest.provider.JsonMoxyConfigurationContextResolver.class);
        resources.add(com.aroundu.rest.resource.AuthResource.class);
        resources.add(com.aroundu.rest.resource.PrivateResource.class);
        resources.add(com.aroundu.rest.resource.SetupResource.class);
        resources.add(com.aroundu.rest.resource.UserResource.class);
        resources.add(com.aroundu.rest.resource.EventsResource.class);
      
        return resources;
    }
    
    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }
    
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.wadl.disableWadl", true);
        return properties;
    }    
}