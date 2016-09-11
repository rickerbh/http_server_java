package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class MethodsTest extends TestCase {
    public void testCreationFromString() {
        Method method = Method.valueOf("GET");
        assertEquals(Method.GET, method);
    }

    public void testStringFromEnum() {
        Method method = Method.HEAD;
        assertEquals("HEAD", method.name());
    }
}
