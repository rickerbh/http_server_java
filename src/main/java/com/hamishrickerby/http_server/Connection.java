package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 5/09/2016.
 */
public class Connection {
    private ByteReader source;
    private ByteWriter destination;

    public Connection(ByteReader source, ByteWriter destination) {
        this.source = source;
        this.destination = destination;
    }

    byte[] read() {
        return source.read();
    }

    void write(byte[] bytes) {
        destination.write(bytes);
    }
}
