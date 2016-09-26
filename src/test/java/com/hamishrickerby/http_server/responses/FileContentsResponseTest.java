package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FilePatch;
import com.hamishrickerby.http_server.FilePatchCache;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import junit.framework.TestCase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileContentsResponseTest extends TestCase {
    String rootDir = "./src/test/resources";

    public void testFileContentsRetrieved() {
        FileContentsResponse response = new FileContentsResponse(new Request("GET /subdirectory/file.txt HTTP/1.1"), rootDir, null);
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

    public void testFileContentsRetrievedBypassingCache() {
        FilePatchCache cache = new FilePatchCache();
        FileContentsResponse response = new FileContentsResponse(new Request("GET /subdirectory/file.txt HTTP/1.1"), rootDir, cache);
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

    public void testFileExists() {
        assertTrue(FileContentsResponse.fileExists(rootDir, "/subdirectory/file.txt", null));
        assertFalse(FileContentsResponse.fileExists(rootDir, "/no-exist", null));
    }

    public void testPatchedFileRetrieval() {
        FilePatchCache cache = new FilePatchCache();
        String path = "/subdirectory/file.txt";
        byte[] contents = "patched contents".getBytes();
        cache.store(new FilePatch(path, "etag", contents));
        FileContentsResponse response = new FileContentsResponse(new Request("GET " + path + " HTTP/1.1"), rootDir, cache);
        assertEquals(contents, response.body());
    }

    public void testPatchRequestStorageAndRetrieval() {
        FilePatchCache cache = new FilePatchCache();
        String path = "/subdirectory/file.txt";
        String contents = "patched contents";
        String requestText = new StringBuilder()
                .append(Method.PATCH.name())
                .append(" ")
                .append(path)
                .append(" HTTP/1.1")
                .append("\r\n")
                .append("ETag: \"1234\"")
                .append("\r\n\r\n")
                .append(contents)
                .append("\r\n")
                .toString();
        FileContentsResponse response = new FileContentsResponse(new Request(requestText), rootDir, cache);
        assertEquals("No Content", response.reason());
        assertResponseCodeEquals("204", response);
        assertResponseReasonEquals("No Content", response);

        response = new FileContentsResponse(new Request(Method.GET.name() + " " + path + " HTTP/1.1"), rootDir, cache);
        assertEquals(contents, new String(response.body()));
        assertResponseCodeEquals("200", response);
        assertResponseReasonEquals("OK", response);
    }

    public void testCacheCheckForFile() {
        FilePatchCache cache = new FilePatchCache();
        String path = "/subdirectory/file.txt";
        byte[] contents = "patched contents".getBytes();
        cache.store(new FilePatch(path, "etag", contents));
        assertTrue(FileContentsResponse.fileExists(rootDir, path, cache));
    }

}
