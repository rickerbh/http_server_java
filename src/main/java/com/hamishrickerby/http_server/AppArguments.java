package com.hamishrickerby.http_server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class AppArguments {
    Map<String, String> arguments;

    public AppArguments(String[] arguments) {
        this.arguments = new HashMap<String, String>();
        for (int i = 0; i + 1 < arguments.length; i = i + 2) {
            String key = arguments[i];
            String value = arguments[i + 1];
            this.arguments.put(key, value);
        }
    }

    public String getOrDefault(String key, String defaultValue) {
        return arguments.getOrDefault(key, defaultValue);
    }

    public String get(String key) {
        return arguments.get(key);
    }

    public int size() {
        return arguments.size();
    }
}
