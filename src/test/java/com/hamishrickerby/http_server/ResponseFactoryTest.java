package com.hamishrickerby.http_server;

import junit.framework.TestCase;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class ResponseFactoryTest extends TestCase {
    public void testResponseIsDirectoryListingResponse() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources");
        Request request = new Request("GET / HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof DirectoryListingResponse));
    }

    public void testResponseIsFileListingResponse() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources");
        Request request = new Request("GET /test.html HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FileContentsResponse));
    }
}
