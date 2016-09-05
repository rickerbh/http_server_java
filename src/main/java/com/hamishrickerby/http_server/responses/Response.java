package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by rickerbh on 17/08/2016.
 */
public abstract class Response {
    Request request;
    private static String CR_LF = "\r\n";

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
                .append(CR_LF)
                .append(String.join(CR_LF, headers()))
                .append(CR_LF);
        return b.toString();
    }

    protected int code() {
        return 200;
    }

    protected String reason() {
        return "OK";
    }

    protected String[] headers() {
        return new String[0];
    }

    abstract protected byte[] body();

}
