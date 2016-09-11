package com.hamishrickerby.http_server.auth;

import com.hamishrickerby.http_server.Request;

/**
 * Created by rickerbh on 11/09/2016.
 */
public interface Authenticator {
    Boolean authenticate(Request request, String user, String pass);
}
