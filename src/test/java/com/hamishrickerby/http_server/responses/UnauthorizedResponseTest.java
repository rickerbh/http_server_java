package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

import java.util.Arrays;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class UnauthorizedResponseTest extends TestCase {
    public void testUnauthorisedResponseCode() {
        Request request = new RequestBuilder().toRequest();
        Response response = new UnauthorizedResponse(request);
        assertResponseCodeEquals("401", response);
    }

    public void testReason() {
        Request request = new RequestBuilder().toRequest();
        Response response = new UnauthorizedResponse(request);
        assertResponseReasonEquals("Unauthorized", response);
    }

    public void testHeaders() {
        Request request = new RequestBuilder().toRequest();
        Response response = new UnauthorizedResponse(request);
        assertTrue(Arrays.asList(response.headers()).contains("WWW-Authenticate: Basic"));
    }
}
