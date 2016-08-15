package com.hamishrickerby.http_server;

import java.util.HashMap;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class AppArguments extends HashMap<String, String> {
    public AppArguments(String[] arguments) {
        for (int i = 0; i + 1 < arguments.length; i = i + 2) {
            String key = arguments[i];
            String value = arguments[i + 1];
            this.put(key, value);
        }
    }
}
