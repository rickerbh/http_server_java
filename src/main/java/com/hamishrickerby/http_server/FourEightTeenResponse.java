package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class FourEightTeenResponse extends Response {
    private int code = 200;
    private String reason;
    private String bodyText;

    public FourEightTeenResponse(Request request) {
        super(request);
        if (isRequestCoffee()) {
            code = 418;
            reason = "I'm a teapot";
            bodyText = "I'm a teapot, short and stout.";
        } else {
            code = 200;
            reason = "OK";
            bodyText = "";
        }
    }

    @Override
    protected int code() {
        return code;
    }

    @Override
    protected String reason() {
        return reason;
    }

    protected byte[] body() {
        return bodyText.getBytes();
    }

    private Boolean isRequestCoffee() {
        return request.getPath().equals("/coffee");
    }

    private Boolean isRequestTea() {
        return request.getPath().equals("/tea");
    }

    public static Boolean existsFor(Request request) {
        FourEightTeenResponse response = new FourEightTeenResponse(request);
        return response.isRequestCoffee() || response.isRequestTea();
    }
}
