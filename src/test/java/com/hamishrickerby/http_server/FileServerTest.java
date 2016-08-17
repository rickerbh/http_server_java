package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileServerTest extends TestCase {

    String rootDir = "./src/test/resources";

    public void testFileExists() {
        FileServer server = server();
        assertTrue(server.fileExists("ihniwid.jpg"));
        assertFalse(server.fileExists("no-file-here"));
    }

    private FileServer server() {
        return new FileServer(rootDir);
    }

    public void testDirectoryIsNotConsideredAFile() {
        FileServer server = server();
        assertFalse(server.fileExists("subdirectory"));
    }

    public void testFileContentsLoadSuccessfully() {
        FileServer server = server();

        String filename = "ihniwid.jpg";
        byte[] expectedContents = new byte[0];
        try {
            expectedContents = Files.readAllBytes(Paths.get(rootDir, filename));
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertTrue(Arrays.equals(expectedContents, server.get(filename)));
    }
}
