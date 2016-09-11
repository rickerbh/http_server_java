package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.mocks.FakeResponse;
import junit.framework.TestCase;

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
}
