package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class HeadersTest extends TestCase {
    public void testEmptyHeadersAreEmpty() {
        Headers headers = new Headers();
        assertEquals(0, headers.list().size());
    }

    public void testHeadersWithContentsReturnData() {
        Headers headers = new Headers();
        headers.put("Content-Type", "image/jpeg");
        headers.put("Content-Length", "100");
        assertEquals(2, headers.list().size());
        assertTrue(headers.list().contains("Content-Type: image/jpeg"));
        assertTrue(headers.list().contains("Content-Length: 100"));
    }

}
