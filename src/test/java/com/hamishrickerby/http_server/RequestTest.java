package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class RequestTest extends TestCase {
    public void testRequestParsesFieldsAndMakesValuesAccessible() {
        String requestText = "GET / HTTP/1.1";
        Request request = new Request(requestText);
        assertEquals("GET", request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.1", request.getVersion());
    }
}
