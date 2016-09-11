package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.*;
import com.hamishrickerby.http_server.auth.RequestAuthenticator;
import com.hamishrickerby.http_server.mocks.FakeReader;
import com.hamishrickerby.http_server.mocks.FakeWriter;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class HTTPResponseCoordinatorTest extends TestCase {
    public void testRequestGivesAResponse() {
        Logger logger = new MemoryLogger();
        ByteReader reader = new FakeReader("GET / HTTP/1.1");
        FakeWriter writer = new FakeWriter();
        ResponseCoordinator handler = new HTTPResponseCoordinator("./src/test/resources", logger);
        Connection connection = new Connection(reader, writer);
        handler.marshalResponse(connection);
        assertNotSame(new byte[0], writer.readWrittenBytes());
    }

    public void testCoordinatorUsesLogger() {
        Logger logger = new MemoryLogger();
        ResponseCoordinator handler = new HTTPResponseCoordinator("./src/test/resources", logger);
        String requestString = "GET / HTTP/1.1";
        ByteReader reader = new FakeReader(requestString);
        FakeWriter writer = new FakeWriter();
        Connection connection = new Connection(reader, writer);
        handler.marshalResponse(connection);
        assertTrue(logger.read().contains(requestString));
    }

    public void testNullLoggerOrAuthenticatorDoesntCauseException() {
        HTTPResponseCoordinator handler = new HTTPResponseCoordinator("./src/test/resources", null);
        handler.setAuthenticator(null);
        ByteReader reader = new FakeReader("GET / HTTP/1.1");
        FakeWriter writer = new FakeWriter();
        Connection connection = new Connection(reader, writer);
        try {
            handler.marshalResponse(connection);
        } catch (Exception e) {
            fail("Exception in marshalling response");
        }
    }

    public void testCoordinatorUsesAuthenticator() {
        HTTPResponseCoordinator handler = new HTTPResponseCoordinator("./src/test/resources", null);
        RequestAuthenticator authenticator = new RequestAuthenticator();
        authenticator.addCredentials("user", "pass");
        authenticator.addRoute("/protected");
        handler.setAuthenticator(authenticator);

        ByteReader reader = new FakeReader("GET /protected HTTP/1.1\r\nAuthorization: Basic dXNlcjpmYWls\r\n");
        FakeWriter writer = new FakeWriter();
        Connection connection = new Connection(reader, writer);
        handler.marshalResponse(connection);
        assertTrue(new String(writer.readWrittenBytes()).trim().contains("HTTP/1.1 401 Unauthorized"));

        reader = new FakeReader("GET /protected HTTP/1.1\r\nAuthorization: Basic dXNlcjpwYXNz\r\n");
        writer = new FakeWriter();
        connection = new Connection(reader, writer);
        handler.marshalResponse(connection);
        assertTrue(new String(writer.readWrittenBytes()).trim().contains("HTTP/1.1 404 Not Found"));
    }

}
