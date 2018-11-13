package com.misonet.model;

@SuppressWarnings("serial")
public class FailureResponse<T> extends ApiResponse<T> {

    public FailureResponse() {
        super(ApiResponse.Status.FAILURE);
    }

    public FailureResponse(int code, String message) {
        super(ApiResponse.Status.FAILURE);
        setCode(code);
        setMessage(message);
    }
}