package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Logger;
import com.hamishrickerby.http_server.Request;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class LogsResponse extends Response {
    Logger logger;

    public LogsResponse(Request request, Logger logger) {
        super(request);
        this.logger = logger;
    }

    @Override
    protected byte[] body() {
        return logger.read().getBytes();
    }

    public static Boolean respondsTo(Request request) {
        return request.getPath().equalsIgnoreCase("/logs");
    }
}
