package com.zuhlke.library.user;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.zuhlke.library.domain.User;

@Component
@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;
    
    @POST @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(User user) {
        userService.createUser(user);
    }
    
}

