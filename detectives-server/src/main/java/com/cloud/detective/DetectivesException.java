package com.cloud.detective;

import org.springframework.http.HttpStatus;

public class DetectivesException extends  RuntimeException {
    private HttpStatus status;

    public DetectivesException(String message) {
        super(message);
    }

    public DetectivesException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public DetectivesException(HttpStatus status,Throwable cause) {
        super(cause);
        this.status = status;
    }

    public DetectivesException(String message, Throwable cause) {
        super(message, cause);
    }

    public String errorMessage(){
        return status.value() + ":".concat(getMessage());
    }

    public HttpStatus getStatus() {
        return status;
    }
}
