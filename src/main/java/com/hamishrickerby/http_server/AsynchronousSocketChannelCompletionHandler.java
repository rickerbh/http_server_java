package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.responses.ResponseCoordinator;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class AsynchronousSocketChannelCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    AsynchronousServerSocketChannel listeningChannel;
    ResponseCoordinator handler;

    public AsynchronousSocketChannelCompletionHandler(AsynchronousServerSocketChannel listeningChannel) {
        this.listeningChannel = listeningChannel;
    }

    public void setResponseCoordinator(ResponseCoordinator coordinator) {
        handler = coordinator;
    }

    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
        setupHandlerForNextConnection();

        if (handler != null) {
            ByteReader reader = new AsynchronousSocketChannelReader(ch);
            ByteWriter writer = new AsynchronousSocketChannelWriter(ch);
            handler.run(reader, writer);
        }

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
