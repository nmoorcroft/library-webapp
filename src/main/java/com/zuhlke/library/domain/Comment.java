package com.zuhlke.library.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;
    
@Entity @Table(name = "comment") @Cacheable
public class Comment implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -405137319657522846L;
    
    @Id 
    @GeneratedValue(generator = "comment_id", strategy = GenerationType.TABLE)
    @TableGenerator(name = "comment_id", pkColumnValue = "comment")
    @Column(name = "comment_id")
    private Long id;
    
    @Column(name = "comment")
    @NotNull @NotEmpty
    @Length(max = 2000)
    private String comment;
    
    @Column(name = "created_date", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @NotNull
    private Book book;
    
    Comment() { }
    
    public Comment(String comment, User user, Book book) {
        this.comment = comment;
        this.user = user;
        this.book = book;
    }

    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    protected void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).toString();
    }
    
    

}
