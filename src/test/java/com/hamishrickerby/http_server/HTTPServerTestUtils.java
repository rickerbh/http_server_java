package com.hamishrickerby.http_server;

import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class HTTPServerTestUtils {
    protected static void assertResponseCodeEquals(String code, Response response) {
        String responseText = new String(response.getBytes());
        Scanner s = new Scanner(responseText);
        s.next();
        String receivedCode = s.next();
        assertEquals(code, receivedCode);
    }
}
