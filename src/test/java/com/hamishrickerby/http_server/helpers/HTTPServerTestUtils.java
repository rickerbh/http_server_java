package com.hamishrickerby.http_server.helpers;

import com.hamishrickerby.http_server.Response;

import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class HTTPServerTestUtils {
    public static void assertResponseCodeEquals(String code, Response response) {
        Scanner s = responseScanner(response);
        s.next();
        String receivedCode = s.next();
        assertEquals(code, receivedCode);
    }

    public static void assertResponseReasonEquals(String reason, Response response) {
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

