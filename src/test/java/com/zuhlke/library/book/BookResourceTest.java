package com.zuhlke.library.book;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.zuhlke.library.domain.Book;
import com.zuhlke.library.domain.BookBuilder;

@RunWith(MockitoJUnitRunner.class)
public class BookResourceTest {

    final List<Book> books = Arrays.asList(
        new BookBuilder().withId(1L).withTitle("book1").build(),
        new BookBuilder().withId(2L).withTitle("book2").build(),
        new BookBuilder().withId(3L).withTitle("book3").build()
    );
    
    final Book book = new BookBuilder().withId(1L).withTitle("book title").build();

    @Mock BookService mockBookService;
    @InjectMocks BookResource resource = new BookResource();
    
    @Test
    public void shouldGetAllBooks() throws Exception {
        when(mockBookService.findBooks(anyString())).thenReturn(books);
        List<Book> result = resource.getBooks(null);
        assertThat(books, is(result));
    }
    
    @Test
    public void shouldGetBooksByQuery() throws Exception {
        when(mockBookService.findBooks("title")).thenReturn(books);
        List<Book> result = resource.getBooks("title");
        assertThat(books, is(result));
    }
    
    @Test
    public void shouldGetBookById() throws Exception {
        when(mockBookService.getBook(1L)).thenReturn(book);
        Book result = resource.getBook(1L);
        assertThat(book, is(result));
    }
    
    @Test
    public void shouldSaveBook() throws Exception {
        resource.saveBook(book);
        verify(mockBookService).saveBook(book);
    }
    
    @Test
    public void shouldDeleteBook() throws Exception {
        resource.deleteBook(1L);
        verify(mockBookService).deleteBook(1L);
    }
    

}

