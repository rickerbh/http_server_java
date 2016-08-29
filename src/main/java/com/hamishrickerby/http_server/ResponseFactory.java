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
        Response response;
        if (RedirectResponse.existsFor(request.getPath())) {
            response = new RedirectResponse(request);
        } else if (FourEightTeenResponse.existsFor(request)) {
            response = new FourEightTeenResponse(request);
        } else if (isFileResponse(request)) {
            response = new FileContentsResponse(request, rootPath);
        } else if (isDirectoryResponse(request)) {
            response = new DirectoryListingResponse(request, rootPath);
        } else {
            response = new FourOhFourResponse(request);
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
