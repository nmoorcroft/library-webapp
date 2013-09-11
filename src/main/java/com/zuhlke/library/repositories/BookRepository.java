package com.zuhlke.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zuhlke.library.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where lower(b.title) like %?1% or lower(b.author) like %?1%")
    List<Book> findByTitleOrAuthor(String query);
    
}

