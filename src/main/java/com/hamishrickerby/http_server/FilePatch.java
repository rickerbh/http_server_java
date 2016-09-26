package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 26/09/2016.
 */
public class FilePatch {
    protected String path;
    protected String eTag;
    protected byte[] contents;

    public FilePatch(String path, String eTag, byte[] contents) {
        this.path = path;
        this.eTag = eTag;
        this.contents = contents;
    }

    public String getPath() {
        return path;
    }

    public String getETag() {
        return eTag;
    }

    public byte[] getContents() {
        return contents;
    }
}
