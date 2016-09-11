package com.hamishrickerby.http_server.auth;

import com.hamishrickerby.http_server.Request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class RequestAuthenticator implements Authenticator {
    Map<String, String> credentials;
    Set<String> routes;

    public RequestAuthenticator() {
        credentials = new HashMap<>();
        routes = new HashSet<>();
    }

    public void addCredentials(String user, String pass) {
        credentials.put(user, pass);
    }

    @Override
    public Boolean authenticate(Request request) {
        if (!isProtected(request.getPath())) {
            return true;
        }
        return authenticate(request.getAuthUser(), request.getAuthPassword());
    }

    public Boolean authenticate(String user, String pass) {
        return credentials.containsKey(user) && credentials.get(user).equals(pass);
    }

    public void addRoute(String route) {
        routes.add(route);
    }

    public Boolean isProtected(String route) {
        return routes.contains(route);
    }
}

