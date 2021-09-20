package com.syscho.graphql.book;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.syscho.graphql.KickStartGraphqlApplication;
import io.micrometer.core.instrument.util.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = KickStartGraphqlApplication.class)
public class BookQueryResolverIT {

    private static final String GRAPHQL_QUERY_REQUEST_PATH = "request/%s.graphql";
    private static final String GRAPHQL_QUERY_RESPONSE_PATH = "response/%s.json";

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void booksTest() throws IOException, JSONException {
        String queryFileName = "books";
        var graphQLResponse = graphQLTestTemplate
                .postForResource(format(GRAPHQL_QUERY_REQUEST_PATH, queryFileName));

        String expectedResponseBody = read(format(GRAPHQL_QUERY_RESPONSE_PATH, queryFileName));

        assertThat(graphQLResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(expectedResponseBody, graphQLResponse.getRawResponse().getBody(), false);
    }

    private String read(String location) throws IOException {
        return IOUtils.toString(
                new ClassPathResource(location).getInputStream(), StandardCharsets.UTF_8);
    }

}