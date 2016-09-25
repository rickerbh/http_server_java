package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FormStore;
import com.hamishrickerby.http_server.MemoryFormStore;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import junit.framework.TestCase;

import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseCodeEquals;
import static com.hamishrickerby.http_server.helpers.HTTPServerTestUtils.assertResponseReasonEquals;

/**
 * Created by rickerbh on 12/09/2016.
 */
public class FormResponseTest extends TestCase {

    public void testFormPostResponseCode() {
        String[] methods = {Method.POST.name(), Method.PUT.name()};
        for (String method : methods) {
            Request request = new Request(method + " /form HTTP/1.1");
            Response response = new FormResponse(request, null);
            assertResponseCodeEquals("200", response);
        }
    }

    public void testReason() {
        String[] methods = {Method.POST.name(), Method.PUT.name()};
        for (String method : methods) {
            Request request = new Request(method + " /form HTTP/1.1");
            Response response = new FormResponse(request, null);
            assertResponseReasonEquals("OK", response);
        }
    }

    public void testRespondsTo() {
        String[] methods = {Method.POST.name(), Method.PUT.name()};
        for (String method : methods) {
            Request request = new Request(method + " /form HTTP/1.1");
            assertTrue(FormResponse.respondsTo(request));
            request = new Request(method + " /no-form HTTP/1.1");
            assertFalse(FormResponse.respondsTo(request));
        }
    }

    public void testPostBodyIsEmpty() {
        String[] methods = {Method.POST.name(), Method.PUT.name()};
        for (String method : methods) {
            Request request = new Request(method + " /form HTTP/1.1\r\n\r\nkey=value\r\n");
            FormStore store = new MemoryFormStore();
            Response response = new FormResponse(request, store);
            assertEquals(0, response.body().length);
        }
    }

    public void testDataNotSavedOnGet() {
        FormStore store = new MemoryFormStore();
        Request request = new Request(Method.GET.name() + " /form HTTP/1.1\r\n\r\nkey=value\r\n");
        new FormResponse(request, store);
        assertEquals("", store.read("key"));
    }

    public void testDataReturnedOnGet() {
        FormStore store = new MemoryFormStore();
        Request request = new Request(Method.POST.name() + " /form HTTP/1.1\r\n\r\nkey=value\r\n");
        new FormResponse(request, store);
        Response response = new FormResponse(new Request(Method.GET.name() + " /form HTTP/1.1"), store);
        String responseBody = new String(response.body());
        assertTrue(responseBody.contains("key=value"));
    }

    public void testDataWipedOnDelete() {
        FormStore store = new MemoryFormStore();
        Request request = new Request(Method.POST.name() + " /form HTTP/1.1\r\n\r\nkey=value\r\n");
        new FormResponse(request, store);
        new FormResponse(new Request(Method.DELETE.name() + " /form HTTP/1.1"), store);
        assertEquals("", store.read("key"));
    }

}
