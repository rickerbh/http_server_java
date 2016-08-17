package com.hamishrickerby.http_server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class HTTPCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    ResponseFactory responseFactory;

    public HTTPCompletionHandler(String rootDirectory) {
        responseFactory = new ResponseFactory(rootDirectory);
    }

    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
        String requestText = extractRequestText(ch);

        Request request = new Request(requestText);
        Response response = responseFactory.makeResponse(request);

        sendResponse(ch, response);

        closeChannel(ch);
    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }

    private String extractRequestText(AsynchronousSocketChannel ch) {
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        byte[] requestBytes = null;
        try {
            int bytesRead = ch.read(buffer).get(20, TimeUnit.SECONDS);
            requestBytes = new byte[bytesRead];

            if (bytesRead > 0 && buffer.position() > 2) {
                buffer.flip();
                buffer.get(requestBytes, 0, bytesRead);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return new String(requestBytes);
        }
    }

    private void sendResponse(AsynchronousSocketChannel ch, Response response) {
        ch.write(ByteBuffer.wrap(response.getBytes()));
    }

    private void closeChannel(AsynchronousSocketChannel ch) {
        if (ch.isOpen()) {
            try {
                ch.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
