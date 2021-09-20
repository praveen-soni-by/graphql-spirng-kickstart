package com.syscho.graphql.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookDO, String> {

    Optional<BookDO> findById(String id);

}
