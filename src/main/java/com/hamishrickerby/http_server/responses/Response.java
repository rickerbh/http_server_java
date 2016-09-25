package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Headers;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rickerbh on 17/08/2016.
 */
public abstract class Response {
    Request request;
    private static String CR_LF = "\r\n";
    protected List<Method> supportedMethods;

    public Response(Request request) {
        this.request = request;
        supportedMethods = new ArrayList<>();
        supportedMethods.add(Method.HEAD);
        supportedMethods.add(Method.OPTIONS);
    }

    public byte[] getBytes() {
        byte[] status = statusLine().getBytes();
        byte[] body;

        switch (request.getMethod()) {
            case HEAD:
            case OPTIONS:
                body = new byte[0];
                break;
            default:
                body = body();
        }

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
        switch (request.getMethod()) {
            case OPTIONS:
                Headers headers = new Headers();
                List<String> methods = getSupportedMethods()
                        .stream()
                        .map(Method::name)
                        .collect(Collectors.toList());
                headers.put("Allow", String.join(",", methods));
                return headers.list().stream().toArray(String[]::new);
            default:
                return new String[0];
        }
    }

    abstract protected byte[] body();

    protected List<Method> getSupportedMethods() {
        return supportedMethods;
    }
}
