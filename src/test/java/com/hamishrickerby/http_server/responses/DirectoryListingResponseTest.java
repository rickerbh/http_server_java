package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
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

    private String getDirectoryListingForPath(String location) {
        Request request = new RequestBuilder()
                .setPath(location)
                .toRequest();
        DirectoryListingResponse response = new DirectoryListingResponse(request, "./src/test/resources");
        return new String(response.body());
    }

    private String getDirectoryListing() {
        return getDirectoryListingForPath("/");
    }

    public void testEnsureDirectoryListingHasHTMLLinks() {
        String responseText = getDirectoryListing();
        assertTrue(responseText.contains("<a href=\"/" + listing.get(0) + "\">" + listing.get(0) + "</a>"));
        assertTrue(responseText.contains("<a href=\"/" + listing.get(1) + "\">" + listing.get(1) + "</a>"));
    }

    public void testEnsureSubdirectoryListingHasCorrectHTMLLinks() {
        String location = "/subdirectory";
        String responseText = getDirectoryListingForPath(location);
        assertTrue(responseText.contains("<a href=\"" + location + "/file.txt\">file.txt</a>"));

        location = "/subdirectory/";
        responseText = getDirectoryListingForPath(location);
        assertTrue(responseText.contains("<a href=\"" + location + "file.txt\">file.txt</a>"));
    }
}
