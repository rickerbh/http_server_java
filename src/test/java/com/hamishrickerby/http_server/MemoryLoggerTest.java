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

    public void testLoggerFlushes() {
        Logger logger = new MemoryLogger();
        logger.log("Log this line");
        logger.flush();
        assertEquals("", logger.read());
    }
}
