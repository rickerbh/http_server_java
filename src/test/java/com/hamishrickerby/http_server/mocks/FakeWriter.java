package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.ByteWriter;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class FakeWriter implements ByteWriter {

    private byte[] byteData;

    @Override
    public void write(byte[] bytes) {
        byteData = bytes;
    }

    public byte[] readWrittenBytes() {
        return byteData;
    }
}
