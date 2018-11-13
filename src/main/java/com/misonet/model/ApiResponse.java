package com.misonet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@SuppressWarnings("serial")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "status")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SuccessResponse.class, name = "SUCCESS"),
        @JsonSubTypes.Type(value = FailureResponse.class, name = "FAILURE")})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> extends BaseResponse {

    public final Status status;

    public ApiResponse(Status status) {
        this.status = status;
    }

    public ApiResponse() {
        this(Status.FAILURE);
    }

    public enum Status {
        SUCCESS, FAILURE
    }

}