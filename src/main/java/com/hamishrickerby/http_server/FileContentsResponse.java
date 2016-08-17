package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileContentsResponse extends Response {
    String rootPath;

    public FileContentsResponse(Request request) {
        super(request);
    }

    public void setRootPath(String path) {
        this.rootPath = path;
    }

    public byte[] body() {
        FileServer server = new FileServer(rootPath);
        return server.get(request.getPath());
    }

}
