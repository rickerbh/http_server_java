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

    public void marshalResponse(Connection connection) {
        String requestText = extractRequestText(connection);

        Request request = new Request(requestText);
        Response response = responseFactory.makeResponse(request);

        sendResponse(connection, response);
    }

    private String extractRequestText(Connection connection) {
        return new String(connection.read());
    }

    private void sendResponse(Connection connection, Response response) {
        connection.write(response.getBytes());
    }
}
