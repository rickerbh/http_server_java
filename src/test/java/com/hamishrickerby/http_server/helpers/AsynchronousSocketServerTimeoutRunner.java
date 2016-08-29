package com.hamishrickerby.http_server.helpers;

import com.hamishrickerby.http_server.AsynchronousSocketServer;
import com.hamishrickerby.http_server.SocketServer;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class AsynchronousSocketServerTimeoutRunner implements Runnable {
    public Long timeToLive;

    public void run() {
        SocketServer server = null;
        try {
            server = new AsynchronousSocketServer();
            server.bind(5000);
        } catch (IOException e) {
            fail("Server failed to instantiate or bind.");
        }
        server.start();
        server.awaitTermination(timeToLive);
        server.stop();
    }
}
