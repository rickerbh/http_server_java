package com.hamishrickerby.http_server;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by rickerbh on 14/08/2016.
 */
public class SocketServerTest extends TestCase {

    int portNumber = 5000;
    String address = "localhost";

    public void testConnectionIsEstablishedSuccessfully() {
        SocketServer server = establishServer();

        Socket socket = null;
        try {
            socket = new Socket(address, portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            assertNull(e);
        }
        assertNotNull(server);
        assertNotNull(socket);
        server.close();
    }

    private SocketServer establishServer() {
        SocketServer server = null;
        try {
            server = new SocketServer(portNumber, "");
        } catch (IOException e) {
            e.printStackTrace();
            assertNotNull(e);
        }
        return server;
    }

    public void testConnectionRespondsToData() {
        SocketServer server = establishServer();
        try {
            Socket client = new Socket(address, portNumber);
            byte[] data = "GET / HTTP/1.1".getBytes(Charset.forName("UTF-8"));
            client.getOutputStream().write(data);
            String result = new BufferedReader(new InputStreamReader(client.getInputStream()))
                    .lines()
                    .collect(Collectors.joining("\n"));
            Scanner s = new Scanner(result);
            assertEquals("HTTP/1.1 200 OK", s.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
            assertNull(e);
        } finally {
            server.close();
        }
    }

    public void testServerCloses() {
        SocketServer server = establishServer();
        server.close();
        try {
            Socket socket = new Socket(address, portNumber);
        } catch (Exception e) {
            // Expected, as connecting to a closed socket raises as exception
            assertNotNull(e);
            return;
        }
        assertTrue(false);
    }

    public void testServerClosedAndCloseAgainBranch() {
        SocketServer server = establishServer();
        server.close();
        server.close();
        try {
            Socket socket = new Socket(address, portNumber);
        } catch (Exception e) {
            // Expected, as connecting to a closed socket raises as exception
            assertNotNull(e);
            return;
        }
        assertTrue(false);

    }

}
