package com.hamishrickerby.http_server.async;

import com.hamishrickerby.http_server.ByteReader;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class AsynchronousSocketChannelReader implements ByteReader {
    private static int BUFFER_SIZE = 8192;
    private static int TIMEOUT_VALUE = 20;
    private static java.util.concurrent.TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

    AsynchronousSocketChannel channel;

    public AsynchronousSocketChannelReader(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public byte[] read() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        byte[] requestBytes = null;
        try {
            int bytesRead = channel.read(buffer).get(TIMEOUT_VALUE, TIMEOUT_UNIT);
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
