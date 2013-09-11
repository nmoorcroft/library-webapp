package com.zuhlke.library.login;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zuhlke.library.domain.User;
import com.zuhlke.library.security.AuthenticationException;
import com.zuhlke.library.security.SecurityService;

/**
 * Responsible for accepting the User username and plaintext password and
 * tries to authenticate them.
 */
@Component
public class LoginResource {

    Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @Inject
    private SecurityService securityService;

    @POST
    @Path("/authenticate")
    @Consumes("application/json") @Produces("application/json")
    public User authenticate(final LoginForm user, @Context final HttpServletRequest request) {
        try {
            return securityService.authenticate(user.getUsername(), user.getPassword());
            
        } catch (AuthenticationException e) {
        	logger.warn(e.getMessage());
            securityService.logout();
            throw new WebApplicationException(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @GET
    @Path("/logout")
    public void logout() throws IOException {
        securityService.logout();
    }
    
}

