package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.auth.Authenticator;
import com.hamishrickerby.http_server.responses.Response;
import com.hamishrickerby.http_server.responses.ResponseCoordinator;
import com.hamishrickerby.http_server.responses.ResponseFactory;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class HTTPResponseCoordinator implements ResponseCoordinator {
    ResponseFactory responseFactory;
    Logger logger;

    public HTTPResponseCoordinator(String rootDirectory, Logger logger) {
        responseFactory = new ResponseFactory(rootDirectory, logger);
        responseFactory.setFormStore(new MemoryFormStore());
        responseFactory.setFilePatchCache(new FilePatchCache());
        this.logger = logger;
    }

    public void marshalResponse(Connection connection) {
        String requestText = extractRequestText(connection);

        Request request = new Request(requestText);
        logRequest(request);
        Response response = responseFactory.makeResponse(request);

        sendResponse(connection, response);
    }

    private void logRequest(Request request) {
        if (logger == null) {
            return;
        }
        logger.log(request.getRequestString());
    }

    private String extractRequestText(Connection connection) {
        return new String(connection.read());
    }

    private void sendResponse(Connection connection, Response response) {
        connection.write(response.getBytes());
    }

    public void setAuthenticator(Authenticator authenticator) {
        responseFactory.setAuthenticator(authenticator);
    }

}
