package com.restful.todos.exception;

public class ExceptionResponse {
    private int httpStatusCode;
    private String message;
    private long timeStamp;

    public ExceptionResponse(int httpStatusCode, String message, long timeStamp) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
