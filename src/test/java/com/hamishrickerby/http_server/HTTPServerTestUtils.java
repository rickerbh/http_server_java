package com.hamishrickerby.http_server;

import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class HTTPServerTestUtils {
    protected static void assertResponseCodeEquals(String code, Response response) {
        Scanner s = responseScanner(response);
        s.next();
        String receivedCode = s.next();
        assertEquals(code, receivedCode);
    }

    protected static void assertResponseReasonEquals(String reason, Response response) {
        Scanner s = responseScanner(response);
        s.next();
        s.next();
        s.useDelimiter("\r\n");
        String receivedReason = s.next().trim();
        assertEquals(reason, receivedReason);
    }

    private static Scanner responseScanner(Response response) {
        String responseText = new String(response.getBytes());
        return new Scanner(responseText);
    }
}
