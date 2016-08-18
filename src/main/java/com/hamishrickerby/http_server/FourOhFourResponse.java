package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class FourOhFourResponse extends Response {

    public FourOhFourResponse(Request request) {
        super(request);
    }

    @Override
    protected int code() {
        return 404;
    }

    @Override
    protected String reason() {
        return "Not Found";
    }

    protected byte[] body() {
        return new byte[0];
    }
}
