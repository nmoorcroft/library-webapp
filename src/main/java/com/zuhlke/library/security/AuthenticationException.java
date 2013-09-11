package com.zuhlke.library.security;

/**
 * Thrown if the authentication of the user failed.
 * <p>
 * This might be happening if the user supplied the wrong password or username.
 */
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -4196097265945547037L;

    public AuthenticationException() {
    }

    public AuthenticationException(final String message) {
        super(message);
    }
}
