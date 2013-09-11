package com.zuhlke.library.domain;

import com.zuhlke.library.domain.Book;

public class BookBuilder {

    private Long id = null;
    private String title = null;
    private String author = null;
    private String description = null;
    
    public BookBuilder() {
    }
    
    public BookBuilder id(long id) {
        this.id = id;
        return this;
    }
    
    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }
    
    public BookBuilder description(String description) {
        this.description = description;
        return this;
    }
    
    public BookBuilder author(String author) {
        this.author = author;
        return this;
    }
    
    public Book build() {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        return book;
    }
    
}
