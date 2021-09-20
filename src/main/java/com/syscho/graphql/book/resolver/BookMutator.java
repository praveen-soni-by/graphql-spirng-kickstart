package com.syscho.graphql.book.resolver;

import com.syscho.graphql.book.BookDO;
import com.syscho.graphql.book.BookRepository;
import com.syscho.graphql.generated.types.Book;
import graphql.kickstart.tools.GraphQLMutationResolver;
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
public class BookMutator implements GraphQLMutationResolver {

    private final BookRepository bookRepository;
    private final JdbcTemplate jdbcTemplate;

    public Book addBook(Book bookVO) {
        BookDO bookDO = new BookDO();
        bookVO.setId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(bookVO, bookDO);
        bookRepository.save(bookDO);
        return bookVO;
    }

    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM  BOOK WHERE ID = ? ", id);
    }

    public Book updateBook(Book bookVO, DataFetchingEnvironment environment) {
        StringBuilder updateFields = new StringBuilder();
        Map<String, String> result = environment.getArgument("request");
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
