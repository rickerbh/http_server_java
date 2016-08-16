package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by rickerbh on 14/08/2016.
 */
public class SocketServerTest extends TestCase {

    public void testConnectionIsEstablishedSuccessfully() {
        int portNumber = 5000;
        SocketServer server = new SocketServer(portNumber);

        String address = "localhost";
        Socket socket = null;
        try {
            socket = new Socket(address, portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            assertNull(e);
        }
        assertNotNull(server);
        assertNotNull(socket);
    }

}
