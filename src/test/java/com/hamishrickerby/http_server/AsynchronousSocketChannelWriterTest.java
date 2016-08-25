package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.MockAsynchronousSocketChannel;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class AsynchronousSocketChannelWriterTest extends TestCase {
    public void testWriterReturnsData() {
        MockAsynchronousSocketChannel channel = new MockAsynchronousSocketChannel(null);
        String expected = "Hello";
        ByteWriter writer = new AsynchronousSocketChannelWriter(channel);
        writer.write(expected.getBytes());
        assertEquals(expected, new String(channel.getWrittenData().array()));
    }
}
