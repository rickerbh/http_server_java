package com.hamishrickerby.http_server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class Headers {
    private static String HEADER_SEPARATOR = ": ";

    Map<String, String> headers;

    public Headers() {
        headers = new HashMap<>();
    }

    public List<String> list() {
        List<String> headerList = new ArrayList<>();
        for (String key : headers.keySet()) {
            headerList.add(key + HEADER_SEPARATOR + headers.get(key));
        }
        return headerList;
    }

    public void put(String key, String value) {
        headers.put(key, value);
    }
}
