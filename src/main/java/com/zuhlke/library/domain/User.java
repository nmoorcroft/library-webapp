package com.zuhlke.library.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Objects;
import com.zuhlke.library.domain.json.Views;

@Entity @Table(name = "library_user") @Cacheable
public class User implements Serializable {

    private static final long serialVersionUID = 594893770324699816L;

    @Id 
    @GeneratedValue(generator = "user_id", strategy = GenerationType.TABLE)
    @TableGenerator(name = "user_id", pkColumnValue = "user")
    @Column(name = "user_id")    
    private Long id;
    
    @Column(name = "email")
    @Length(max = 100) @NotNull @NotEmpty
    private String email;
    
    @Column(name = "password")
    @Length(max = 100) @NotNull @NotEmpty
    @JsonView(Views.Internal.class)
    private String password;
    
    @Column(name = "fullname")
    @Length(max = 250) @NotNull @NotEmpty
    private String name;
    
    @Column(name = "role") 
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    User() { }
    
    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this).toString();
    }
    
}
