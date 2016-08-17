package com.hamishrickerby.http_server.mocks;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class MockAsynchronousServerSocketChannel extends AsynchronousServerSocketChannel {
    public MockAsynchronousServerSocketChannel(AsynchronousChannelProvider provider) {
        super(provider);
    }

    @Override
    public AsynchronousServerSocketChannel bind(SocketAddress local, int backlog) throws IOException {
        return null;
    }

    @Override
    public <T> AsynchronousServerSocketChannel setOption(SocketOption<T> name, T value) throws IOException {
        return null;
    }

    @Override
    public <A> void accept(A attachment, CompletionHandler<AsynchronousSocketChannel, ? super A> handler) {

    }

    @Override
    public Future<AsynchronousSocketChannel> accept() {
        return null;
    }

    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public <T> T getOption(SocketOption<T> name) throws IOException {
        return null;
    }

    @Override
    public Set<SocketOption<?>> supportedOptions() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }
}
