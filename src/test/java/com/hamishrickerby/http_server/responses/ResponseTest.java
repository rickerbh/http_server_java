package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.mocks.FakeResponse;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class ResponseTest extends TestCase {
    public void testSuccessfulRequestResponseWith200() {
        Response response = new FakeResponse(new Request("GET / HTTP/1.1"));
        assertResponseCodeEquals("200", response);
        assertResponseReasonEquals("OK", response);
    }

    public void testLineBreaksAreCRLFOnHeader() {
        Response response = new FakeResponse(new Request("GET / HTTP/1.1"));
        byte[] responseBytes = response.getBytes();

        String responseText = new String(responseBytes);
        assertTrue(responseText.startsWith("HTTP/1.1 200 OK\r\n\r\n"));
    }

    public void testGetResponseBody() {
        FakeResponse response = new FakeResponse(new Request("GET / HTTP/1.1"));
        response.setBody("I should be visible".getBytes());

        byte[] responseBytes = response.getBytes();
        String responseText = new String(responseBytes);
        assertTrue(responseText.contains("visible"));
    }

    public void testHeadAndOptionResponseBody() {
        String[] methods = {Method.HEAD.name(), Method.OPTIONS.name()};
        for (String method : methods) {
            FakeResponse response = new FakeResponse(new Request(method + " / HTTP/1.1"));
            response.setBody("I should be invisible".getBytes());

            byte[] responseBytes = response.getBytes();
            String responseText = new String(responseBytes);
            assertFalse(responseText.contains("invisible"));
        }
    }

    public void testSupportedMethodsReturnHeadAndOptionAsDefaults() {
        FakeResponse response = new FakeResponse(new Request(Method.OPTIONS.name() + " / HTTP/1.1"));
        List<Method> methods = response.getSupportedMethods();
        assertTrue(methods.contains(Method.GET));
        assertTrue(methods.contains(Method.HEAD));
        assertTrue(methods.contains(Method.OPTIONS));
    }

    public void testDefaultHeadersAreEmpty() {
        FakeResponse response = new FakeResponse(new Request(Method.GET.name() + " / HTTP/1.1"));
        assertEquals(0, response.headers().length);
    }

    public void testOptionHeadersContainHeadAndOptions() {
        FakeResponse response = new FakeResponse(new Request(Method.OPTIONS.name() + " / HTTP/1.1"));
        List<String> headers = new ArrayList<>(Arrays.asList(response.headers()));
        assertTrue(headers.contains("Allow: GET,HEAD,OPTIONS"));
    }

    public void testValidatedResponseCodeValidatesMethod() {
        FakeResponse response = new FakeResponse(new Request(Method.OPTIONS.name() + " / HTTP/1.1"));
        assertEquals(200, response.validatedCode());
        response = new FakeResponse(new Request(Method.POST.name() + " / HTTP/1.1"));
        assertEquals(405, response.validatedCode());
    }

    public void testBogusRequestReturns405Response() {
        FakeResponse response = new FakeResponse(new Request("BOGUS / HTTP/1.1"));
        assertEquals(405, response.validatedCode());
    }
}
