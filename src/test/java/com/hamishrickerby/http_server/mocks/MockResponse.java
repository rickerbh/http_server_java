package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.Response;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class MockResponse extends Response {
    public MockResponse(Request request) {
        super(request);
    }

    public String body() {
        return "";
    }
}