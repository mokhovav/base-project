package com.mokhovav.base.annotations;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TestConditionExecution implements ExecutionCondition {

    private String makeEnvironment(ExtensionContext context, String property) {
        String prefix = context.getTestClass()
                .map(cl -> cl.getAnnotation(TestConditionPrefix.class))
                .map(TestConditionPrefix::prefix)
                .map(pref -> !pref.isEmpty() && !pref.endsWith(".") ? pref + "." : "")
                .orElse("");
        return prefix + property;
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Environment env = SpringExtension.getApplicationContext(context).getEnvironment();
        return context.getElement()
                .map(e -> e.getAnnotation(TestCondition.class))
                .map(TestCondition::property)
                .map(property -> makeEnvironment(context, property))
                .map(environment -> env.getProperty(environment, Boolean.class))
                .map(environment -> {
                    if (environment)
                        return ConditionEvaluationResult.enabled("Enabled by property");
                    else
                        return ConditionEvaluationResult.disabled("Disabled by property");
                }).orElse(
                        ConditionEvaluationResult.enabled("Enabled by default")
                );
    }
}
