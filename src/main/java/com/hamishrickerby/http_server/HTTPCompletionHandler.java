package com.hamishrickerby.http_server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class HTTPCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        try {
            int bytesRead = ch.read(buffer).get(20, TimeUnit.SECONDS);
            boolean running = true;
            while (bytesRead != -1 && running) {
                if (buffer.position() > 2) {
                    buffer.flip();
                    byte[] lineBytes = new byte[bytesRead];
                    buffer.get(lineBytes, 0, bytesRead);
                    ch.write(ByteBuffer.wrap("HTTP/1.1 200 OK".getBytes()));
                    buffer.clear();
                    running = false;
                } else {
                    running = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ch.isOpen()) {
            try {
                ch.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }
}
