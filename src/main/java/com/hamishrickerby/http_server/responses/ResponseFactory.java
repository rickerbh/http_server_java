package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FileDirectoryServer;
import com.hamishrickerby.http_server.Request;

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
        } else if (FileContentsResponse.fileExists(rootPath, request.getPath())) {
            response = new FileContentsResponse(request, rootPath);
        } else if (isDirectoryResponse(request)) {
            response = new DirectoryListingResponse(request, rootPath);
        } else {
            response = new FourOhFourResponse(request);
        }
        return response;
    }

    private boolean isDirectoryResponse(Request request) {
        FileDirectoryServer server = new FileDirectoryServer(rootPath);
        return server.directoryExists(request.getPath());
    }

}
