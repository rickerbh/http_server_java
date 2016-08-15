package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.MockAsynchronousSocketChannel;
import junit.framework.TestCase;

import java.nio.ByteBuffer;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class HTTPCompletionHandlerTest extends TestCase {
    public void testGetRespondsWith200() {
        HTTPCompletionHandler handler = new HTTPCompletionHandler();
        MockAsynchronousSocketChannel channel = new MockAsynchronousSocketChannel(null);
        channel.setReadData(ByteBuffer.wrap("GET / HTTP/1.1".getBytes()));

        handler.completed(channel, null);

        ByteBuffer writtenBuffer = channel.getWrittenData();
        final byte[] bytes = new byte[writtenBuffer.remaining()];
        writtenBuffer.duplicate().get(bytes);
        String response = new String(bytes);
        assertEquals("HTTP/1.1 200 OK", response);
    }
}
