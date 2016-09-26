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

    public void testRequestDataIsParsedOut() {
        String requestText = new StringBuilder()
                .append(Method.POST.name())
                .append(" ")
                .append("/form HTTP/1.1")
                .append(CRLF)
                .append(CRLF)
                .append("key=value&abc=123")
                .append(CRLF)
                .toString();
        Request request = new Request(requestText);
        assertEquals("value", request.readData("key"));
        assertEquals("123", request.readData("abc"));
        assertEquals("", request.readData("not-here"));
        assertEquals("key=value&abc=123", new String(request.getDataContent()));
    }

    public void testRequestDataIsParsedOutWithHeaders() {
        String requestText = new StringBuilder()
                .append(Method.POST.name())
                .append(" ")
                .append("/form HTTP/1.1")
                .append(CRLF)
                .append("Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l")
                .append(CRLF)
                .append(CRLF)
                .append("key=value&abc=123")
                .append(CRLF)
                .toString();
        Request request = new Request(requestText);
        assertEquals("Aladdin", request.getAuthUser());
        assertEquals("OpenSesame", request.getAuthPassword());
        assertEquals("value", request.readData("key"));
        assertEquals("123", request.readData("abc"));
        assertEquals("", request.readData("not-here"));
    }

    public void testRequestDataReturnsAllKeys() {
        String requestText = new StringBuilder()
                .append(Method.POST.name())
                .append(" ")
                .append("/form HTTP/1.1")
                .append(CRLF)
                .append(CRLF)
                .append("key=value&abc=123")
                .append(CRLF)
                .toString();
        Request request = new Request(requestText);
        assertEquals(2, request.dataKeys().size());
    }

    public void testRequestHandlesBogusMethodWithoutCrashing() {
        Request request = new Request("BOGUS / HTTP/1.1");
        assertEquals(Method.UNKNOWN, request.getMethod());
    }

    public void testRequestWithParameters() {
        Request request = new Request("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1");
        Parameters parameters = request.getParameters();
        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", parameters.get("variable_1"));
        assertEquals("stuff", parameters.get("variable_2"));
    }

}
