package com.hamishrickerby.http_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;

/**
 * Created by rickerbh on 14/08/2016.
 */
public class SocketServer {
    final AsynchronousServerSocketChannel listener;
    final AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newSingleThreadExecutor());

    public SocketServer(int portNumber, String rootDirectory) throws IOException {
        listener = AsynchronousServerSocketChannel.open(group);
        listener.bind(new InetSocketAddress(portNumber));
        AsynchronousSocketChannelCompletionHandler handler = new AsynchronousSocketChannelCompletionHandler(listener);
        handler.setResponseCoordinator(new HTTPResponseCoordinator(rootDirectory));
        listener.accept(null, handler);
    }

    public void close() {
        if (listener.isOpen()) {
            try {
                listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
