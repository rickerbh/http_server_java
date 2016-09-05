package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.Connection;
import com.hamishrickerby.http_server.responses.ResponseCoordinator;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class FakeResponseCoordinator implements ResponseCoordinator {

    boolean calledRun = false;

    public void marshalResponse(Connection connection) {
        calledRun = true;
    }

    public boolean didCallRun() {
        return calledRun;
    }

}
