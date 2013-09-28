package com.zuhlke.library.artwork;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Path("/artwork")
public class ArtworkResource {
    
    final Logger logger = LoggerFactory.getLogger(ArtworkResource.class);
    
    @Inject
    private ArtworkService artworkService;

    @GET @Path("/{filename}") 
    @Produces("image/jpg") // todo : add support for other image types
    public Response getArtwork(@PathParam("filename") String filename, @HeaderParam("If-Modified-Since") Date ims) {
        try {
        	ArtworkAdapter artwork = artworkService.loadArtwork(filename);
        	long lastModified = artwork.getLastModified().getTime() / 1000 * 1000; 
        	if (ims != null && lastModified <= ims.getTime()) {
        		return Response.notModified().build();
        	}
    		return Response.ok(artwork.getData()).lastModified(artwork.getLastModified()).build();
        	
        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
    
    @POST @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String upload(final List<Attachment> attachments) throws Exception {
    	Assert.notEmpty(attachments);
        DataHandler handler = attachments.get(0).getDataHandler();
        return artworkService.saveArtwork(IOUtils.toByteArray(handler.getInputStream()));
    }
    
}

