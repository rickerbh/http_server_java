package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 6/09/2016.
 */
public class RequestRangeTest extends TestCase {

    public void testRangeWithStartAndEnd() {
        RequestRange range = new RequestRange("0-6", 10);
        assertEquals(0, range.getStart());
        assertEquals(7, range.getEnd());
    }

    public void testRangeWithStartOnly() {
        RequestRange range = new RequestRange("3-", 10);
        assertEquals(3, range.getStart());
        assertEquals(10, range.getEnd());
    }

    public void testRangeWithEndOnly() {
        RequestRange range = new RequestRange("-6", 10);
        assertEquals(4, range.getStart());
        assertEquals(10, range.getEnd());
    }

}
