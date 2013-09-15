package com.zuhlke.library.book;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.zuhlke.library.domain.Book;
import com.zuhlke.library.domain.BookBuilder;
import com.zuhlke.library.repositories.BookRepository;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock BookRepository mockRepository;
    @InjectMocks BookService bookService = new BookService();
    
    @Test
    public void shouldGetBookById() throws Exception {
        bookService.getBook(1L);
        verify(mockRepository).findOne(1L);
    }
    
    @Test
    public void shouldFindAllBooks() throws Exception {
        bookService.findBooks(null);
        verify(mockRepository).findAll();
    }
    
    @Test
    public void shouldFindBooksByQuery() throws Exception {
        bookService.findBooks("Query");
        verify(mockRepository).findByTitleOrAuthor("query");
    }
    
    @Test
    public void shouldSaveBook() throws Exception {
        Book book = new BookBuilder().withId(1L).build();
        bookService.saveBook(book);
        verify(mockRepository).save(book);
    }
    
    @Test
    public void shouldDeleteBook() throws Exception {
        Book book = new BookBuilder().withId(2L).build();
        when(mockRepository.findOne(2L)).thenReturn(book);
        bookService.deleteBook(2L);
        verify(mockRepository).delete(book);
    }
    
}
