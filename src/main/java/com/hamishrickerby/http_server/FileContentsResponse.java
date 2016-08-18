package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileContentsResponse extends FileSystemResponse {
    public FileContentsResponse(Request request, String rootPath) {
        super(request, rootPath);
    }

    public byte[] body() {
        FileServer server = new FileServer(rootPath);
        return server.get(request.getPath());
    }

}
