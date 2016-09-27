package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class FourEightTeenResponseTest extends TestCase {
    public void testCoffeeEndpointHas418Response() {
        Request request = new RequestBuilder()
                .setPath("/coffee")
                .toRequest();
        Response response = new FourEightTeenResponse(request);
        assertResponseCodeEquals("418", response);
        String teapotResponse = "I'm a teapot";
        assertResponseReasonEquals(teapotResponse, response);
        String body = new String(response.body());
        assertTrue(body.contains(teapotResponse));
    }

    public void testTeaEndpointHas200Response() {
        Request request = new RequestBuilder()
                .setPath("/tea")
                .toRequest();
        Response response = new FourEightTeenResponse(request);
        assertResponseCodeEquals("200", response);
        assertResponseReasonEquals("OK", response);
        assertEquals(0, response.body().length);
    }

    public void testResponseHandlesCoffee() {
        Request request = new RequestBuilder()
                .setPath("/coffee")
                .toRequest();
        assertTrue(FourEightTeenResponse.existsFor(request));
    }

    public void testResponseHandlesTea() {
        Request request = new RequestBuilder()
                .setPath("/tea")
                .toRequest();
        assertTrue(FourEightTeenResponse.existsFor(request));
    }

    public void testResponseDoesNotHandleCola() {
        Request request = new RequestBuilder()
                .setPath("/cola")
                .toRequest();
        assertFalse(FourEightTeenResponse.existsFor(request));
    }
}
