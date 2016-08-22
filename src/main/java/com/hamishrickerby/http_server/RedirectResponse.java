package com.hamishrickerby.http_server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rickerbh on 18/08/2016.
 */
public class RedirectResponse extends Response {

    Map<String, String> redirects;

    public RedirectResponse(Request request) {
        super(request);
        redirects = RedirectResponse.defaultRedirects();
    }

    public static Map<String, String> defaultRedirects() {
        Map<String, String> redirects = new HashMap<>();
        redirects.put("/redirect", "http://localhost:5000/");
        return redirects;
    }

    public void setRedirect(String source, String destination) {
        redirects.put(source, destination);
    }

    @Override
    protected int code() {
        return 302;
    }

    @Override
    protected String[] headers() {
        Headers headers = new Headers();
        String redirectLocation = redirects.getOrDefault(request.getPath(), "/");
        headers.put("Location", redirectLocation);
        return headers.list().stream().toArray(String[]::new);
    }

    protected byte[] body() {
        return new byte[0];
    }

    public static Boolean existsFor(String location) {
        return RedirectResponse.defaultRedirects().containsKey(location);
    }
}
