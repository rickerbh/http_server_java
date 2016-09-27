package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class EmptyResponseTest extends TestCase {

    public void testEndpointIsConfigurable() {
        Request request = new RequestBuilder()
                .setMethod(Method.OPTIONS)
                .setPath("/testEndPoint")
                .toRequest();
        assertTrue(EmptyResponse.respondsTo(request, "/testEndPoint"));
        assertFalse(EmptyResponse.respondsTo(request, "/no-match"));
    }

}
