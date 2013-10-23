package com.zuhlke.library.artwork;

import static org.apache.commons.io.IOUtils.toByteArray;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArtworkResourceTest {

    @Mock ArtworkService mockArtworkService;
    @InjectMocks ArtworkResource artworkResource = new ArtworkResource();
    
    @Test
    public void shouldGetArtwork() throws Exception {
        byte[] artwork = toByteArray(getClass().getResourceAsStream("/img/domain.jpg"));
        ArtworkAdapter artworkAdapter = new ArtworkAdapter(artwork, new Date(), "image/jpeg");
        Mockito.when(mockArtworkService.loadArtwork("filename")).thenReturn(artworkAdapter);
        Response response = artworkResource.getArtwork("filename", new Date(0L));
        assertThat(response.getStatus(), is(200));
        assertThat((byte[]) response.getEntity(), is(artwork));
        
    }
    
    @Test
    public void shouldGetNotModified() throws Exception {
        ArtworkAdapter artworkAdapter = new ArtworkAdapter(new byte[] {}, new Date(0L), "image/jpeg");
        Mockito.when(mockArtworkService.loadArtwork("filename")).thenReturn(artworkAdapter);
        Response response = artworkResource.getArtwork("filename", new Date(0L));
        assertThat(response.getStatus(), is(304));
        
    }
    
    @Test
    public void shouldGetMissingArtwork() throws Exception {
        when(mockArtworkService.loadArtwork("filename")).thenThrow(new IOException());
        try {
            artworkResource.getArtwork("filename", new Date(0L));
            fail();
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(404));
        }
        
    }
    
    @Test
    public void shouldSaveArtwork() throws Exception {
        byte[] artwork = toByteArray(getClass().getResourceAsStream("/img/domain.jpg"));

        Attachment attachment = Mockito.mock(Attachment.class);
        DataHandler dataHandler = Mockito.mock(DataHandler.class);
        when(attachment.getDataHandler()).thenReturn(dataHandler);
        when(dataHandler.getInputStream()).thenReturn(getClass().getResourceAsStream("/img/domain.jpg"));

        when(mockArtworkService.saveArtwork(artwork)).thenReturn("123");
        
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        
        String uuid = artworkResource.upload(attachments);
        
        verify(mockArtworkService).saveArtwork(artwork);
        assertThat(uuid, is("123"));

        
    }
    
    
}
