package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 18/08/2016.
 */
public abstract class FileSystemResponse extends Response {
    String rootPath;

    public FileSystemResponse(Request request, String rootPath) {
        super(request);
        this.rootPath = rootPath;
    }
}
