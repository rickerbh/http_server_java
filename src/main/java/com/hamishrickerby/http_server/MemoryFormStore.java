package com.hamishrickerby.http_server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by rickerbh on 12/09/2016.
 */
public class MemoryFormStore implements FormStore {
    Map<String, String> store;

    public MemoryFormStore() {
        this.store = new HashMap<>();
    }

    @Override
    public String read(String key) {
        return store.getOrDefault(key, "");
    }

    @Override
    public void write(String key, String value) {
        store.put(key, value);
    }

    @Override
    public Set<String> keys() {
        return store.keySet();
    }
}
