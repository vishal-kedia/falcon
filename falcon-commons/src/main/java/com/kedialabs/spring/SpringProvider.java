package com.kedialabs.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public enum SpringProvider {

    INSTANCE;

    private AnnotationConfigApplicationContext context;

    private SpringProvider() {
        context = new AnnotationConfigApplicationContext();
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }
}