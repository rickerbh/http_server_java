package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class UnauthorizedResponseTest extends TestCase {
    public void testUnauthorisedResponseCode() {
        Request request = new Request("GET / HTTP/1.1");
        Response response = new UnauthorizedResponse(request);
        assertResponseCodeEquals("401", response);
    }

    public void testReason() {
        Request request = new Request("GET / HTTP/1.1");
        Response response = new UnauthorizedResponse(request);
        assertResponseReasonEquals("Unauthorized", response);
    }
}
