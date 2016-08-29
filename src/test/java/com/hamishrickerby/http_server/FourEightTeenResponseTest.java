package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class FourEightTeenResponseTest extends TestCase {
    public void testCoffeeEndpointHas418Response() {
        Request request = new Request("GET /coffee HTTP/1.1");
        Response response = new FourEightTeenResponse(request);
        assertResponseCodeEquals("418", response);
        String teapotResponse = "I'm a teapot";
        assertResponseReasonEquals(teapotResponse, response);
        String body = new String(response.body());
        assertTrue(body.contains(teapotResponse));
    }

    public void testTeaEndpointHas200Response() {
        Request request = new Request("GET /tea HTTP/1.1");
        Response response = new FourEightTeenResponse(request);
        assertResponseCodeEquals("200", response);
        assertResponseReasonEquals("OK", response);
        assertEquals(0, response.body().length);
    }
}
