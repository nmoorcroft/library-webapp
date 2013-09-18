package com.zuhlke.library.artwork;

import static org.apache.commons.io.FileUtils.deleteQuietly;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ArtworkServiceTest {

    @InjectMocks ArtworkService artworkService = new ArtworkService();
    
    @Test
    public void shouldSaveAndLoadArtwork() throws Exception {
    	artworkService.imgStore = "target";
    	
        byte[] artwork = toByteArray(getClass().getResourceAsStream("/img/extreme.jpg"));
        String uuid = artworkService.saveArtwork(artwork);
        byte[] loaded = artworkService.loadArtwork(uuid).getData();
        
        assertArrayEquals(artwork, loaded);
        
        deleteQuietly(new File("target/"+uuid));
        
    }
}
