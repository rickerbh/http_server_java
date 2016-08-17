package com.hamishrickerby.http_server;

import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class Request {
    String method;
    String path;
    String version;

    public Request(String inputText) {
        Scanner s = new Scanner(inputText);
        method = s.next();
        path = Paths.get(s.next()).normalize().toString();
        version = s.next();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public String pathWithTrailingSlash() {
        if (getPath().endsWith("/")) {
            return getPath();
        }
        return getPath() + "/";
    }
}
