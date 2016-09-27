package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

import java.util.Arrays;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;


/**
 * Created by rickerbh on 18/08/2016.
 */
public class RedirectResponseTest extends TestCase {
    public void testRedirectCodeIs302() {
        Request request = new RequestBuilder().setPath("/redirect").toRequest();
        RedirectResponse response = new RedirectResponse(request);
        assertResponseCodeEquals("302", response);
    }

    public void testRedirectLocationIsLocalhost() {
        Request request = new RequestBuilder().setPath("/redirect").toRequest();
        RedirectResponse response = new RedirectResponse(request);
        response.setRedirect("/redirect", "http://localhost/redirect-location");
        assertTrue(Arrays.asList(response.headers()).contains("Location: http://localhost/redirect-location"));
    }

    public void testUnknownRedirectPointsToRoot() {
        Request request = new RequestBuilder().setPath("/unconfigured").toRequest();
        RedirectResponse response = new RedirectResponse(request);
        assertTrue(Arrays.asList(response.headers()).contains("Location: /"));
    }

    public void testRedirectHasEmptyBody() {
        Request request = new RequestBuilder().setPath("/redirect").toRequest();
        RedirectResponse response = new RedirectResponse(request);
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
