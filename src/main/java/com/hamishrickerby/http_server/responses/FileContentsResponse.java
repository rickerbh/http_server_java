package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.*;

import java.util.Arrays;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FileContentsResponse extends FileSystemResponse {
    protected FilePatchCache cache;

    public FileContentsResponse(Request request, String rootPath, FilePatchCache cache) {
        super(request, rootPath);

        Method[] supportedMethods = {
                Method.GET,
                Method.HEAD,
                Method.OPTIONS,
                Method.PATCH
        };
        this.supportedMethods = Arrays.asList(supportedMethods);

        this.cache = cache;

        storePatchIfNeeded();
    }

    protected void storePatchIfNeeded() {
        if (request.getMethod() == Method.PATCH && fileExists(rootPath, request.getPath(), cache)) {
            FilePatch patch = new FilePatch(request.getPath(), request.getHeader("ETag"), request.getDataContent());
            cache.store(patch);
        }
    }

    @Override
    protected String reason() {
        switch (request.getMethod()) {
            case PATCH:
                return "No Content";
            default:
                return super.reason();
        }
    }

    @Override
    protected int code() {
        switch (request.getMethod()) {
            case PATCH:
                return 204;
            default:
                return super.code();
        }
    }

    public byte[] body() {
        if (request.getMethod() == Method.PATCH) {
            return new byte[0];
        } else if (cache != null && cache.retrieve(request.getPath()) != null) {
            return cache.retrieve(request.getPath()).getContents();
        }
        FileServer server = new FileServer(rootPath);
        return server.get(request.getPath());
    }

    public static boolean fileExists(String rootPath, String filePath, FilePatchCache cache) {
        if (cache != null && cache.retrieve(filePath) != null) {
            return true;
        }
        FileServer server = new FileServer(rootPath);
        return server.fileExists(filePath);
    }

}
