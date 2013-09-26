package com.zuhlke.library.user;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DuplicateEmailException extends WebApplicationException {

    private static final long serialVersionUID = 1138636058845011872L;

    public DuplicateEmailException() {
        super(Response.status(Response.Status.CONFLICT).entity("Duplicate Email").build());
    }
    
}

