package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class DirectoryListingResponseTest extends TestCase {

    public void testEnsureDirectoryListingTextMatchesFiles() {
        String path = "MyDirectory";
        List<String> listing = new ArrayList<String>(Arrays.asList("test.html", "ihniwid.jpg"));
        DirectoryListingResponse response = new DirectoryListingResponse(new Request("GET / HTTP/1.1"));
        response.setRootPath("./src/test/resources");
        String responseText = response.body();
        assertTrue(responseText.contains("/"));
        assertTrue(responseText.contains(listing.get(0)));
        assertTrue(responseText.contains(listing.get(1)));
    }
}
