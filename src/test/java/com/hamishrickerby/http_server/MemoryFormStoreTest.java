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

    public void testStoreReturnsKeys() {
        FormStore store = new MemoryFormStore();
        String key1 = "myKey";
        String value1 = "myValue";
        String key2 = "myKey";
        String value2 = "myValue";

        store.write(key1, value1);
        store.write(key2, value2);

        assertTrue(store.keys().contains(key1));
        assertTrue(store.keys().contains(key2));
    }

    public void testStoreSupportsDeletion() {
        FormStore store = new MemoryFormStore();
        String key = "myKey";
        String value = "myValue";
        store.write(key, value);
        store.clear();
        assertEquals("", store.read("myKey"));
    }

}
