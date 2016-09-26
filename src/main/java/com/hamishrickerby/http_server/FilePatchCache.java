package com.hamishrickerby.http_server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rickerbh on 26/09/2016.
 */
public class FilePatchCache {

    protected Map<String, FilePatch> cache;

    public FilePatchCache() {
        cache = new HashMap<>();
    }

    public void store(FilePatch patch) {
        cache.put(patch.path, patch);
    }

    public FilePatch retrieve(String path) {
        return cache.get(path);
    }

    public boolean contains(String path) {
        return cache.get(path) != null;
    }
}
