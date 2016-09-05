package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.FakeReader;
import com.hamishrickerby.http_server.mocks.FakeWriter;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 5/09/2016.
 */
public class ConnectionTest extends TestCase {

    public void testConnectionCanBeReadReturnsData() {
        String expected = "Hello";
        ByteReader reader = new FakeReader("Hello");
        Connection connection = new Connection(reader, null);
        assertEquals(expected, new String(connection.read()));
    }

    public void testConnectionWrittenBytesCanBeRead() {
        String expected = "Hello";
        FakeWriter writer = new FakeWriter();
        Connection connection = new Connection(null, writer);
        connection.write(expected.getBytes());
        assertEquals(expected, new String(writer.readWrittenBytes()));
    }
}
