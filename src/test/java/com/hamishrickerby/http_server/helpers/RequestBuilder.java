package com.hamishrickerby.http_server.helpers;

import com.hamishrickerby.http_server.Headers;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;

/**
 * Created by rickerbh on 27/09/2016.
 */
public class RequestBuilder {

    static String CRLF = "\r\n";
    Method method;
    String path;
    Headers headers;
    String content;

    public RequestBuilder() {
        method = Method.GET;
        path = "/";
        headers = new Headers();
    }

    public RequestBuilder setMethod(Method method) {
        this.method = method;
        return this;
    }

    public RequestBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder addHeader(String header) {
        headers.parse(header);
        return this;
    }

    public RequestBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public Request toRequest() {
        StringBuilder resultBuilder = new StringBuilder()
                .append(method.name())
                .append(" ")
                .append(path)
                .append(" ")
                .append("HTTP/1.1")
                .append(CRLF);

        if (headers.list().size() > 0) {
            for (String line : headers.list()) {
                resultBuilder.append(line);
            }
            resultBuilder.append(CRLF);
        }
        resultBuilder.append(CRLF);

        if (content != null) {
            resultBuilder.append(content);
            resultBuilder.append(CRLF);
        }

        return new Request(resultBuilder.toString());
    }
}
