package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class EmptyResponse extends Response {
    public EmptyResponse(Request request) {
        super(request);
    }

    @Override
    protected byte[] body() {
        return new byte[0];
    }

    public static boolean respondsTo(Request request, String endpoint) {
        return request.getPath().equals(endpoint);
    }

}
