package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.MemoryFormStore;
import com.hamishrickerby.http_server.Method;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.auth.RequestAuthenticator;
import com.hamishrickerby.http_server.helpers.RequestBuilder;
import junit.framework.TestCase;

/**
 * Created by rickerbh on 17/08/2016.
 */
public class ResponseFactoryTest extends TestCase {
    public void testResponseIsDirectoryListingResponse() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder().toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof DirectoryListingResponse));
    }

    public void testResponseIsFileListingResponse() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder().setPath("/test.html").toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FileContentsResponse));
    }

    public void testResponseIs404() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder().setPath("/no-file-here").toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FourOhFourResponse));
    }

    public void testResponseIsRedirect() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder().setPath("/redirect").toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof RedirectResponse));
    }

    public void testResponseIsFourEightTeen() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder().setPath("/coffee").toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FourEightTeenResponse));

        request = new Request("GET /tea HTTP/1.1");
        response = factory.makeResponse(request);
        assertTrue((response instanceof FourEightTeenResponse));
    }

    public void testResponseIsPartial() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder()
                .setPath("/test.html")
                .addHeader("Range: 0-10")
                .toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof PartialFileContentsResponse));
    }

    public void testResponseIsLogs() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder().setPath("/logs").toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof LogsResponse));
    }

    public void testProtectedRoute() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        RequestAuthenticator auth = new RequestAuthenticator();
        auth.addRoute("/logs");
        auth.addCredentials("Aladdin", "OpenSesame");
        factory.setAuthenticator(auth);
        Request request = new RequestBuilder()
                .setPath("/logs")
                .addHeader("Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l")
                .toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof LogsResponse));
        auth.addCredentials("Aladdin", "failing-password");
        response = factory.makeResponse(request);
        assertTrue((response instanceof UnauthorizedResponse));
    }

    public void testResponseIsForm() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        factory.setFormStore(new MemoryFormStore());
        Request request = new RequestBuilder()
                .setMethod(Method.POST)
                .setPath("/form")
                .setContent("key=value&abc=123")
                .toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof FormResponse));
    }

    public void testResponseIsEmptyForMethodOptions() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder()
                .setMethod(Method.OPTIONS)
                .setPath("/method_options")
                .toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof EmptyResponse));

        request = new RequestBuilder()
                .setMethod(Method.OPTIONS)
                .setPath("/method_options2")
                .toRequest();
        response = factory.makeResponse(request);
        assertTrue((response instanceof EmptyResponse));
    }

    public void testResponseIsEcho() {
        ResponseFactory factory = new ResponseFactory("./src/test/resources", null);
        Request request = new RequestBuilder()
                .setPath("/parameters?1234=4312")
                .toRequest();
        Response response = factory.makeResponse(request);
        assertTrue((response instanceof ParametersEchoResponse));
    }
}
