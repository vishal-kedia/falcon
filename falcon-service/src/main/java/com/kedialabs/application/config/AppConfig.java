package com.kedialabs.application.config;

import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConfig extends Configuration {
    private DBConfig dbConfig;
}
