package com.zuhlke.library.security;

/**
 * Thrown on authentication failure
 * 
 */
public class AuthenticationException extends Exception {

    private static final long serialVersionUID = -4196097265945547037L;

    public AuthenticationException() {
    }

    public AuthenticationException(final String message) {
        super(message);
    }
}
