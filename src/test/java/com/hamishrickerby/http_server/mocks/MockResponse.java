package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.responses.Response;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class MockResponse extends Response {
    public MockResponse(Request request) {
        super(request);
    }

    public byte[] body() {
        return new byte[0];
    }
}
