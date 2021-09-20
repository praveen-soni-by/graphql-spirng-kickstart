package com.syscho.graphql.exception.handler;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        log.info("CustomGraphQLErrorHandler : ");
        return errors.stream()
                .map(this::customError)
                .collect(Collectors.toList());
    }

    private GraphQLError customError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionWhileDataFetching = (ExceptionWhileDataFetching) error;
            if (exceptionWhileDataFetching instanceof GraphQLError) {
                return (GraphQLError) exceptionWhileDataFetching.getException();
            }
        }
        return error;
    }


}
