package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FilePatch;
import com.hamishrickerby.http_server.FilePatchCache;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
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
        Request request = new RequestBuilder()
                .setPath("/subdirectory/file.txt")
                .toRequest();
        FileContentsResponse response = new FileContentsResponse(request, rootDir, null);
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
        Request request = new RequestBuilder()
                .setPath("/subdirectory/file.txt")
                .toRequest();
        FileContentsResponse response = new FileContentsResponse(request, rootDir, cache);
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

        Request request = new RequestBuilder()
                .setPath(path)
                .toRequest();
        FileContentsResponse response = new FileContentsResponse(request, rootDir, cache);
        assertEquals(contents, response.body());
    }

    public void testPatchRequestStorageAndRetrieval() {
        FilePatchCache cache = new FilePatchCache();
        String path = "/subdirectory/file.txt";
        String contents = "patched contents";

        Request request = new RequestBuilder()
                .setMethod(Method.PATCH)
                .setPath(path)
                .addHeader("ETag: \"1234\"")
                .setContent(contents)
                .toRequest();

        FileContentsResponse response = new FileContentsResponse(request, rootDir, cache);
        assertEquals("No Content", response.reason());
        assertResponseCodeEquals("204", response);
        assertResponseReasonEquals("No Content", response);

        request = new RequestBuilder()
                .setPath(path)
                .toRequest();

        response = new FileContentsResponse(request, rootDir, cache);
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
