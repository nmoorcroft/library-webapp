package com.zuhlke.library.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Objects;
import com.zuhlke.library.domain.json.Views;

@Entity @Table(name = "book") @Cacheable
public class Book implements Serializable {

    private static final long serialVersionUID = 5541659969794633170L;

    @Id 
    @GeneratedValue(generator = "book_id", strategy = GenerationType.TABLE)
    @TableGenerator(name = "book_id", pkColumnValue = "book")
    @Column(name = "book_id")
    private Long id;
    
    @Column(name = "title")
    @Length(max = 500) @NotNull @NotEmpty
    private String title;
    
    @Column(name = "description")
    @Length(max = 2000)
    private String description;

    @Column(name = "author")
    @Length(max = 255) @NotNull @NotEmpty
    private String author;
    
    @Column(name = "artwork")
    @Length(max = 255)
    private String artwork;
    
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("createdDate")
    @JsonView(Views.WithComments.class)
    private List<Comment> comments = new ArrayList<>();
    
    Book() { }
    
    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getArtwork() {
        return artwork;
    }
    
    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }
    
    public List<Comment> getComments() {
        return comments;
    }
    
    protected void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).toString();
    }
    

}

