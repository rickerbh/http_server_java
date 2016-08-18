package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileContentsResponseTest extends TestCase {

    public void testFileContentsRetrieved() {
        String rootDir = "./src/test/resources";
        FileContentsResponse response = new FileContentsResponse(new Request("GET /subdirectory/file.txt HTTP/1.1"), rootDir);
        byte[] contents = response.body();

        String filename = "subdirectory/file.txt";
        byte[] expectedContents = new byte[0];
        try {
            expectedContents = Files.readAllBytes(Paths.get(rootDir, filename));
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertTrue(Arrays.equals(expectedContents, contents));
    }
}
