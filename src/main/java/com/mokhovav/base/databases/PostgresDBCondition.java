package com.mokhovav.base.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
public class PostgresDBCondition implements Condition {

    @Autowired
    private Environment env;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabledDBType = System.getProperty("project.config.base.SQLDBEnable", "false");
        return (enabledDBType != null && enabledDBType.equalsIgnoreCase("false"));
    }
}
