package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class FourOhFourResponseTest extends TestCase {

    public void testResponseCodeIs404() {
        Request request = new Request("GET /i-dont-exist HTTP/1.1");
        FourOhFourResponse response = new FourOhFourResponse(request);
        assertResponseCodeEquals("404", response);
        assertResponseReasonEquals("Not Found", response);
    }

}
