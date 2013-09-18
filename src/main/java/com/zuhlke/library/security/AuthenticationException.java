package com.zuhlke.library.security;

/**
 * Thrown if user authentication fails.
 * 
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -4196097265945547037L;

    public AuthenticationException() {
    }

    public AuthenticationException(final String message) {
        super(message);
    }
}
