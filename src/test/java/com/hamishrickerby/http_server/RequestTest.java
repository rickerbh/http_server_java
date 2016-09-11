package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class RequestTest extends TestCase {
    static String CRLF = "\r\n";

    public void testRequestParsesFieldsAndMakesValuesAccessible() {
        String requestText = Method.GET.name() + " / HTTP/1.1";
        Request request = new Request(requestText);
        assertEquals(requestText, request.getRequestString());
        assertEquals(Method.GET, request.getMethod());
        assertEquals("/", request.getPath());
        assertEquals("HTTP/1.1", request.getVersion());
    }

    public void testTrailingSlashOnDirectoriesAppearsWhenGettingSubdirectories() {
        String requestText = Method.GET.name() + " /subdirectory HTTP/1.1";
        Request request = new Request(requestText);
        assertEquals("/subdirectory/", request.pathWithTrailingSlash());

        requestText = Method.GET.name() + " /subdirectory/ HTTP/1.1";
        request = new Request(requestText);
        assertEquals("/subdirectory/", request.pathWithTrailingSlash());
    }

    public void testRequestHeadersAreParsedOut() {
        String k1 = "Content-Length";
        String v1 = "26012";
        String k2 = "Content-Type";
        String v2 = "image/gif";
        String separator = ": ";
        String headerString = new StringBuilder()
                .append(k1)
                .append(separator)
                .append(v1)
                .append(CRLF)
                .append(k2)
                .append(separator)
                .append(v2)
                .append(CRLF)
                .toString();

        String requestText = new StringBuilder()
                .append(Method.GET.name())
                .append(" ")
                .append("/subdirectory HTTP/1.1")
                .append(CRLF)
                .append(headerString)
                .toString();
        Request request = new Request(requestText);
        assertEquals(v1, request.getHeader(k1));
        assertEquals(v2, request.getHeader(k2));
    }

    public void testRequestAuthIsParsedOut() {
        String requestText = new StringBuilder()
                .append(Method.GET.name())
                .append(" ")
                .append("/protected HTTP/1.1")
                .append(CRLF)
                .append("Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l")
                .append(CRLF)
                .toString();
        Request request = new Request(requestText);
        assertEquals("Aladdin", request.getAuthUser());
        assertEquals("OpenSesame", request.getAuthPassword());
    }

}
