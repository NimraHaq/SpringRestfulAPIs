package com.restful.books.exception;

import java.sql.Timestamp;

public class BookErrorResponse {
    private int httpStatusCode;
    private String message;
    private long timestamp;

    public BookErrorResponse(int httpStatusCode, String message, long timestamp) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
