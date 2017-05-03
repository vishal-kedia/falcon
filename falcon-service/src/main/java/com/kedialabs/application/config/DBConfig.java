package com.kedialabs.application.config;

import lombok.Data;

@Data
public class DBConfig {
    private String driverClass;
    private String user;
    private String password;
    private String url;
    private HibernateProperties hibernateProperties;
    private ConnectionPoolProperties connectionPoolProperties;
    
    @Data
    public static class HibernateProperties{
        private String dialect;
    }
    
    @Data
    public static class ConnectionPoolProperties{
        private Integer acquireIncrement;
        private Integer initialPoolSize;
        private Integer minPoolSize;
        private Integer maxPoolSize;
        private Integer maxIdleTime;
        private Integer maxStatements;
        private Integer idleConnectionTestPeriod;
    }
    
}
