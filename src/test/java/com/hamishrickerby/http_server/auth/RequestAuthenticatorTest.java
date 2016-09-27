package com.hamishrickerby.http_server.auth;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
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
        Request request = new RequestBuilder()
                .setPath("/protected")
                .addHeader("Authorization: Basic dXNlcjpwYXNz")
                .toRequest();
        assertTrue(authenticator.authenticate(request));

        request = new RequestBuilder()
                .setPath("/protected")
                .addHeader("Authorization: Basic dXNlcjpmYWls")
                .toRequest();
        assertFalse(authenticator.authenticate(request));

        request = new RequestBuilder()
                .setPath("/unprotected")
                .addHeader("Authorization: Basic dXNlcjpmYWls")
                .toRequest();
        assertTrue(authenticator.authenticate(request));
    }

    public void testEmptyAuthenticationFieldsFail() {
        RequestAuthenticator authenticator = new RequestAuthenticator();
        authenticator.addCredentials("user", "pass");
        authenticator.addRoute("/protected");
        Request request = new RequestBuilder()
                .setPath("/protected")
                .toRequest();
        assertFalse(authenticator.authenticate(request));

    }

}
