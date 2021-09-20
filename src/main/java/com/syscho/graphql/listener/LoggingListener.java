package com.syscho.graphql.listener;

import graphql.kickstart.servlet.core.GraphQLServletListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingListener implements GraphQLServletListener {

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
        return new RequestCallback() {
            @Override
            public void onSuccess(HttpServletRequest request, HttpServletResponse response) {
                // no-op
                log.info("request completed successfully :URI {} ", request.getRequestURI());
            }

            @Override
            public void onError(HttpServletRequest request, HttpServletResponse response,
                                Throwable throwable) {
                log.error("Caught exception in listener.:URI {} error : {} ", request.getRequestURI(), throwable);
            }

            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                // This callback will be called post graphql lifecycle.
                MDC.clear();
            }
        };
    }

}