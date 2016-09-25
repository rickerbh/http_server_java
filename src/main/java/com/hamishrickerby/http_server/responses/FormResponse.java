package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FormStore;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rickerbh on 12/09/2016.
 */
public class FormResponse extends Response {
    FormStore store;

    public FormResponse(Request request, FormStore store) {
        super(request);

        Method[] supportedMethods = {
                Method.GET,
                Method.OPTIONS,
                Method.POST,
                Method.PUT};
        this.supportedMethods = Arrays.asList(supportedMethods);

        this.store = store;

        saveDataIfNeeded();
    }

    private void saveDataIfNeeded() {
        if (!updatingMethods().contains(request.getMethod().name())) {
            return;
        }
        for (String key : request.dataKeys()) {
            store.write(key, request.readData(key));
        }
    }

    private List<String> updatingMethods() {
        String[] methods = {Method.POST.name(), Method.PUT.name()};
        return Arrays.asList(methods);
    }

    public static Boolean respondsTo(Request request) {
        return request.getPath().equalsIgnoreCase("/form");
    }

    @Override
    protected byte[] body() {
        switch (request.getMethod()) {
            case GET:
                return getFormData();
            default:
                return new byte[0];
        }
    }

    private byte[] getFormData() {
        StringBuilder builder = new StringBuilder();
        for (String key : store.keys()) {
            builder.append(MessageFormat.format("{0}={1}\r\n", key, store.read(key)));
        }
        return builder.toString().getBytes();
    }
}
