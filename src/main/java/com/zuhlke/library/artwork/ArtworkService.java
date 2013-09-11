package com.zuhlke.library.artwork;

import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArtworkService {

    @Value("${java.io.tmpdir}")
    private String imgStore;

    public String saveArtwork(byte[] artwork) throws IOException {
        String uuid = UUID.randomUUID().toString();
        FileUtils.writeByteArrayToFile(new File(imgStore, uuid), artwork);
        return uuid;
    }
    
    public byte[] loadArtwork(String uuid) throws IOException {
        return readFileToByteArray(new File(imgStore, uuid));
    }
    
}

