package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 10/09/2016.
 */
public class MemoryLogger implements Logger {

    StringBuilder logs;

    public MemoryLogger() {
        flush();
    }

    @Override
    public void log(String message) {
        logs.append(message).append("\n");
    }

    @Override
    public String read() {
        return logs.toString();
    }

    @Override
    public void flush() {
        logs = new StringBuilder();
    }

}
