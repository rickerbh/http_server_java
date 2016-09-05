package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Connection;

/**
 * Created by rickerbh on 29/08/2016.
 */
public interface ResponseCoordinator {
    void marshalResponse(Connection connection);
}
