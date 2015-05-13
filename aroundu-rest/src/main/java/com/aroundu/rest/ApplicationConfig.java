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
        
        //Features
        resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        
        
        //Providers
        resources.add(com.aroundu.rest.provider.JsonMoxyConfigurationContextResolver.class);
//        resources.add(com.aroundu.rest.filters.ResponseHeaderFilter.class);
        
        
        //OAuth2      
//        resources.add(com.aroundu.rest.security.oauth.OAuthSetupResource.class);
//        resources.add(com.aroundu.rest.security.oauth.OAuthResource.class);
//        resources.add(com.aroundu.rest.security.oauth.google.GoogleUserInfoResource.class);
//        resources.add(com.aroundu.rest.security.oauth.facebook.FacebookUserInfoResource.class);
        
        //Application Resource
        resources.add(com.aroundu.rest.resource.UserResource.class);
        resources.add(com.aroundu.rest.resource.EventsResource.class);
        resources.add(com.aroundu.rest.resource.ImageResource.class);
      
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