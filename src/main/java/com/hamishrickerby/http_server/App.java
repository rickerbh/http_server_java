package com.hamishrickerby.http_server;

import java.util.concurrent.TimeUnit;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class App {
    public static void main(String[] args) {
        AppArguments arguments = new AppArguments(args);
        Integer port = Integer.parseInt(arguments.getOrDefault("-p", "5000"));
        String rootDirectory = arguments.get("-d");
        if (rootDirectory == null) {
            System.err.println("Must provide a root directory with a -d argument.");
            System.exit(1);
        }
        SocketServer server = null;

        try {
            server = new SocketServer(port, rootDirectory);
            System.out.println("Mounted " + rootDirectory + " as the directory to serve from.");
            System.out.println("Server started on port " + port);
            server.group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.close();
        }
    }
}
