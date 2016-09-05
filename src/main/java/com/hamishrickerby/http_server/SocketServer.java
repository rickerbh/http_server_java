package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.responses.ResponseCoordinator;

import java.io.IOException;

/**
 * Created by rickerbh on 14/08/2016.
 */
public interface SocketServer {
    void bind(int portNumber) throws IOException;

    void start();

    void stop();

    void awaitTermination(Long secondsToSurviveFor);

    void setResponseCoordinator(ResponseCoordinator coordinator);
}
