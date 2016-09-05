package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.ByteReader;
import com.hamishrickerby.http_server.ByteWriter;

/**
 * Created by rickerbh on 29/08/2016.
 */
public interface ResponseCoordinator {
    void run(ByteReader reader, ByteWriter writer);
}
