package com.triofantastico.practiceproject.httpclient.restful;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class RestfulRestAssuredFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext context) {
        Response response = context.next(requestSpec, responseSpec);
        if (response.statusCode() != 200) {
            log.error("{} {} => \n Response headers => {} \n Response status => {} {} \n Response body => {}", requestSpec.getMethod(),
                    requestSpec.getURI(), response.getHeaders(), response.getStatusCode(), response.getStatusLine(), response.getBody());
        }
        log.info("{} {} \n Request headers => {} \n Request body => {} \n Response status => {} {} \n Response body => {}",
                requestSpec.getMethod(), requestSpec.getURI(), requestSpec.getHeaders(), requestSpec.getBody(), response.getStatusCode(),
                response.getStatusLine(), response.getBody().prettyPrint());
        return response;
    }
}
