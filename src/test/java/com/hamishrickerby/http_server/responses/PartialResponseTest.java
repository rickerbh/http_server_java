package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 6/09/2016.
 */
public class PartialResponseTest extends TestCase {
    String rootDir = "./src/test/resources";

    public void testResponseCodeIs206() {
        Request request = new RequestBuilder().setPath("/subdirectory/file.txt").toRequest();
        Response response = new PartialFileContentsResponse(request, rootDir, null);
        assertEquals(206, response.code());
    }

    public void testBodySupportsStartAndEndRange() {
        String bodyString = rangeBody("0", "3");
        assertEquals("This", bodyString);
    }

    private String rangeBody(String start, String end) {
        String headerString = new StringBuilder()
                .append("Range: bytes=")
                .append(start)
                .append("-")
                .append(end)
                .toString();

        Request request = new RequestBuilder()
                .setPath("/subdirectory/file.txt")
                .addHeader(headerString).toRequest();
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
        Request request = new RequestBuilder()
                .setPath("/subdirectory/file.txt")
                .addHeader("Range: bytes=0-10")
                .toRequest();
        assertTrue(PartialFileContentsResponse.isValidRangeRequest(request, rootDir, null));

        request = new RequestBuilder()
                .setPath("/no-exist")
                .addHeader("Range: bytes=0-10")
                .toRequest();

        assertFalse(PartialFileContentsResponse.isValidRangeRequest(request, rootDir, null));

        request = new RequestBuilder()
                .setPath("/subdirectory/file.txt")
                .toRequest();
        assertFalse(PartialFileContentsResponse.isValidRangeRequest(request, rootDir, null));
    }

}
