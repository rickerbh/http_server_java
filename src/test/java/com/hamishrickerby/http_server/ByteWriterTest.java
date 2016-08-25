package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.FakeWriter;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class ByteWriterTest extends TestCase {
    public void testWrittenBytesCanBeRead() {
        String expected = "Hello";
        FakeWriter writer = new FakeWriter();
        writer.write(expected.getBytes());
        assertEquals(expected, new String(writer.readWrittenBytes()));
    }
}
