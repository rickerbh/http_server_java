package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 10/09/2016.
 */
public class MemoryLogger implements Logger {

    StringBuilder logs;

    public MemoryLogger() {
        logs = new StringBuilder();
    }

    @Override
    public void log(String message) {
        logs.append(message).append("\n");
    }

    @Override
    public String read() {
        return logs.toString();
    }

    public static Boolean respondsTo(Request request) {
        return request.getPath().equalsIgnoreCase("/logs");
    }
}
