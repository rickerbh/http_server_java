package com.hamishrickerby.http_server.responses;

import com.hamishrickerby.http_server.FilePatchCache;
import com.hamishrickerby.http_server.Request;
import com.hamishrickerby.http_server.RequestRange;

/**
 * Created by rickerbh on 6/09/2016.
 */
public class PartialFileContentsResponse extends FileContentsResponse {
    private static String RANGE_HEADER = "Range";

    public PartialFileContentsResponse(Request request, String rootPath, FilePatchCache cache) {
        super(request, rootPath, cache);
    }

    @Override
    public byte[] body() {
        byte[] fullResponse = super.body();
        int responseLength = fullResponse.length;

        RequestRange range = new RequestRange(stripBytesText(), responseLength);
        int rangeStart = range.getStart();
        int rangeEnd = range.getEnd();

        byte[] partialResponse = new byte[rangeEnd - rangeStart];

        for (int i = 0; i < rangeEnd - rangeStart; i++) {
            partialResponse[i] = fullResponse[rangeStart + i];
        }
        return partialResponse;
    }

    private String stripBytesText() {
        return request
                .getHeader(RANGE_HEADER)
                .replaceFirst("bytes=", "");
    }

    @Override
    protected int code() {
        return 206;
    }

    public static boolean isValidRangeRequest(Request request, String rootPath, FilePatchCache cache) {
        boolean isValidFile = FileContentsResponse.fileExists(rootPath, request.getPath(), cache);
        boolean hasRangeHeader = !request.getHeader(RANGE_HEADER).isEmpty();
        return isValidFile && hasRangeHeader;
    }
}

