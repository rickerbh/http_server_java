package com.hamishrickerby.http_server.async;

import com.hamishrickerby.http_server.ByteReader;
import com.hamishrickerby.http_server.mocks.MockAsynchronousSocketChannel;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class AsynchronousSocketChannelReaderTest extends TestCase {

    public void testReaderReturnsData() {
        MockAsynchronousSocketChannel channel = new MockAsynchronousSocketChannel(null);
        String expected = "Hello";
        channel.setReadData(expected);
        ByteReader reader = new AsynchronousSocketChannelReader(channel);
        assertEquals(expected, new String(reader.read()));
    }

}
