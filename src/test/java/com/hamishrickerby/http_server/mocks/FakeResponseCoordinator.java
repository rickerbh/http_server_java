package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.ByteReader;
import com.hamishrickerby.http_server.ByteWriter;
import com.hamishrickerby.http_server.responses.ResponseCoordinator;

/**
 * Created by rickerbh on 29/08/2016.
 */
public class FakeResponseCoordinator implements ResponseCoordinator {

    boolean calledRun = false;

    public void marshalResponse(ByteReader reader, ByteWriter writer) {
        calledRun = true;
    }

    public boolean didCallRun() {
        return calledRun;
    }

}
