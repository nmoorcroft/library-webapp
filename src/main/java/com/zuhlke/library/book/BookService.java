package com.zuhlke.library.book;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zuhlke.library.domain.Book;
import com.zuhlke.library.repositories.BookRepository;

/**
 * Manages books
 *
 */
@Service
public class BookService {

    @Inject
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Book getBook(long id) {
        return bookRepository.findOne(id);
    }
    
    @Transactional(readOnly = true)
    public List<Book> findBooks(String query) {
        if (isNullOrEmpty(query)) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findByTitleOrAuthor(query.toLowerCase());
        }
    }
    
    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
    
    @Transactional
    public void deleteBook(long id) {
        bookRepository.delete(bookRepository.findOne(id));
    }
   
}

