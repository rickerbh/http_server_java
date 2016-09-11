package com.hamishrickerby.http_server;

import com.hamishrickerby.http_server.auth.Authenticator;
import com.hamishrickerby.http_server.auth.RequestAuthenticator;

/**
 * Created by rickerbh on 15/08/2016.
 */
public class App {
    private static String DEFAULT_PORT = "5000";

    public static void main(String[] args) {
        AppArguments arguments = new AppArguments(args);
        Integer port = Integer.parseInt(arguments.getOrDefault("-p", DEFAULT_PORT));
        String rootDirectory = arguments.get("-d");
        if (rootDirectory == null) {
            System.err.println("Must provide a root directory with a -d argument.");
            System.exit(1);
        }
        Server server = null;

        try {
            server = new AsynchronousSocketServer(port);

            Logger logger = new MemoryLogger();
            HTTPResponseCoordinator coordinator = new HTTPResponseCoordinator(rootDirectory, logger);
            coordinator.setAuthenticator(getAuthenticator());
            server.setResponseCoordinator(coordinator);

            server.start();

            System.out.println("Mounted " + rootDirectory + " as the directory to serve from.");
            System.out.println("Server started on port " + port);

            server.awaitTermination(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

    public static Authenticator getAuthenticator() {
        RequestAuthenticator authenticator = new RequestAuthenticator();
        authenticator.addRoute("/logs");
        authenticator.addCredentials("admin", "hunter2");
        return authenticator;
    }
}
