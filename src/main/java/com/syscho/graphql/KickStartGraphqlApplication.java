package com.syscho.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
        "com.syscho.graphql.company.repository",
        "com.syscho.graphql.book"
})
@RequiredArgsConstructor
public class KickStartGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(KickStartGraphqlApplication.class, args);
    }

}
