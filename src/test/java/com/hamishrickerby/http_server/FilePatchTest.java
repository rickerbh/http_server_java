package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 26/09/2016.
 */
public class FilePatchTest extends TestCase {
    public void testFilePatchStoresAndRetrievesContents() {
        String path = "/filepath.txt";
        String etag = "etag";
        byte[] contents = "File contents".getBytes();
        FilePatch patch = new FilePatch(path, etag, contents);
        assertEquals(path, patch.getPath());
        assertEquals(etag, patch.getETag());
        assertEquals(contents, patch.getContents());
    }
}
