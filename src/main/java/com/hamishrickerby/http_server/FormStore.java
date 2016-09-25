package com.hamishrickerby.http_server;

import java.util.Set;

/**
 * Created by rickerbh on 12/09/2016.
 */
public interface FormStore {
    String read(String key);

    void write(String key, String value);

    Set<String> keys();
}
