package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 11/09/2016.
 */
public interface RequestAuthenticator {
    Boolean authenticate(Request request, String user, String pass);
}
