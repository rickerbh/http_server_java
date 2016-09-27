package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Logger;
import com.hamishrickerby.http_server.MemoryLogger;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;

/**
 * Created by rickerbh on 11/09/2016.
 */
public class LogsResponseTest extends TestCase {
    public void testResponseIs200() {
        Logger logger = new MemoryLogger();
        Request request = new RequestBuilder().setPath("/anything").toRequest();
        LogsResponse response = new LogsResponse(request, logger);
        assertResponseCodeEquals("200", response);
    }

    public void testResponseEndpointAcceptance() {
        Request request = new RequestBuilder().setPath("/logs").toRequest();
        assertTrue(LogsResponse.respondsTo(request));
        request = new RequestBuilder().setPath("/no-logs-here").toRequest();
        assertFalse(LogsResponse.respondsTo(request));
    }

    public void testResponseBodyContainsLoggingStatements() {
        Logger logger = new MemoryLogger();
        String logText = "Hello";
        logger.log(logText);
        Request request = new RequestBuilder().setPath("/logs").toRequest();
        LogsResponse response = new LogsResponse(request, logger);
        String responseText = new String(response.getBytes());
        assertTrue(responseText.contains(logText));
    }
}
