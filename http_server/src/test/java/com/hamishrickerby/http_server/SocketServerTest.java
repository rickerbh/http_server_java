package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by rickerbh on 14/08/2016.
 */
public class SocketServerTest extends TestCase {

    public void testConnectionSuccess() {
        SocketServer server = new SocketServer();

        String address = "localhost";
        Socket socket = null;
        try {
            socket = new Socket(address, 5000);
        } catch (IOException e) {
            e.printStackTrace();
            assertNull(e);
        }
        assertNotNull(server);
        assertNotNull(socket);
    }

}
