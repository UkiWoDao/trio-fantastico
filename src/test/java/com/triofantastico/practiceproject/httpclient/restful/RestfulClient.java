package com.triofantastico.practiceproject.httpclient.restful;

import com.triofantastico.practiceproject.config.yamlbased.YamlConfigHandler;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class RestfulClient {

    protected static final String PETSTORE_BASE_URL = YamlConfigHandler.getEnvironmentConfig().getBeUrl();

    protected static RequestSpecification getCommonReqSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RestfulRestAssuredFilter())
                .setRelaxedHTTPSValidation()
                .build();
    }
    protected static Response sendRequest(RequestSpecification rSpec, Method method) {
         return given()
                    .spec(rSpec).relaxedHTTPSValidation().request(method)
                .then()
                    .extract().response();
    }
}