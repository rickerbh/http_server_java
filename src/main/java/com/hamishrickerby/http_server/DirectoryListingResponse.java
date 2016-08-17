package com.hamishrickerby.http_server;

import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class DirectoryListingResponse extends Response {
    String rootPath;

    public DirectoryListingResponse(Request request) {
        super(request);
    }

    public void setRootPath(String path) {
        this.rootPath = path;
    }

    protected String body() {
        FileDirectoryServer fileDirectoryServer = new FileDirectoryServer(rootPath);
        String requestPath = request.getPath();
        List<String> listing = fileDirectoryServer.get(requestPath);

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("Directory listing for " + requestPath + "\n\n");
        for (String filename : listing) {
            responseBuilder.append(filename + "\n");
        }
        return responseBuilder.toString();
    }

}
