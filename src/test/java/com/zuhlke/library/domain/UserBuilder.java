package com.zuhlke.library.domain;

import com.zuhlke.library.domain.User;
import com.zuhlke.library.domain.UserRole;

public class UserBuilder {

    private Long id = null;
    private String email = null;
    private String password = null;
    private UserRole role = null;
    private String name = null;
    
    public UserBuilder() {
    }
    
    public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }
    
    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    
    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
    
    public UserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }
    
    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public User build() {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setName(name);
        return user;
    }
    
}
