package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class ParametersEchoResponseTest extends TestCase {

    public void testPageEchosParametersBack() {
        Request request = new RequestBuilder().setPath("/?key1=value1&key2=value2").toRequest();
        Response response = new ParametersEchoResponse(request);
        String text = new String(response.body());
        assertTrue(text.contains("key1 = value1"));
        assertTrue(text.contains("key2 = value2"));
    }

    public void testEchoRespondsOnEndpoint() {
        assertTrue(ParametersEchoResponse.respondsTo(new RequestBuilder().setPath("/parameters").toRequest()));
        assertFalse(ParametersEchoResponse.respondsTo(new RequestBuilder().setPath("/no-match").toRequest()));
    }
}
