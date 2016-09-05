package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.responses.ResponseCoordinator;

/**
 * Created by rickerbh on 14/08/2016.
 */
public interface Server {
    void start();

    void stop();

    void awaitTermination(Long secondsToSurviveFor);

    void setResponseCoordinator(ResponseCoordinator coordinator);
}
