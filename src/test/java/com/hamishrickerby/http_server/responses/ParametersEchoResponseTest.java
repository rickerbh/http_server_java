package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class ParametersEchoResponseTest extends TestCase {

    public void testPageEchosParametersBack() {
        Response response = new ParametersEchoResponse(new Request("GET /?key1=value1&key2=value2 HTTP/1.1"));
        String text = new String(response.body());
        assertTrue(text.contains("key1 = value1"));
        assertTrue(text.contains("key2 = value2"));
    }

    public void testEchoRespondsOnEndpoint() {
        assertTrue(ParametersEchoResponse.respondsTo(new Request("GET /parameters HTTP/1.1")));
        assertFalse(ParametersEchoResponse.respondsTo(new Request("GET /no-match HTTP/1.1")));
    }
}
