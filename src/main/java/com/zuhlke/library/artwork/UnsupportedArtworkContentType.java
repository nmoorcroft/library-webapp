package com.zuhlke.library.artwork;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UnsupportedArtworkContentType extends WebApplicationException {
    
    private static final long serialVersionUID = -1299049019527899112L;

    public UnsupportedArtworkContentType() {
        super(Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity("Unsupported artwork media type").build());
    }

}
