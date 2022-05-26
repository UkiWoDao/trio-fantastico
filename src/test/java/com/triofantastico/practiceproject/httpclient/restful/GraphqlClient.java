package com.triofantastico.practiceproject.httpclient.restful;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class GraphqlClient extends HttpClient {

    public Response create(String query) { return sendRequest(getCommonGqlReqSpec(query).baseUri(GQL_ENDPOINT_URL), Method.POST); }
}
