package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class FourEightTeenResponse extends Response {
    public FourEightTeenResponse(Request request) {
        super(request);
    }

    @Override
    protected int code() {
        if (isRequestCoffee()) {
            return 418;
        }
        return super.code();
    }

    @Override
    protected String reason() {
        if (isRequestCoffee()) {
            return "I'm a teapot";
        }
        return super.reason();
    }

    protected byte[] body() {
        if (isRequestCoffee()) {
            String bodyContent = "I'm a teapot, short and stout.";
            return bodyContent.getBytes();
        }
        return new byte[0];
    }

    private Boolean isRequestCoffee() {
        return request.getPath().equals("/coffee");
    }
}
