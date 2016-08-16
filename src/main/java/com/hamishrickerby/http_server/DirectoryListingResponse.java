package com.hamishrickerby.http_server;

import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class DirectoryListingResponse {
    String path;
    List<String> listing;

    public DirectoryListingResponse(String path, List<String> listing) {
        this.path = path;
        this.listing = listing;
    }

    public String toString() {
        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Directory listing for " + path + "\n\n");
        for (String filename : listing) {
            responseBuilder.append(filename + "\n");
        }
        return responseBuilder.toString();
    }
}
