package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 10/09/2016.
 */
public class MemoryLoggerTest extends TestCase {

    public void testLoggerSupportsReadingAndWriting() {
        Logger logger = new MemoryLogger();
        assertEquals("", logger.read());

        logger.log("Log this line");
        assertEquals("Log this line\n", logger.read());

        logger.log("Second line");
        assertEquals("Log this line\nSecond line\n", logger.read());
    }

    public void testLoggerEndpointResponseCheck() {
        Request request = new Request("GET /logs HTTP/1.1");
        assertTrue(MemoryLogger.respondsTo(request));

        request = new Request("GET /no-logs-here HTTP/1.1");
        assertFalse(MemoryLogger.respondsTo(request));
    }
}
