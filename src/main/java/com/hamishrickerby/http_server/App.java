package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class App {
    public static void main(String[] args) {
        AppArguments arguments = new AppArguments(args);
        Integer port = Integer.parseInt(arguments.getOrDefault("-p", "5000"));
        try {
            SocketServer server = new SocketServer(port);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
