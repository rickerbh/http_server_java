package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.FakeReader;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class ByteReaderTest extends TestCase {

    public void testReaderReturnsData() {
        String expected = "Hello";
        ByteReader reader = new FakeReader("Hello");
        assertEquals(expected, new String(reader.read()));
    }

}
