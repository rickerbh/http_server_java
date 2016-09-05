package com.hamishrickerby.http_server.helpers;

import com.hamishrickerby.http_server.AsynchronousSocketServer;
import com.hamishrickerby.http_server.Server;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class AsynchronousSocketServerTimeoutRunner implements Runnable {
    public Long timeToLive;

    public void run() {
        Server server = null;
        try {
            server = new AsynchronousSocketServer(5000);
        } catch (IOException e) {
            fail("Server failed to instantiate");
        }
        server.start();
        server.awaitTermination(timeToLive);
        server.stop();
    }
}
