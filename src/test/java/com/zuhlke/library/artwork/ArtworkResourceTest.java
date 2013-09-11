package com.zuhlke.library.artwork;

import static org.apache.commons.io.IOUtils.toByteArray;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

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
        Mockito.when(mockArtworkService.loadArtwork("filename")).thenReturn(artwork);
        Response response = artworkResource.getArtwork("filename");
        assertThat(response.getStatus(), is(200));
        assertThat((byte[]) response.getEntity(), is(artwork));
        
    }
    
    @Test
    public void shouldGetMissingArtwork() throws Exception {
        when(mockArtworkService.loadArtwork("filename")).thenThrow(new IOException());
        try {
            artworkResource.getArtwork("filename");
            fail();
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(404));
        }
        
    }
    
//    @Test
//    public void shouldSaveArtwork() throws Exception {
//        byte[] artwork = toByteArray(getClass().getResourceAsStream("/img/domain.jpg"));
//        when(mockArtworkService.saveArtwork(artwork)).thenReturn("123");
//        String uuid = artworkResource.saveArtwork(getClass().getResourceAsStream("/img/domain.jpg"), null);
//        verify(mockArtworkService).saveArtwork(artwork);
//        assertThat(uuid, is("123"));
//    }
    
    
}
