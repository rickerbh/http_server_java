package com.hamishrickerby.http_server;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * Created by rickerbh on 14/08/2016.
 */
public class SocketServer extends Thread {
    public SocketServer() {
        try {
            final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open();
            listener.bind(new InetSocketAddress(5000));
       } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
