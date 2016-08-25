package com.hamishrickerby.http_server.mocks;

import com.hamishrickerby.http_server.ByteReader;

/**
 * Created by rickerbh on 25/08/2016.
 */
public class FakeReader implements ByteReader {

    private byte[] byteData;

    public FakeReader(String data) {
        byteData = data.getBytes();
    }

    @Override
    public byte[] read() {
        return byteData;
    }
}
