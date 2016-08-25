package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class HTTPHandler {
    ResponseFactory responseFactory;

    public HTTPHandler(String rootDirectory) {
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
