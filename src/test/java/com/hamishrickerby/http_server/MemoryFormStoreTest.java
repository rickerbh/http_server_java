package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 12/09/2016.
 */
public class MemoryFormStoreTest extends TestCase {

    public void testStorePutsAndGetsData() {
        FormStore store = new MemoryFormStore();
        String key = "myKey";
        String value = "myValue";
        store.write(key, value);
        assertEquals(value, store.read(key));
    }

    public void testStoreReturnsEmptyDataWhenKeyDoesntExist() {
        FormStore store = new MemoryFormStore();
        assertEquals("", store.read("no-data"));
    }
}
