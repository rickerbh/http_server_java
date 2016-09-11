package com.hamishrickerby.http_server;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class Request {
    private static String PATH_TRAILING_SYMBOL = "/";
    String requestString;
    Method method;
    String path;
    String version;
    Headers headers;

    public Request(String inputText) {
        requestString = inputText;
        Scanner s = new Scanner(inputText);
        method = Method.valueOf(s.next());
        path = Paths.get(s.next()).normalize().toString();
        version = s.next();

        headers = new Headers();
        parseOutHeaders(s);
    }

    private void parseOutHeaders(Scanner scanner) {
        StringBuilder headerBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            headerBuilder
                    .append(scanner.nextLine())
                    .append("\r\n");
        }
        headers.parse(headerBuilder.toString());
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public String pathWithTrailingSlash() {
        if (getPath().endsWith(PATH_TRAILING_SYMBOL)) {
            return getPath();
        }
        return getPath() + PATH_TRAILING_SYMBOL;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getRequestString() {
        return requestString;
    }

    public String getAuthUser() {
        return getAuthenticationFields()[0];
    }

    private String[] getAuthenticationFields() {
        String[] emptyAuth = {"", ""};
        String authRequestText = getHeader("Authorization");
        if (authRequestText.isEmpty()) {
            return emptyAuth;
        }

        authRequestText = trimAuthMethod(authRequestText);
        authRequestText = decodeAuthCredentials(authRequestText);

        return authRequestText.split(":");
    }

    private String trimAuthMethod(String text) {
        return text.replaceFirst("Basic ", "");
    }

    private String decodeAuthCredentials(String credentials) {
        return new String(Base64.getDecoder().decode(credentials));
    }


    public String getAuthPassword() {
        return getAuthenticationFields()[1];
    }

}
