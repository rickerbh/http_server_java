package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class AppArgumentsTest extends TestCase {
    public void testEmptyInputReturnsValidArguments() {
        String[] inputArgs = new String[0];
        AppArguments args = new AppArguments(inputArgs);
        assertEquals(0, args.size());
    }

    public void testArgumentsAreRetrieved() {
        String[] inputArgs = {"-p", "3000", "-d", "hello"};
        AppArguments args = new AppArguments(inputArgs);
        assertEquals("3000", args.get("-p"));
        assertEquals("hello", args.get("-d"));
    }

    public void testIncompleteAndNotPresentArgumentsFailToGetRead() {
        String[] inputArgs = {"-p", "3000", "-d"};
        AppArguments args = new AppArguments(inputArgs);
        assertEquals("3000", args.get("-p"));
        assertNull(args.get("-d"));
        assertNull(args.get("i-don't-exist"));
    }

}
