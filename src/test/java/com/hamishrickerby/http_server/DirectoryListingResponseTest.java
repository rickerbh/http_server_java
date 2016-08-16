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
        List<String> listing = new ArrayList<String>(Arrays.asList("file1.txt", "file2.html"));
        DirectoryListingResponse response = new DirectoryListingResponse(path, listing);
        String responseText = response.toString();
        assertTrue(responseText.contains("MyDirectory"));
        assertTrue(responseText.contains(listing.get(0)));
        assertTrue(responseText.contains(listing.get(1)));
    }
}
