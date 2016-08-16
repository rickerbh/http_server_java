package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class FileDirectoryServerTest extends TestCase {
    public void testDirectoryListingReturnsFiles() {
        String rootDir = "./src/test/resources";
        FileDirectoryServer fs = new FileDirectoryServer(rootDir);
        List<String> files = fs.get("/");
        assertTrue(files.contains("ihniwid.jpg"));
        assertTrue(files.contains("test.html"));
    }

    public void testNonExistingDirectoryAtValidRootReturnsEmpty() {
        String rootDir = "./src/test/resources";
        FileDirectoryServer fs = new FileDirectoryServer(rootDir);
        List<String> files = fs.get("/i-do-not-exist/");
        assertEquals(new ArrayList<String>(), files);
    }

    public void testFileQueryReturnsEmpty() {
        String rootDir = "./src/test/resources";
        FileDirectoryServer fs = new FileDirectoryServer(rootDir);
        List<String> files = fs.get("/test.html");
        assertEquals(new ArrayList<String>(), files);
    }

}
