package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.responses.Response;
import com.hamishrickerby.http_server.responses.ResponseCoordinator;
import com.hamishrickerby.http_server.responses.ResponseFactory;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class HTTPResponseCoordinator implements ResponseCoordinator {
    ResponseFactory responseFactory;

    public HTTPResponseCoordinator(String rootDirectory) {
        responseFactory = new ResponseFactory(rootDirectory);
    }

    public void run(ByteReader reader, ByteWriter writer) {
        String requestText = extractRequestText(reader);

        Request request = new Request(requestText);
        Response response = responseFactory.makeResponse(request);

        sendResponse(writer, response);
    }

    private String extractRequestText(ByteReader reader) {
        return new String(reader.read());
    }

    private void sendResponse(ByteWriter writer, Response response) {
        writer.write(response.getBytes());
    }
}
