package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.MockResponse;
import junit.framework.TestCase;

import java.util.Scanner;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class ResponseTest extends TestCase {
    public void testSuccessfulRequestResponseWith200() {
        Response response = new MockResponse(new Request("GET / HTTP/1.1"));
        byte[] responseBytes = response.getBytes();

        String responseText = new String(responseBytes);
        Scanner s = new Scanner(responseText);
        assertEquals("HTTP/1.1 200 OK", s.nextLine());
    }

    public void testLineBreaksAreCRLFOnHeader() {
        Response response = new MockResponse(new Request("GET / HTTP/1.1"));
        byte[] responseBytes = response.getBytes();

        String responseText = new String(responseBytes);
        assertTrue(responseText.startsWith("HTTP/1.1 200 OK\r\n\r\n"));
    }
}
