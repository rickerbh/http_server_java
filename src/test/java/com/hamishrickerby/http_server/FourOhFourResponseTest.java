package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.util.Scanner;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class FourOhFourResponseTest extends TestCase {

    public void testResponseCodeIs404() {
        Request request = new Request("GET /i-dont-exist HTTP/1.1");
        FourOhFourResponse response = new FourOhFourResponse(request);
        String responseText = new String(response.getBytes());
        Scanner s = new Scanner(responseText);
        s.next();
        String code = s.next();
        assertEquals("404", code);
    }

}
