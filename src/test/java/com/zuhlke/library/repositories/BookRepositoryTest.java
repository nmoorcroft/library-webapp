package com.zuhlke.library.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.zuhlke.library.domain.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/data-context.xml")
public class BookRepositoryTest {

    @Inject BookRepository bookRepository;
    @Inject TransactionTemplate transactionTemplate;
    
    @Test
    public void shouldBookFindById() throws Exception {
        Book book = transactionTemplate.execute(new TransactionCallback<Book>() {
            @Override
            public Book doInTransaction(TransactionStatus status) {
                return bookRepository.findOne(1L);
            }
        });
        
        assertNotNull(book);
        assertEquals(new Long(1), book.getId());
        
    }
    
    @Test
    public void shouldFindBookByTitle() throws Exception {
        List<Book> books = transactionTemplate.execute(new TransactionCallback<List<Book>>() {
            @Override
            public List<Book> doInTransaction(TransactionStatus status) {
                return bookRepository.findByTitleOrAuthor("java");
            }
        });

        assertEquals(1, books.size());
        assertEquals(new Long(3), books.get(0).getId());
        
    }
    
    @Test
    public void shouldFindBookByAuthor() throws Exception {
        List<Book> books = transactionTemplate.execute(new TransactionCallback<List<Book>>() {
            @Override
            public List<Book> doInTransaction(TransactionStatus status) {
                return bookRepository.findByTitleOrAuthor("king");
            }
        });
        
        assertEquals(1, books.size());
        assertEquals(new Long(3), books.get(0).getId());
        
    }
    
}
