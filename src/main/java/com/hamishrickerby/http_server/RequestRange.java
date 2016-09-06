package com.hamishrickerby.http_server;

/**
 * Created by rickerbh on 6/09/2016.
 */
public class RequestRange {
    private static int INVALID_RANGE = -1;

    int start;
    int end;
    String rangeText;

    public RequestRange(String range, int maxRange) {
        rangeText = range;
        if (hasStart() && hasEnd()) {
            start = rangeStart();
            end = rangeEnd();
        } else if (hasStart() && !hasEnd()) {
            start = maxRange - rangeStart();
            end = maxRange;
        } else if (!hasStart() && hasEnd()) {
            start = maxRange - rangeEnd();
            end = maxRange;
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    private boolean hasStart() {
        return rangeStart() != INVALID_RANGE;
    }

    private boolean hasEnd() {
        return rangeEnd() != INVALID_RANGE;
    }

    private int rangeStart() {
        String[] parts = splitRange();
        if (parts[0].isEmpty()) {
            return INVALID_RANGE;
        }
        return Integer.parseInt(parts[0]);
    }

    private int rangeEnd() {
        String[] parts = splitRange();
        if (parts.length != 2 || parts[1].isEmpty()) {
            return INVALID_RANGE;
        }
        return Integer.parseInt(parts[1]);
    }

    private String[] splitRange() {
        return rangeText.split("-");
    }

}
