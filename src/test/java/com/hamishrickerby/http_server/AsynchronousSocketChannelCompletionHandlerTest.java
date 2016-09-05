package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.mocks.FakeResponseCoordinator;
import com.hamishrickerby.http_server.mocks.MockAsynchronousServerSocketChannel;
import com.hamishrickerby.http_server.mocks.MockAsynchronousSocketChannel;
import com.hamishrickerby.http_server.responses.ResponseCoordinator;
import junit.framework.TestCase;

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class AsynchronousSocketChannelCompletionHandlerTest extends TestCase {
    public void testGetRespondsWith200() {
        String response = runRequestAndGetResponse("GET / HTTP/1.1");
        Scanner s = new Scanner(response);
        assertEquals("HTTP/1.1 200 OK", s.nextLine());
    }

    private String runRequestAndGetResponse(String request) {
        ResponseCoordinator coordinator = new HTTPResponseCoordinator("./src/test/resources");
        AsynchronousSocketChannelCompletionHandler handler = new AsynchronousSocketChannelCompletionHandler(new MockAsynchronousServerSocketChannel(null));
        handler.setResponseCoordinator(coordinator);
        MockAsynchronousSocketChannel channel = new MockAsynchronousSocketChannel(null);
        channel.setReadData(request);

        handler.completed(channel, null);

        ByteBuffer writtenBuffer = channel.getWrittenData();
        final byte[] bytes = new byte[writtenBuffer.remaining()];
        writtenBuffer.duplicate().get(bytes);
        return new String(bytes);
    }

    public void testGetDirectoryRespondsWithDirectoryContents() {
        String response = runRequestAndGetResponse("GET / HTTP/1.1");
        assertTrue(response.contains("ihniwid.jpg"));
        assertTrue(response.contains("test.html"));
    }

    public void testHandlerRunCalled() {
        AsynchronousSocketChannelCompletionHandler handler = new AsynchronousSocketChannelCompletionHandler(new MockAsynchronousServerSocketChannel(null));
        FakeResponseCoordinator coordinator = new FakeResponseCoordinator();
        handler.setResponseCoordinator(coordinator);

        MockAsynchronousSocketChannel channel = new MockAsynchronousSocketChannel(null);
        channel.setReadData("GET / HTTP/1.1");

        handler.completed(channel, null);

        assertTrue(coordinator.didCallRun());
    }

    public void testEmptyHandlerDoesNotCrash() {
        AsynchronousSocketChannelCompletionHandler handler = new AsynchronousSocketChannelCompletionHandler(new MockAsynchronousServerSocketChannel(null));

        MockAsynchronousSocketChannel channel = new MockAsynchronousSocketChannel(null);
        channel.setReadData("GET / HTTP/1.1");

        handler.completed(channel, null);
    }

}
