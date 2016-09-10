package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class RequestTest extends TestCase {
    public void testRequestParsesFieldsAndMakesValuesAccessible() {
        String requestText = "GET / HTTP/1.1";
        Request request = new Request(requestText);
        assertEquals(requestText, request.getRequestString());
        assertEquals("GET", request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.1", request.getVersion());
    }

    public void testTrailingSlashOnDirectoriesAppearsWhenGettingSubdirectories() {
        String requestText = "GET /subdirectory HTTP/1.1";
        Request request = new Request(requestText);
        assertEquals("/subdirectory/", request.pathWithTrailingSlash());

        requestText = "GET /subdirectory/ HTTP/1.1";
        request = new Request(requestText);
        assertEquals("/subdirectory/", request.pathWithTrailingSlash());
    }

    public void testRequestHeadersAreParsedOut() {
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

        String requestText = new StringBuilder()
                .append("GET /subdirectory HTTP/1.1\r\n")
                .append(headerString)
                .toString();
        Request request = new Request(requestText);
        assertEquals(v1, request.getHeader(k1));
        assertEquals(v2, request.getHeader(k2));
    }
}
