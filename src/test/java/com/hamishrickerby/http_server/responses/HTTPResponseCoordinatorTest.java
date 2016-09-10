package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.*;
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

    public void testNullLoggerDoesntCauseException() {
        ResponseCoordinator handler = new HTTPResponseCoordinator("./src/test/resources", null);
        ByteReader reader = new FakeReader("GET / HTTP/1.1");
        FakeWriter writer = new FakeWriter();
        Connection connection = new Connection(reader, writer);
        try {
            handler.marshalResponse(connection);
        } catch (Exception e) {
            fail("Exception in marshalling response");
        }
    }
}
