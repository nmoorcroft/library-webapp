package com.zuhlke.library.artwork;

import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArtworkService {

    private final Logger logger = LoggerFactory.getLogger(ArtworkService.class);
    
    private static final List<String> SUPPORTED_MEDIA_TYPES = asList(new String[] {
        "image/jpeg", "image/gif", "image/png"
    });

    @Value("${img.store}")
    protected String imgStore;
    
    @Inject
    protected Tika tika;
    
    public String saveArtwork(byte[] artwork) throws IOException {
        String contentType = tika.detect(artwork);
        if (!SUPPORTED_MEDIA_TYPES.contains(contentType)) {
            throw new UnsupportedArtworkContentType();
        }
        String uuid = UUID.randomUUID().toString();
        File file = new File(imgStore, uuid);
        logger.debug("writing artwork to file {}", file);
        FileUtils.writeByteArrayToFile(file, artwork);
        return uuid;
    }
    
    public ArtworkAdapter loadArtwork(String uuid) throws IOException {
        File file = new File(imgStore, uuid);
        return new ArtworkAdapter(readFileToByteArray(file), new Date(file.lastModified()), tika.detect(file));
    }
    
}

