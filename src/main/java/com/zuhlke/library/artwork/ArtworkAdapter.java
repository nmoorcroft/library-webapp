package com.zuhlke.library.artwork;

import java.util.Date;

public class ArtworkAdapter {

	private byte[] data;
	private Date lastModified;
	
	public ArtworkAdapter(byte[] data, Date lastModified) {
		this.data = data;
		this.lastModified = lastModified;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
}
