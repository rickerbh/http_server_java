package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 10/09/2016.
 */
public interface Logger {
    void log(String message);

    String read();

    void flush();
}
