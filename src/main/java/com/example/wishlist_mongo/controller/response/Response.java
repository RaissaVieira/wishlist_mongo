package com.example.wishlist_mongo.controller.response;

import org.springframework.http.HttpStatus;

public class Response <T>{

    private HttpStatus status;
    private String message;
    private T data;

    public Response(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
