package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.MemoryFormStore;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.auth.RequestAuthenticator;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class ResponseFactoryTest extends TestCase {
    public void testResponseIsDirectoryListingResponse() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET / HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof DirectoryListingResponse));
    }

    public void testResponseIsFileListingResponse() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET /test.html HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FileContentsResponse));
    }

    public void testResponseIs404() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET /no-file-here HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FourOhFourResponse));
    }

    public void testResponseIsRedirect() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET /redirect HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof RedirectResponse));
    }

    public void testResponseIsFourEightTeen() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET /coffee HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FourEightTeenResponse));

        request = new Request("GET /tea HTTP/1.1");
        response = factory.makeResponse(request);
        assertTrue((response instanceof FourEightTeenResponse));
    }

    public void testResponseIsPartial() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET /test.html HTTP/1.1\r\nRange: 0-10\r\n");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof PartialFileContentsResponse));
    }

    public void testResponseIsLogs() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new Request("GET /logs HTTP/1.1");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof LogsResponse));
    }

    public void testProtectedRoute() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        RequestAuthenticator auth = new RequestAuthenticator();
        auth.addRoute("/logs");
        auth.addCredentials("Aladdin", "OpenSesame");
        factory.setAuthenticator(auth);
        Request request = new Request("GET /logs HTTP/1.1\r\nAuthorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l\r\n");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof LogsResponse));
        auth.addCredentials("Aladdin", "failing-password");
        response = factory.makeResponse(request);
        assertTrue((response instanceof UnauthorizedResponse));
    }

    public void testResponseIsForm() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        factory.setFormStore(new MemoryFormStore());
        Request request = new Request("POST /form HTTP/1.1\r\n\r\nkey=value&abc=123\r\n");
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FormResponse));
    }
}

