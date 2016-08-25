package com.hamishrickerby.http_server.mocks;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class MockAsynchronousSocketChannel extends AsynchronousSocketChannel {
    public void setReadData(String text) {
        this.readData = ByteBuffer.wrap(text.getBytes());
    }

    private ByteBuffer readData;

    public ByteBuffer getWrittenData() {
        return writtenData;
    }

    private ByteBuffer writtenData;

    public MockAsynchronousSocketChannel(AsynchronousChannelProvider provider) {
        super(provider);
        writtenData = ByteBuffer.wrap("".getBytes());
    }

    @Override
    public AsynchronousSocketChannel bind(SocketAddress local) throws IOException {
        return null;
    }

    @Override
    public <T> AsynchronousSocketChannel setOption(SocketOption<T> name, T value) throws IOException {
        return null;
    }

    @Override
    public AsynchronousSocketChannel shutdownInput() throws IOException {
        return null;
    }

    @Override
    public AsynchronousSocketChannel shutdownOutput() throws IOException {
        return null;
    }

    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return null;
    }

    @Override
    public <A> void connect(SocketAddress remote, A attachment, CompletionHandler<Void, ? super A> handler) {

    }

    @Override
    public Future<Void> connect(SocketAddress remote) {
        return null;
    }

    @Override
    public <A> void read(ByteBuffer dst, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {

    }

    @Override
    public Future<Integer> read(ByteBuffer dst) {
        dst.put(readData);
        int leftToRead = readData.limit() - readData.arrayOffset();
        return CompletableFuture.completedFuture(leftToRead);
    }

    @Override
    public <A> void read(ByteBuffer[] dsts, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long, ? super A> handler) {

    }

    @Override
    public <A> void write(ByteBuffer src, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {

    }

    @Override
    public Future<Integer> write(ByteBuffer src) {
        this.writtenData = src.duplicate();
        return CompletableFuture.completedFuture(0);
    }

    @Override
    public <A> void write(ByteBuffer[] srcs, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long, ? super A> handler) {

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
