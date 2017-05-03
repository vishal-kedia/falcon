package com.kedialabs.application;

import java.util.EnumSet;

import javax.persistence.EntityManagerFactory;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

import org.activejpa.enhancer.ActiveJpaAgentLoader;
import org.activejpa.jpa.JPA;
import org.activejpa.utils.OpenSessionInViewFilter;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kedialabs.application.config.AppConfig;
import com.kedialabs.spring.SpringBeanInitializer;
import com.kedialabs.spring.SpringProvider;

import io.dropwizard.Application;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application<AppConfig>{
    
    @Override
    public void run(AppConfig config, Environment env) throws Exception {
        env.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SpringProvider.INSTANCE.getContext().getBeanFactory().registerSingleton("configuration", config);
        SpringProvider.INSTANCE.getContext().getBeanFactory().registerSingleton("environment", env);
        SpringProvider.INSTANCE.getContext().register(SpringBeanInitializer.class);
        SpringProvider.INSTANCE.getContext().refresh();
        
        env.lifecycle().manage(new Managed() {
            @Override
            public void stop() throws Exception {
                log.info("Closing JPA instance");
                JPA.instance.close();
                
                log.info("Destroying application context");
                SpringProvider.INSTANCE.getContext().destroy();
            }
            
            @Override
            public void start() throws Exception {
                log.info("Initializing the active jpa module");
                ActiveJpaAgentLoader.instance().loadAgent();
                
                log.info("Registering persistence context to ActiveJPA");
                JPA.instance.addPersistenceUnit("falcon", SpringProvider.INSTANCE.getContext().getBean(EntityManagerFactory.class), true);
            }
        });
        registerOSIVFilter(env);
    }
    private void registerOSIVFilter(Environment environment) {
        Filter openSessionInViewFilter = new OpenSessionInViewFilter();
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("OSIVFilter", openSessionInViewFilter);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter = environment.admin().addFilter("OSIVFilter", openSessionInViewFilter);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
    public static void main(String[] args) throws Exception {
        log.info("Starting App");
        new App().run("server", args[0]);
    }
}
