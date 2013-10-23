package com.zuhlke.library.artwork;

import java.util.Date;

public class ArtworkAdapter {

    private byte[] data;
    private Date lastModified;
    private String contentType;
    
    public ArtworkAdapter(byte[] data, Date lastModified, String contentType) {
        this.data = data;
        this.lastModified = lastModified;
        this.contentType = contentType;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public Date getLastModified() {
        return lastModified;
    }
    
    public String getContentType() {
        return contentType;
    }
}
