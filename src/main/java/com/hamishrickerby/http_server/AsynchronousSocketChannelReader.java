package com.hamishrickerby.http_server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class AsynchronousSocketChannelReader implements ByteReader {

    AsynchronousSocketChannel channel;

    public AsynchronousSocketChannelReader(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public byte[] read() {
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        byte[] requestBytes = null;
        try {
            int bytesRead = channel.read(buffer).get(20, TimeUnit.SECONDS);
            requestBytes = new byte[bytesRead];

            if (bytesRead > 0 && buffer.position() > 2) {
                buffer.flip();
                buffer.get(requestBytes, 0, bytesRead);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return requestBytes;
        }
    }
}
