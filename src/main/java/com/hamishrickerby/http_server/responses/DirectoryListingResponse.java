package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FileDirectoryServer;
import com.hamishrickerby.http_server.Request;

import java.util.List;

/**
 * Created by rickerbh on 16/08/2016.
 */
public class DirectoryListingResponse extends FileSystemResponse {

    public DirectoryListingResponse(Request request, String rootPath) {
        super(request, rootPath);
    }

    protected byte[] body() {
        FileDirectoryServer fileDirectoryServer = new FileDirectoryServer(rootPath);
        String requestPath = request.getPath();
        List<String> listing = fileDirectoryServer.get(requestPath);

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("<html><body>")
                .append("<h1>")
                .append("Directory listing for " + requestPath + "\n\n")
                .append("</h1>");
        for (String filename : listing) {
            responseBuilder.append("<a href=\"")
                    .append(request.pathWithTrailingSlash())
                    .append(filename)
                    .append("\">")
                    .append(filename)
                    .append("</a>")
                    .append("<br />")
                    .append("\n");
        }
        responseBuilder.append("</body>")
                .append("</html>");
        return responseBuilder.toString().getBytes();
    }

}
