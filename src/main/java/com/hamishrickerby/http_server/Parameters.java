package com.hamishrickerby.http_server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class Parameters {
    private static String SEPARATOR = " = ";

    Map<String, String> parameters;

    public Parameters() {
        this.parameters = new HashMap<>();
    }

    public void parse(String headerString) {
        String[] items = headerString.split("&");
        for (String item : Arrays.asList(items)) {
            String[] components = item.split("=");
            if (components.length >= 2) {
                try {
                    String decodedValue = URLDecoder.decode(components[1].trim(), "UTF-8");
                    parameters.put(components[0].trim(), decodedValue);
                } catch (UnsupportedEncodingException e) {
                    // Ignore the error.
                }
            }
        }
    }

    public Set<String> keys() {
        return parameters.keySet();
    }

    public String get(String key) {
        return parameters.getOrDefault(key, "");
    }

}
