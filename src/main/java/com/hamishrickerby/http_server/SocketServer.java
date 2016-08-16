package com.hamishrickerby.http_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * Created by rickerbh on 14/08/2016.
 */
public class SocketServer {
    final AsynchronousServerSocketChannel listener;

    public SocketServer(int portNumber, String rootDirectory) throws IOException {
        listener = AsynchronousServerSocketChannel.open();
        listener.bind(new InetSocketAddress(portNumber));
        listener.accept(null, new HTTPCompletionHandler(rootDirectory));
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
