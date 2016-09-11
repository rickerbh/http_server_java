package com.hamishrickerby.http_server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class Authenticator implements RequestAuthenticator {
    Map<String, String> credentials;
    Set<String> routes;

    public Authenticator() {
        credentials = new HashMap<>();
        routes = new HashSet<>();
    }

    public void addCredentials(String user, String pass) {
        credentials.put(user, pass);
    }

    @Override
    public Boolean authenticate(Request request, String user, String pass) {
        if (!isProtected(request.getPath())) {
            return true;
        }
        return authenticate(user, pass);
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

