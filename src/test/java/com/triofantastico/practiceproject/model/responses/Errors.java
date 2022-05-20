package com.triofantastico.practiceproject.model.responses;

import io.restassured.response.Response;

public class Errors {

    String CODE = "code";
    String TYPE = "type";
    String MESSAGE = "message";

    public String getErrorCode(Response response) {
        return response.jsonPath().get(CODE).toString();
    }

    public String getErrorType(Response response) {
        return response.jsonPath().get(TYPE).toString();
    }

    public String getErrorMessage(Response response) {
        return response.jsonPath().get(MESSAGE).toString();
    }
}
