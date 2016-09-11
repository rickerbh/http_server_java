package com.hamishrickerby.http_server.auth;

import com.hamishrickerby.http_server.Request;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class RequestAuthenticatorTest extends TestCase {

    public void testCredentialsVerification() {
        RequestAuthenticator authenticator = new RequestAuthenticator();
        authenticator.addCredentials("user", "pass");
        assertTrue(authenticator.authenticate("user", "pass"));
        assertFalse(authenticator.authenticate("user", "fail"));
        assertFalse(authenticator.authenticate("no-user", "pass"));
        assertFalse(authenticator.authenticate("no-user", "fail"));
    }

    public void testRouteProtection() {
        RequestAuthenticator authenticator = new RequestAuthenticator();
        String protectedRoute = "/protected";
        authenticator.addRoute(protectedRoute);
        assertTrue(authenticator.isProtected(protectedRoute));
        assertFalse(authenticator.isProtected("/unprotected"));
    }

    public void testRequestAuthenticationInterface() {
        RequestAuthenticator authenticator = new RequestAuthenticator();
        authenticator.addCredentials("user", "pass");
        authenticator.addRoute("/protected");
        Request request = new Request("GET /protected HTTP/1.1");
        assertTrue(authenticator.authenticate(request, "user", "pass"));
        assertFalse(authenticator.authenticate(request, "user", "fail"));
        request = new Request("GET /unprotected HTTP/1.1");
        assertTrue(authenticator.authenticate(request, "anything", "passes"));
    }

}
