package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class ParametersTest extends TestCase {
    public void testEmptyParametersAreEmpty() {
        Parameters parameters = new Parameters();
        assertEquals(0, parameters.keys().size());
    }

    public void testEmptyParametersParseWithoutException() {
        Parameters parameters = new Parameters();
        parameters.parse("");
        assertEquals("", parameters.get("non-existant-key"));
    }

    public void testParametersParseAndRetrieve() {
        Parameters parameters = new Parameters();
        parameters.parse("param1=value1&param2=ham!%24%5E%20more");
        assertEquals("value1", parameters.get("param1"));
        assertEquals("ham!$^ more", parameters.get("param2"));
    }

}
