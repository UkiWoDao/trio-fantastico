package com.triofantastico.practiceproject.httpclient.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GraphqlClient extends HttpClient {

    public Response send(String request) {
        return sendRequest(getCommonGqlReqSpec(request)
                .baseUri(GQL_ENDPOINT_URL), Method.POST);
    }

    public String parseGraphql(File file, ObjectNode variables) throws IOException {
        String graphqlFileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        return convertToGraphqlString(graphqlFileContent, variables);
    }

    private String convertToGraphqlString(String graphqlQuery, ObjectNode variables) throws JsonProcessingException {
        ObjectMapper oMapper = new ObjectMapper();
        ObjectNode oNode = oMapper.createObjectNode();
        oNode.put("query", graphqlQuery);
        oNode.set("variables", variables);

        return oMapper.writeValueAsString(oNode);
    }
}
