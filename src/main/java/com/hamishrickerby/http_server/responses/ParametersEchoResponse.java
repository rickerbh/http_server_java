package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.Parameters;
import com.hamishrickerby.http_server.Request;

import java.text.MessageFormat;

/**
 * Created by rickerbh on 25/09/2016.
 */
public class ParametersEchoResponse extends Response {
    public ParametersEchoResponse(Request request) {
        super(request);
    }

    @Override
    protected byte[] body() {
        Parameters parameters = request.getParameters();
        StringBuilder builder = new StringBuilder();
        for (String key : parameters.keys()) {
            builder.append(MessageFormat.format("{0} = {1}\r\n", key, parameters.get(key)));
        }
        return builder.toString().getBytes();
    }

    public static boolean respondsTo(Request request) {
        return request.getPath().equals("/parameters");
    }
}
