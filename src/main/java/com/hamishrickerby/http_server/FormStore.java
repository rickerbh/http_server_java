package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 12/09/2016.
 */
public interface FormStore {
    String read(String key);

    void write(String key, String value);
}
