package com.syscho.graphql.jsonplaceholder.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
@Slf4j
public class ClientConfig {

    private final HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofMinutes(6));

    @Value("${jsonplaceholder.url}")
    private String baseurl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseurl)
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("response {} ", clientResponse);
            return Mono.just(clientResponse);
        });
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Url {}  method {} ", clientRequest.url(), clientRequest.method().name());
            return Mono.just(clientRequest);
        });
    }
}
