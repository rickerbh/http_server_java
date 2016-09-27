package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FormStore;
import com.hamishrickerby.http_server.MemoryFormStore;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 12/09/2016.
 */
public class FormResponseTest extends TestCase {

    public void testFormPostResponseCode() {
        Method[] methods = {Method.POST, Method.PUT};
        for (Method method : methods) {
            Request request = new RequestBuilder()
                    .setMethod(method)
                    .setPath("/form")
                    .toRequest();
            Response response = new FormResponse(request, null);
            assertResponseCodeEquals("200", response);
        }
    }

    public void testReason() {
        Method[] methods = {Method.POST, Method.PUT};
        for (Method method : methods) {
            Request request = new RequestBuilder()
                    .setMethod(method)
                    .setPath("/form")
                    .toRequest();
            Response response = new FormResponse(request, null);
            assertResponseReasonEquals("OK", response);
        }
    }

    public void testRespondsTo() {
        Method[] methods = {Method.POST, Method.PUT};
        for (Method method : methods) {
            Request request = new RequestBuilder()
                    .setMethod(method)
                    .setPath("/form")
                    .toRequest();
            assertTrue(FormResponse.respondsTo(request));
            request = new Request(method + " /no-form HTTP/1.1");
            assertFalse(FormResponse.respondsTo(request));
        }
    }

    public void testPostBodyIsEmpty() {
        Method[] methods = {Method.POST, Method.PUT};
        for (Method method : methods) {
            Request request = new RequestBuilder()
                    .setMethod(method)
                    .setPath("/form")
                    .setContent("key=value")
                    .toRequest();

            FormStore store = new MemoryFormStore();
            Response response = new FormResponse(request, store);
            assertEquals(0, response.body().length);
        }
    }

    public void testDataNotSavedOnGet() {
        FormStore store = new MemoryFormStore();
        Request request = new RequestBuilder()
                .setMethod(Method.GET)
                .setPath("/form")
                .setContent("key=value")
                .toRequest();
        new FormResponse(request, store);
        assertEquals("", store.read("key"));
    }

    public void testDataReturnedOnGet() {
        FormStore store = new MemoryFormStore();
        Request request = new RequestBuilder()
                .setMethod(Method.POST)
                .setPath("/form")
                .setContent("key=value")
                .toRequest();
        new FormResponse(request, store);

        request = new RequestBuilder()
                .setMethod(Method.GET)
                .setPath("/form")
                .toRequest();
        Response response = new FormResponse(request, store);
        String responseBody = new String(response.body());
        assertTrue(responseBody.contains("key=value"));
    }

    public void testDataWipedOnDelete() {
        FormStore store = new MemoryFormStore();
        Request request = new RequestBuilder()
                .setMethod(Method.POST)
                .setPath("/form")
                .setContent("key=value")
                .toRequest();
        new FormResponse(request, store);

        request = new RequestBuilder()
                .setMethod(Method.DELETE)
                .setPath("/form")
                .toRequest();
        new FormResponse(request, store);
        assertEquals("", store.read("key"));
    }

}
