package com.hamishrickerby.http_server;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class HTTPCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    AsynchronousServerSocketChannel listeningChannel;
    HTTPHandler handler;

    public HTTPCompletionHandler(String rootDirectory, AsynchronousServerSocketChannel listeningChannel) {
        handler = new HTTPHandler(rootDirectory);
        this.listeningChannel = listeningChannel;
    }

    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
        setupHandlerForNextConnection();

        ByteReader reader = new AsynchronousSocketChannelReader(ch);
        ByteWriter writer = new AsynchronousSocketChannelWriter(ch);
        handler.run(reader, writer);

        closeChannel(ch);
    }

    private void setupHandlerForNextConnection() {
        listeningChannel.accept(null, this);
    }

    @Override
    public void failed(Throwable exc, Void attachment) {

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
