package com.hamishrickerby.http_server;

import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class Request {
    private static String PATH_TRAILING_SYMBOL = "/";
    private static String CRLF = "\r\n";
    String requestString;
    Method method;
    String path;
    Parameters parameters;
    String version;
    Headers headers;

    Map<String, String> data;
    byte[] dataContent;

    public Request(String inputText) {
        requestString = inputText;
        Scanner s = new Scanner(inputText);
        try {
            method = Method.valueOf(s.next());
        } catch (IllegalArgumentException e) {
            method = Method.UNKNOWN;
        }

        parameters = new Parameters();
        parsePathAndParameters(Paths.get(s.next()).normalize().toString());

        version = s.next();

        // Move the scanner to the next line if there is one for headers/data.
        if (s.hasNextLine()) {
            s.nextLine();
        }

        headers = new Headers();
        parseOutHeaders(s);

        data = new HashMap<>();
        parseOutData(s);
    }

    private void parsePathAndParameters(String pathParams) {
        String[] splitPathParams = pathParams.split("\\?");
        path = splitPathParams[0];
        if (splitPathParams.length > 1) {
            parameters.parse(splitPathParams[1]);
        }
    }

    private void parseOutHeaders(Scanner scanner) {
        StringBuilder headerBuilder = new StringBuilder();
        boolean inHeaders = true;
        while (inHeaders && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                inHeaders = false;
            } else {
                headerBuilder
                        .append(line)
                        .append(CRLF);
            }
        }
        headers.parse(headerBuilder.toString());
    }

    private void parseOutData(Scanner scanner) {
        Pattern restOfContentPattern = Pattern.compile("(.*|\\s+)", Pattern.MULTILINE);
        if (!scanner.hasNext(restOfContentPattern)) {
            return;
        }
        scanner.useDelimiter(CRLF + CRLF);

        String content = scanner.next().trim();
        dataContent = content.getBytes();

        Scanner dataScanner = new Scanner(content);
        dataScanner.useDelimiter("&");

        while (dataScanner.hasNext()) {
            String[] parts = dataScanner.next().split("=");
            if (parts.length >= 2) {
                data.put(parts[0], parts[1]);
            }
        }
    }

    public Parameters getParameters() {
        return parameters;
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

    public String readData(String key) {
        return data.getOrDefault(key, "");
    }

    public Set<String> dataKeys() {
        return data.keySet();
    }

    public byte[] getDataContent() {
        return dataContent;
    }
}
