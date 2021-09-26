package com.syscho.graphql.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.ExecutionStrategyInstrumentationContext;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecuteOperationParameters;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionStrategyParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters;
import graphql.execution.instrumentation.parameters.InstrumentationFieldParameters;
import graphql.execution.instrumentation.parameters.InstrumentationValidationParameters;
import graphql.language.Document;
import graphql.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
//@Component
public class RequestLogging extends SimpleInstrumentation {

    private final String CORRELATION_ID = "correlation_id";

    public RequestLogging() {
    }

    @Override
    public InstrumentationContext<Document> beginParse(InstrumentationExecutionParameters parameters) {
        log.info("RequestLogging.beginParse");
        return super.beginParse(parameters);
    }

    @Override
    public InstrumentationContext<List<ValidationError>> beginValidation(InstrumentationValidationParameters parameters) {
        log.info("RequestLogging.beginValidation");
        return super.beginValidation(parameters);
    }

    @Override
    public ExecutionStrategyInstrumentationContext beginExecutionStrategy(InstrumentationExecutionStrategyParameters parameters) {
        log.info("RequestLogging.beginExecutionStrategy");
        return super.beginExecutionStrategy(parameters);
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecuteOperation(InstrumentationExecuteOperationParameters parameters) {
        log.info("RequestLogging.beginExecuteOperation");
        return super.beginExecuteOperation(parameters);
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginField(InstrumentationFieldParameters parameters) {
        log.info("RequestLogging.beginField");
        return super.beginField(parameters);
    }

    @Override
    public InstrumentationContext<Object> beginFieldFetch(InstrumentationFieldFetchParameters parameters) {
        log.info("RequestLogging.beginFieldFetch");
        return super.beginFieldFetch(parameters);
    }

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        log.info("RequestLogging.beginExecution");
        MDC.put(CORRELATION_ID, parameters.getExecutionInput().getExecutionId().toString());
        return SimpleInstrumentationContext.whenCompleted((executionResult, throwable) -> MDC.clear());
    }
}
