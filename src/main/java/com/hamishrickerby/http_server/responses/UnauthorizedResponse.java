package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class UnauthorizedResponse extends Response {

    public UnauthorizedResponse(Request request) {
        super(request);
    }

    @Override
    protected int code() {
        return 401;
    }

    @Override
    protected String reason() {
        return "Unauthorized";
    }

    @Override
    protected byte[] body() {
        return new byte[0];
    }
}
