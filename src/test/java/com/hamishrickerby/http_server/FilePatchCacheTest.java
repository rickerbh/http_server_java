package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 26/09/2016.
 */
public class FilePatchCacheTest extends TestCase {

    public void testFilesCanBeStoredAndRetrieved() {
        FilePatchCache cache = new FilePatchCache();
        String path = "/path";
        FilePatch patch = new FilePatch(path, "etag", "Contents".getBytes());
        cache.store(patch);
        assertEquals(patch, cache.retrieve(path));
    }

    public void testNonExistentFilesGiveNull() {
        FilePatchCache cache = new FilePatchCache();
        assertNull(cache.retrieve("/no-exist"));
    }

    public void testFilesCanBeStoredAndTestedForExistence() {
        FilePatchCache cache = new FilePatchCache();
        String path = "/path";
        FilePatch patch = new FilePatch(path, "etag", "Contents".getBytes());
        cache.store(patch);
        assertTrue(cache.contains(path));
        assertFalse(cache.contains("/no-file-here"));
    }
}
