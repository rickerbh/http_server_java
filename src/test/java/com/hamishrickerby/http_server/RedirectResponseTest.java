package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.util.Arrays;

import static com.hamishrickerby.http_server.HTTPServerTestUtils.assertResponseCodeEquals;


/**
 * Created by rickerbh on 18/08/2016.
 */
public class RedirectResponseTest extends TestCase {
    public void testRedirectCodeIs302() {
        RedirectResponse response = new RedirectResponse(new Request("GET /redirect HTTP/1.1"));
        assertResponseCodeEquals("302", response);
    }

    public void testRedirectLocationIsLocalhost() {
        RedirectResponse response = new RedirectResponse(new Request("GET /redirect HTTP/1.1"));
        response.setRedirect("/redirect", "http://localhost/redirect-location");
        assertTrue(Arrays.asList(response.headers()).contains("Location: http://localhost/redirect-location"));
    }

    public void testUnknownRedirectPointsToRoot() {
        RedirectResponse response = new RedirectResponse(new Request("GET /unconfigured HTTP/1.1"));
        assertTrue(Arrays.asList(response.headers()).contains("Location: /"));
    }

    public void testRedirectHasEmptyBody() {
        RedirectResponse response = new RedirectResponse(new Request("GET /redirect HTTP/1.1"));
        assertEquals(0, response.body().length);
    }

    public void testDefaultRedirectResponseReturnsMatchSuccesses() {
        assertTrue(RedirectResponse.existsFor("/redirect"));
        assertFalse(RedirectResponse.existsFor("/no-match"));
    }

    public void testDefaultRedirectsContainLocalhost5000() {
        assertEquals("http://localhost:5000/", RedirectResponse.defaultRedirects().get("/redirect"));
    }

}
