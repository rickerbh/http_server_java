package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FormStore;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;

/**
 * Created by rickerbh on 12/09/2016.
 */
public class FormResponse extends Response {
    FormStore store;

    public FormResponse(Request request, FormStore store) {
        super(request);
        this.store = store;

        saveDataIfNeeded();
    }

    private void saveDataIfNeeded() {
        if (request.getMethod() != Method.POST) {
            return;
        }
        for (String key : request.dataKeys()) {
            store.write(key, request.readData(key));
        }
    }

    public static Boolean respondsTo(Request request) {
        return request.getPath().equalsIgnoreCase("/form");
    }

    @Override
    protected byte[] body() {
        switch (request.getMethod()) {
            default:
                return new byte[0];
        }
    }
}
