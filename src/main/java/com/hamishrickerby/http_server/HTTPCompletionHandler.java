package com.hamishrickerby.http_server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class HTTPCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    FileDirectoryServer fileDirectoryServer;

    public HTTPCompletionHandler(String rootDirectory) {
        fileDirectoryServer = new FileDirectoryServer(rootDirectory);
    }

    @Override
    public void completed(AsynchronousSocketChannel ch, Void attachment) {
        byte[] requestBytes = extractRequestBytes(ch);
        Request request = new Request(new String(requestBytes));
        List<String> listing = fileDirectoryServer.get(request.getPath());
        StringBuilder responseBuilder = new StringBuilder("HTTP/1.1 200 OK\n\n");
        responseBuilder.append("Directory listing for " + request.getPath() + "\n\n");
        for (String filename : listing) {
            responseBuilder.append(filename + "\n");
        }
        sendResponse(ch, responseBuilder.toString().getBytes());
        closeChannel(ch);
    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }

    private byte[] extractRequestBytes(AsynchronousSocketChannel ch) {
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        byte[] requestBytes = null;
        try {
            int bytesRead = ch.read(buffer).get(20, TimeUnit.SECONDS);
            requestBytes = new byte[bytesRead];

            if (bytesRead > 0 && buffer.position() > 2) {
                buffer.flip();
                buffer.get(requestBytes, 0, bytesRead);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return requestBytes;
        }
    }

    private void sendResponse(AsynchronousSocketChannel ch, byte[] responseBytes) {
        ch.write(ByteBuffer.wrap(responseBytes));
    }

    private void closeChannel(AsynchronousSocketChannel ch) {
        if (ch.isOpen()) {
            try {
                ch.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
