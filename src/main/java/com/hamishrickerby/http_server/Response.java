package com.hamishrickerby.http_server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by rickerbh on 17/08/2016.
 */
public abstract class Response {
    Request request;

    public Response(Request request) {
        this.request = request;
    }

    public byte[] getBytes() {
        byte[] status = statusLine().getBytes();
        byte[] body = body();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(status);
            outputStream.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return outputStream.toByteArray();
        }
    }

    private String statusLine() {
        StringBuilder b = new StringBuilder();
        b.append(request.getVersion())
                .append(" ")
                .append(code())
                .append(" ")
                .append(reason())
                .append("\r\n\r\n");
        return b.toString();
    }

    protected int code() {
        return 200;
    }

    protected String reason() {
        return "OK";
    }

    abstract protected byte[] body();

}
