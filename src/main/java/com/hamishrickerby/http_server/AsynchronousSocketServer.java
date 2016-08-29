package com.hamishrickerby.http_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class AsynchronousSocketServer implements SocketServer {

    final AsynchronousServerSocketChannel listener;
    final AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newSingleThreadExecutor());
    ResponseCoordinator coordinator;

    public AsynchronousSocketServer() throws IOException {
        listener = AsynchronousServerSocketChannel.open(group);
    }

    public void bind(int portNumber) throws IOException {
        listener.bind(new InetSocketAddress(portNumber));
    }

    public void setResponseCoordinator(ResponseCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public void start() {
        AsynchronousSocketChannelCompletionHandler handler = new AsynchronousSocketChannelCompletionHandler(listener);
        handler.setResponseCoordinator(coordinator);
        listener.accept(null, handler);
    }

    public void stop() {
        if (listener.isOpen()) {
            try {
                listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void awaitTermination(Long secondsToSurviveFor) {
        try {
            group.awaitTermination(secondsToSurviveFor, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // This is OK. It just signals the group has been terminated.
        }
    }

}
