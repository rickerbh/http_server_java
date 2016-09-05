package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.ByteReader;
import com.hamishrickerby.http_server.Connection;
import com.hamishrickerby.http_server.HTTPResponseCoordinator;
import com.hamishrickerby.http_server.mocks.FakeReader;
import com.hamishrickerby.http_server.mocks.FakeWriter;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class HTTPResponseCoordinatorTest extends TestCase {
    public void testRequestGivesAResponse() {
        ByteReader reader = new FakeReader("GET / HTTP/1.1");
        FakeWriter writer = new FakeWriter();
        ResponseCoordinator handler = new HTTPResponseCoordinator("./src/test/resources");
        Connection connection = new Connection(reader, writer);
        handler.marshalResponse(connection);
        assertNotSame(new byte[0], writer.readWrittenBytes());
    }
}
