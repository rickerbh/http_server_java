package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.helpers.AsynchronousSocketServerTimeoutRunner;
import com.hamishrickerby.http_server.responses.ResponseCoordinator;
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
public class AsynchronousSocketServerTest extends TestCase {

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
        server.stop();
    }

    private SocketServer establishServer() {
        SocketServer server = null;
        try {
            server = new AsynchronousSocketServer();
            server.bind(portNumber);

            ResponseCoordinator coordinator = new HTTPResponseCoordinator("");
            server.setResponseCoordinator(coordinator);

        } catch (IOException e) {
            fail("Could not instantiate or bind AsynchronousSocketServer.");
        }
        server.start();
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
            server.stop();
        }
    }

    public void testServerCloses() {
        SocketServer server = establishServer();
        server.stop();
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
        server.stop();
        server.stop();
        try {
            Socket socket = new Socket(address, portNumber);
        } catch (Exception e) {
            // Expected, as connecting to a closed socket raises as exception
            assertNotNull(e);
            return;
        }
        assertTrue(false);

    }

    public void testServerExhibitsSurvivingAndInterruptedBehaviour() {
        AsynchronousSocketServerTimeoutRunner runner = new AsynchronousSocketServerTimeoutRunner();
        runner.timeToLive = 2L;
        Thread t = new Thread(runner);
        t.start();
        try {
            t.join(1000);
        } catch (InterruptedException e) {
            fail("Socket server interrupted in test.");
        }
        assertTrue(t.isAlive());
        assertFalse(t.isInterrupted());
        t.interrupt();
        assertTrue(t.isInterrupted());
    }

    public void testServerExhibitsTimeoutBehaviour() {
        AsynchronousSocketServerTimeoutRunner runner = new AsynchronousSocketServerTimeoutRunner();
        runner.timeToLive = 1L;
        Thread t = new Thread(runner);
        t.start();
        try {
            t.join(1200);
        } catch (InterruptedException e) {
            fail("Socket server interrupted in test.");
        }
        assertFalse(t.isAlive());
        assertFalse(t.isInterrupted());
    }

}
