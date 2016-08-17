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
        Response response = null;
        if (isFileResponse(request)) {
            FileContentsResponse fcr = new FileContentsResponse(request);
            fcr.setRootPath(rootPath);
            response = fcr;
        } else if (isDirectoryResponse(request)) {
            DirectoryListingResponse dlr = new DirectoryListingResponse(request);
            dlr.setRootPath(rootPath);
            response = dlr;
        }
        return response;
    }

    private boolean isFileResponse(Request request) {
        FileServer server = new FileServer(rootPath);
        return server.fileExists(request.getPath());
    }

    private boolean isDirectoryResponse(Request request) {
        FileDirectoryServer server = new FileDirectoryServer(rootPath);
        return server.directoryExists(request.getPath());
    }

}
