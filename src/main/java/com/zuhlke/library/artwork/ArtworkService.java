package com.zuhlke.library.artwork;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArtworkService {

    private final Logger logger = LoggerFactory.getLogger(ArtworkService.class);
    
    @Value("${img.store}")
    protected String imgStore;

    public String saveArtwork(byte[] artwork) throws IOException {
        String uuid = UUID.randomUUID().toString();
        File file = new File(imgStore, uuid);
        logger.debug("writing artwork to file {}", file);
        FileUtils.writeByteArrayToFile(file, artwork);
        return uuid;
    }
    
    public byte[] loadArtwork(String uuid) throws IOException {
        return readFileToByteArray(new File(imgStore, uuid));
    }
    
}

