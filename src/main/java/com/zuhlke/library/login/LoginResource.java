package com.zuhlke.library.login;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;
import com.zuhlke.library.domain.User;
import com.zuhlke.library.domain.json.Views;
import com.zuhlke.library.security.AuthenticationException;
import com.zuhlke.library.security.SecurityService;

@Component
public class LoginResource {

    final Logger logger = LoggerFactory.getLogger(LoginResource.class);

    @Inject
    private SecurityService securityService;

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    public User authenticate(final LoginForm loginForm) {
        try {
            return securityService.authenticate(loginForm.getUsername(), loginForm.getPassword());
            
        } catch (AuthenticationException e) {
        	logger.warn(e.getMessage());
            securityService.logout();
            throw new WebApplicationException(HttpServletResponse.SC_UNAUTHORIZED);
        }
        
    }
    
    @GET
    @Path("/checklogin")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonView(Views.Public.class)
    public User checkLogin() throws Exception {
        return securityService.getCurrentUser().orNull();
    }
    
    @POST
    @Path("/logout")
    public void logout() {
    	securityService.logout();
    }
    
}

