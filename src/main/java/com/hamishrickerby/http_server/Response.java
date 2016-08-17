package com.hamishrickerby.http_server;

import java.util.List;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class Response {
    Request request;

    public Response(Request request) {
        this.request = request;
    }

    public byte[] getBytes() {
        StringBuilder b = new StringBuilder();
        b.append(statusLine())
                .append(body());
        return b.toString().getBytes();
    }

    private String statusLine() {
        StringBuilder b = new StringBuilder();
        b.append(request.getVersion())
                .append(" ")
                .append("200")
                .append(" ")
                .append("OK")
                .append("\n\n");
        return b.toString();
    }

    private String body() {
        FileDirectoryServer fileDirectoryServer = new FileDirectoryServer("./src/test/resources");
        List<String> listing = fileDirectoryServer.get(request.getPath());
        DirectoryListingResponse dirResponse = new DirectoryListingResponse(request.getPath(), listing);
        return dirResponse.toString();
    }

}
