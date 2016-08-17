package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class DirectoryListingResponseTest extends TestCase {
    List<String> listing = new ArrayList<String>(Arrays.asList("test.html", "ihniwid.jpg"));

    public void testEnsureDirectoryListingTextMatchesFiles() {
        String responseText = getDirectoryListing();
        assertTrue(responseText.contains("/"));
        assertTrue(responseText.contains(listing.get(0)));
        assertTrue(responseText.contains(listing.get(1)));
    }

    private String getDirectoryListing() {
        DirectoryListingResponse response = new DirectoryListingResponse(new Request("GET / HTTP/1.1"));
        response.setRootPath("./src/test/resources");
        return new String(response.body());
    }

    public void testEnsureDirectoryListingHasHTMLLinks() {
        String responseText = getDirectoryListing();
        assertTrue(responseText.contains("<a href=\"/" + listing.get(0) + "\">" + listing.get(0) + "</a>"));
        assertTrue(responseText.contains("<a href=\"/" + listing.get(1) + "\">" + listing.get(1) + "</a>"));
    }
}
