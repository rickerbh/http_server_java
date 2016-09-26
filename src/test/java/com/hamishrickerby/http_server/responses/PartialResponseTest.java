package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 6/09/2016.
 */
public class PartialResponseTest extends TestCase {
    String rootDir = "./src/test/resources";

    public void testResponseCodeIs206() {
        Response response = new PartialFileContentsResponse(new Request("GET /subdirectory/file.txt HTTP/1.1"), rootDir, null);
        assertEquals(206, response.code());
    }

    public void testBodySupportsStartAndEndRange() {
        String bodyString = rangeBody("0", "3");
        assertEquals("This", bodyString);
    }

    private String rangeBody(String start, String end) {
        String requestString = new StringBuilder()
                .append("GET /subdirectory/file.txt HTTP/1.1\r\n")
                .append("Range: bytes=")
                .append(start)
                .append("-")
                .append(end)
                .append("\r\n")
                .toString();
        Request request = new Request(requestString);
        Response response = new PartialFileContentsResponse(request, rootDir, null);
        return new String(response.body());
    }

    public void testBodySupportsEndRangeOnly() {
        String bodyString = rangeBody("", "6");
        assertEquals("file.\n", bodyString);
    }

    public void testBodySupportsStartRangeOnly() {
        String bodyString = rangeBody("11", "");
        assertEquals("example text file.\n", bodyString);
    }

    public void testResponseValidity() {
        String requestString = new StringBuilder()
                .append("GET /subdirectory/file.txt HTTP/1.1\r\n")
                .append("Range: bytes=0-10\r\n")
                .toString();
        Request request = new Request(requestString);
        assertTrue(PartialFileContentsResponse.isValidRangeRequest(request, rootDir, null));

        requestString = new StringBuilder()
                .append("GET /no-exist HTTP/1.1\r\n")
                .append("Range: bytes=0-10\r\n")
                .toString();
        request = new Request(requestString);
        assertFalse(PartialFileContentsResponse.isValidRangeRequest(request, rootDir, null));

        requestString = new StringBuilder()
                .append("GET /subdirectory/file.txt HTTP/1.1\r\n")
                .toString();
        request = new Request(requestString);
        assertFalse(PartialFileContentsResponse.isValidRangeRequest(request, rootDir, null));
    }

}
