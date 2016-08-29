package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 29/08/2016.
 */
public interface ResponseCoordinator {
    void run(ByteReader reader, ByteWriter writer);
}
