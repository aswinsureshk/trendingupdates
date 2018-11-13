package com.misonet.model;

@SuppressWarnings("serial")
public class SuccessResponse<T> extends ApiResponse<T> {

    public T data;

    public SuccessResponse() {
        super(Status.SUCCESS);
        this.data = null;
    }

    public SuccessResponse(T data) {
        super(Status.SUCCESS);
        this.data = data;
    }

    public SuccessResponse(T data, int code, String message) {
        super(Status.SUCCESS);
        setCode(code);
        setMessage(message);
        this.data = data;
    }

    public SuccessResponse(int code, String message) {
        super(Status.SUCCESS);
        setCode(code);
        setMessage(message);
    }

}
