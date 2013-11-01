package com.zuhlke.library.book;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;
import com.zuhlke.library.domain.Book;
import com.zuhlke.library.domain.json.Views;

/**
 * REST Resource for /books
 *
 */
@Component
@Path("/books")
public class BookResource {

    final Logger logger = LoggerFactory.getLogger(BookResource.class);
    
    @Inject
    private BookService bookService;
    
    @GET 
    @Produces(MediaType.APPLICATION_JSON) @JsonView(Views.Book.class)
    public List<Book> getBooks(@QueryParam("q") String query) {
        return bookService.findBooks(query);
    }
    
    @GET @Path("/{id}") 
    @Produces(MediaType.APPLICATION_JSON) @JsonView(Views.Book.class)
    public Book getBook(@PathParam("id") Long id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return book;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveBook(Book book) {
        bookService.saveBook(book);
    }

    @DELETE @Path("/{id}") 
    public void deleteBook(@PathParam("id") Long id) {
        bookService.deleteBook(id);
    }

}

