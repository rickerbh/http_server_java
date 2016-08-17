package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class ResponseFactory {
    String rootPath;

    public ResponseFactory(String rootPath) {
        this.rootPath = rootPath;
    }

    public Response makeResponse(Request request) {
        DirectoryListingResponse response = new DirectoryListingResponse(request);
        response.setRootPath(rootPath);
        return response;
    }
}
