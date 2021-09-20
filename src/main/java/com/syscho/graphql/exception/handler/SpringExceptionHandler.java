package com.syscho.graphql.exception.handler;

import graphql.GraphQLException;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import javax.validation.ConstraintViolationException;

@Component
@Slf4j
public class SpringExceptionHandler {

    @ExceptionHandler({GraphQLException.class, ConstraintViolationException.class})
    public ThrowableGraphQLError handle(GraphQLException graphQLException) {
        log.error("error GraphQLException handle : {} ", graphQLException);
        return new ThrowableGraphQLError(graphQLException);
    }

    @ExceptionHandler({WebClientRequestException.class})
    public ThrowableGraphQLError webClientError(WebClientRequestException graphQLException) {
        log.error("error webClientError webClientError : {} ", graphQLException);
        return new ThrowableGraphQLError(graphQLException, "Server is down try some other time");
    }

    @ExceptionHandler({DataAccessException.class})
    public ThrowableGraphQLError databaseException(DataAccessException graphQLException) {
        log.error("error databaseException databaseException : {} ", graphQLException);
        return new ThrowableGraphQLError(graphQLException, "Application is down");
    }

    @ExceptionHandler(RuntimeException.class)
    public ThrowableGraphQLError runtimeError(RuntimeException e) {
        log.error("error databaseException runtimeError : {} ", e);
        return new ThrowableGraphQLError(e, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }


}
