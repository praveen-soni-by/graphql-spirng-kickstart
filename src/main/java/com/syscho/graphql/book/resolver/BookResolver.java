package com.syscho.graphql.book.resolver;

import com.syscho.graphql.exception.NoDataFoundException;
import com.syscho.graphql.generated.resolver.BookQueryResolver;
import com.syscho.graphql.generated.types.Book;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class BookResolver implements BookQueryResolver {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CompletableFuture<List<Book>> books(DataFetchingEnvironment environment) {
        return CompletableFuture.supplyAsync(() ->
        {
            String query = "SELECT " + extractSelectedFields(environment) + " FROM BOOK ";
            log.info("query : {}", query);
            List<Book> books = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Book.class));
            return books;
        });
    }

    @Override
    public CompletableFuture<Book> bookById(String id, DataFetchingEnvironment environment) {
        return CompletableFuture.supplyAsync(() ->
        {
            String query = prepareQueryById(environment);
            log.info("query : {}", query);
            try {
                return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Book.class), new Object[]{id});

            } catch (EmptyResultDataAccessException exception) {
                throw new NoDataFoundException(id);
            }
        });
    }


    private String prepareQueryById(DataFetchingEnvironment environment) {
        return "SELECT " + extractSelectedFields(environment) + " FROM BOOK WHERE ID = ? ";
    }

    private String extractSelectedFields(DataFetchingEnvironment env) {
        return env.getSelectionSet().getImmediateFields().stream().map(selectedField ->
                selectedField.getName()).collect(Collectors.joining(","));
    }
}
