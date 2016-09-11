package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.responses.Response;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class FakeResponse extends Response {
    byte[] body = new byte[0];

    public FakeResponse(Request request) {
        super(request);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] body() {
        return body;
    }
}
