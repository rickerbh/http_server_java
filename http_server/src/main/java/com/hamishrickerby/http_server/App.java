package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class App {
    public static void main(String[] args) {
        SocketServer server = new SocketServer(5000);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
