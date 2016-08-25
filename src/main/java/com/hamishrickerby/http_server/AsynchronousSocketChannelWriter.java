package com.hamishrickerby.http_server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class AsynchronousSocketChannelWriter implements ByteWriter {
    private AsynchronousSocketChannel channel;

    public AsynchronousSocketChannelWriter(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void write(byte[] bytes) {
        channel.write(ByteBuffer.wrap(bytes));
    }
}
