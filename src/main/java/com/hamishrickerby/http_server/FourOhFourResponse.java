package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class FourOhFourResponse extends Response {

    public FourOhFourResponse(Request request) {
        super(request);
    }

    @Override
    protected int responseCode() {
        return 404;
    }

    protected byte[] body() {
        return new byte[0];
    }
}
