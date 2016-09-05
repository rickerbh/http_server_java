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

    public void testHeadersParseData() {
        Headers headers = new Headers();
        String k1 = "Content-Length";
        String v1 = "26012";
        String k2 = "Content-Type";
        String v2 = "image/gif";
        String separator = ": ";
        String crlf = "\r\n";
        String headerString = new StringBuilder()
                .append(k1)
                .append(separator)
                .append(v1)
                .append(crlf)
                .append(k2)
                .append(separator)
                .append(v2)
                .append(crlf)
                .toString();
        headers.parse(headerString);
        assertEquals(v1, headers.get(k1));
        assertEquals(v2, headers.get(k2));
    }

}
