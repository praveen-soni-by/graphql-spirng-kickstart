package com.syscho.graphql.book.resolver;

import com.syscho.graphql.book.BookDO;
import com.syscho.graphql.book.BookRepository;
import com.syscho.graphql.generated.resolver.BookMutationResolver;
import com.syscho.graphql.generated.types.Book;
import com.syscho.graphql.generated.types.BookInput;
import com.syscho.graphql.generated.types.BookUpdateInput;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class BookMutator implements BookMutationResolver {

    private final BookRepository bookRepository;
    private final JdbcTemplate jdbcTemplate;

    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM  BOOK WHERE ID = ? ", id);
    }


    @Override
    public Book addBook(BookInput request, DataFetchingEnvironment env) throws Exception {
        BookDO bookDO = new BookDO();
        BeanUtils.copyProperties(request, bookDO);
        bookDO.setId(UUID.randomUUID().toString());
        bookRepository.save(bookDO);

        Book book = new Book();
        BeanUtils.copyProperties(book, bookDO);

        return book;
    }

    @Override
    public Book updateBook(BookUpdateInput request, DataFetchingEnvironment env) throws Exception {
        StringBuilder updateFields = new StringBuilder();
        Map<String, String> result = env.getArgument("request");
        result.forEach((key, value) -> {
            if (!key.equals("id"))
                updateFields.append(" " + key + " = '" + value + "',");
        });
        String updateQuery = "UPDATE BOOK SET  " + updateFields.substring(0, updateFields.toString().length() - 1) + " Where Id = ?";
        String id = result.get("id");
        log.info("Query :{} ", updateQuery);
        jdbcTemplate.update(updateQuery, id);
        return jdbcTemplate.queryForObject("select * from Book where id = ?", new BeanPropertyRowMapper<>(Book.class), id);
    }
}
