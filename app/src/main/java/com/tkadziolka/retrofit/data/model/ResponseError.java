package com.tkadziolka.retrofit.data.model;

public class ResponseError {
    private int error_id;
    private String error_message;
    private String error_name;

    public ResponseError(int error_id, String error_message, String error_name) {
        this.error_id = error_id;
        this.error_message = error_message;
        this.error_name = error_name;
    }

    public int getError_id() {
        return error_id;
    }

    public String getError_message() {
        return error_message;
    }

    public String getError_name() {
        return error_name;
    }
}
