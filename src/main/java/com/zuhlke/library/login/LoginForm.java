package com.zuhlke.library.login;

public class LoginForm {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(final String password) {
        this.password = password;
    }

    public LoginForm withUsername(final String user) {
        this.username = user;
        return this;
    }

    public LoginForm withPassword(final String pass) {
        this.password = pass;
        return this;
    }
    
}
